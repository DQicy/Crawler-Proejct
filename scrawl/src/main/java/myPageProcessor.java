
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class myPageProcessor implements PageProcessor {

    //爬虫配置信息设置
    private Site site = new Site()
            .setCharset("utf-8")    //设置编码
            .setSleepTime(1)        //设置抓取间隔
            .setTimeOut(10)         //设置超时时间
            .setRetrySleepTime(3)      //设置重试时间
            .setRetryTimes(3);       //设置重试次数


    @Override
    public void process(Page page) {

        //对抓取到的页面进行处理
        page.putField("title",page.getHtml().css(".c44380").all());

    }

    @Override
    public Site getSite() {
        return this.site;
    }
}
