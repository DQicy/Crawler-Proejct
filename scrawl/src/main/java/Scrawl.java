import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.List;

public class Scrawl implements PageProcessor {

    Logger logger = Logger.getLogger(Scrawl.class);

    @Override
    public void process(Page page) {
        //首先从json数据页开始爬取
    if(page.getUrl().toString().equals("http://21.push2.eastmoney.com/api/qt/clist/get?pn=1&pz=4059&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:6,m:0+t:13,m:0+t:80,m:1+t:2,m:1+t:23&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1595486383877")){
        List<String> list = new JsonPathSelector("$.data.diff..f12").selectList(page.getRawText());
        for(int i = 0; i < list.size(); i ++){
            String stockCode = list.get(i);
            if(stockCode.startsWith("5") || stockCode.startsWith("6") || stockCode.startsWith("9")) {
                stockCode = "sh" + stockCode;
            }else if(stockCode.substring(0, 3).equals("009") || stockCode.substring(0, 3).equals("126") || stockCode.substring(0, 3).equals("110") || stockCode.substring(0, 3).equals("201") || stockCode.substring(0, 3).equals("202") || stockCode.substring(0, 3).equals("203") || stockCode.substring(0, 3).equals("204")){
                stockCode = "sh" + stockCode;
            }else{
                stockCode = "sz" + stockCode;
            }
            page.addTargetRequest("http://quote.eastmoney.com/"+ stockCode + ".html");
            logger.info("当前股票代码是：" + stockCode);
            page.addTargetRequest("http://f10.eastmoney.com/CompanySurvey/CompanySurveyAjax?code=" + stockCode.toUpperCase());
        }
    } else{
        //进入最终的公司详情页面，需要进行数据的存取
        logger.info("正在进行数据的存储");

        //公司名称
        page.putField("公司名称", new JsonPathSelector("$..jbzl.gsmc").select(page.getRawText()));

        //英文名称
        page.putField("英文名称", new JsonPathSelector("$..jbzl.ywmc").select(page.getRawText()));

        //曾用名
        page.putField("曾用名", new JsonPathSelector("$..jbzl.cym").select(page.getRawText()));

        //A股代码
        page.putField("A股代码", new JsonPathSelector("$..jbzl.agdm").select(page.getRawText()));

        //A股简称
        page.putField("A股简称", new JsonPathSelector("$..jbzl.agjc").select(page.getRawText()));

        //B股代码
        page.putField("B股代码", new JsonPathSelector("$..jbzl.bgdm").select(page.getRawText()));

        //B股简称
        page.putField("B股简称", new JsonPathSelector("$..jbzl.bgjc").select(page.getRawText()));

        //H股代码
        page.putField("H股简称", new JsonPathSelector("$..jbzl.hgdm").select(page.getRawText()));

        //H股简称
        page.putField("H股简称", new JsonPathSelector("$..jbzl.hgjc").select(page.getRawText()));

        //证券类别
        page.putField("证券类别", new JsonPathSelector("$..jbzl.zqlb").select(page.getRawText()));

        //所属东财行业
        page.putField("所属东财行业", new JsonPathSelector("$..jbzl.sshy").select(page.getRawText()));

        //上市交易所
        page.putField("上市交易所", new JsonPathSelector("$..jbzl.ssjys").select(page.getRawText()));

        //所属证监会行业
        page.putField("所属证监会行业", new JsonPathSelector("$..jbzl.sszjhhy").select(page.getRawText()));

        //总经理
        page.putField("总经理", new JsonPathSelector("$..jbzl.zjl").select(page.getRawText()));

        //法人代表
        page.putField("法人代表", new JsonPathSelector("$..jbzl.frdb").select(page.getRawText()));

        //董秘
        page.putField("董秘", new JsonPathSelector("$..jbzl.dm").select(page.getRawText()));

        //董事长
        page.putField("董事长", new JsonPathSelector("$..jbzl.dsz").select(page.getRawText()));

        //证券事务代表
        page.putField("证券事务代表", new JsonPathSelector("$..jbzl.zqswdb").select(page.getRawText()));

        //独立董事
        page.putField("独立董事", new JsonPathSelector("$..jbzl.dlds").select(page.getRawText()));

        //联系电话
        page.putField("联系电话", new JsonPathSelector("$..jbzl.lxdh").select(page.getRawText()));

        //电子信箱
        page.putField("电子信箱", new JsonPathSelector("$..jbzl.dzxx").select(page.getRawText()));

        //传真
        page.putField("传真", new JsonPathSelector("$..jbzl.cz").select(page.getRawText()));

        //公司网址
        page.putField("公司网址", new JsonPathSelector("$..jbzl.gswz").select(page.getRawText()));

        //办公地址
        page.putField("办公地址", new JsonPathSelector("$..jbzl.bgdz").select(page.getRawText()));

        //	注册地址
        page.putField("注册地址", new JsonPathSelector("$..jbzl.zcdz").select(page.getRawText()));

        //区域
        page.putField("区域", new JsonPathSelector("$..jbzl.qy").select(page.getRawText()));

        //邮政编码
        page.putField("邮政编码", new JsonPathSelector("$..jbzl.yzbm").select(page.getRawText()));

        //注册资本(元)
        page.putField("注册资本", new JsonPathSelector("$..jbzl.zczb").select(page.getRawText()));

        //工商登记
        page.putField("工商登记", new JsonPathSelector("$..jbzl.gsdj").select(page.getRawText()));

        //雇员人数
        page.putField("雇员人数", new JsonPathSelector("$..jbzl.gyrs").select(page.getRawText()));

        //管理人员人数
        page.putField("管理人员人数", new JsonPathSelector("$..jbzl.glryrs").select(page.getRawText()));

        //律师事务所
        page.putField("律师事务所", new JsonPathSelector("$..jbzl.lssws").select(page.getRawText()));

        //会计师事务所
        page.putField("会计师事务所", new JsonPathSelector("$..jbzl.kjssws").select(page.getRawText()));

        //公司简介
        page.putField("公司简介", new JsonPathSelector("$..jbzl.gsjj").select(page.getRawText()));

        //经营范围
        page.putField("经营范围", new JsonPathSelector("$..jbzl.jyfw").select(page.getRawText()));
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
        Spider.create(new Scrawl()).addUrl("http://21.push2.eastmoney.com/api/qt/clist/get?pn=1&pz=4059&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:6,m:0+t:13,m:0+t:80,m:1+t:2,m:1+t:23&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1595486383877").addPipeline(new ConsolePipeline()).addPipeline(new filePipeline("/Users/me/Desktop/Download")).thread(5).run();
    }
}
