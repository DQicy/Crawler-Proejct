import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author ydq
 */
public class ScrawlWeb implements PageProcessor {
    /**
     * 目录页
     */
    private String urlList = new String("https://www.iteblog.com/archives/category/carbondata/page/[0-9]*");
    @Override
    public void process(Page page) {
        if(page.getUrl().regex(urlList).match()){
            //将列表中页面名称加到name的键值对中
            page.putField("name", page.getHtml().xpath("/html/body/section/div[4]/div/article/header/h2/a/text()").all());
            //把文章详细信息的链接加进来
            page.addTargetRequests(page.getHtml().xpath("/html/body/section/div[4]/div/article/header/h2/a/@href").all());
            //把列表页都加进来
            page.addTargetRequests(page.getHtml().links().regex(urlList).all());

        }else{
            //把文章详情页中的目录存在page中
            page.putField("content", page.getHtml().xpath("//div[@class=\"no_bullets\"]//ul[@class=\"toc_list\"]//a/text()").all());
        }
   }
   @Override
   public Site getSite() {
        return site;
    }
   private Site site = Site.me().setCharset("utf8").setRetryTimes(3)
           //设置抓取的间隔时间
           .setSleepTime(2000)
           //设置超时时间，单位是ms
           .setTimeOut(60000);
   public static void main(String[] args) {
           //使用文件进行存储

       //http://f10.eastmoney.com/f10_v2/CompanyBigNews.aspx?code
           //Spider.create(new ScrawlWeb()).addUrl("https://www.iteblog.com/archives/category/carbondata/page/1").addPipeline(new FilePipeline("/Users/me/Desktop")).thread(5).run();
           //使用mysql进行存储
           //Spider.create(new ScrawlWeb()).addUrl("https://www.iteblog.com/archives/category/carbondata/page/1").addPipeline(new MysqlPipeline()).thread(5).run();

       Spider.create(new ScrawlWeb()).addUrl("http://quote.eastmoney.com/center/gridlist.html#hs_a_board").addPipeline(new FilePipeline("/Users/me/Desktop")).thread(5).run();


   }
}
