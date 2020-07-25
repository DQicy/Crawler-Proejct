import org.apache.log4j.Logger;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;

public class MysqlPipeline implements Pipeline {
        private int index = 0;
        private  int nameSize;
        private int contentSize;
        private List<String> nameList = new ArrayList<>();

        private  List<String> contentList = new ArrayList<>();
        Logger logger = Logger.getLogger(MysqlPipeline.class);
        @Override
        public void process(ResultItems resultItems, Task task) {
            if(resultItems.get("name") == null){
                //此时应该去遍历content
                contentList = resultItems.get("content");
                contentSize = contentList.size();
                if(contentSize == 0) {
                    String sql = "insert into information_schema" + "(name, content) values ( \""  +  nameList.get(index) +" \"" + "," + "\"" + null +"\"" + ")";
                    System.out.println(sql);
                    jdbcUtil.executeSQL(sql);
                    index ++;
                }else{
                    StringBuilder sb = new StringBuilder();
                    for(int i = 0; i < contentSize; i++) {
                    sb.append(contentList.get(i) + " ");
                }
                String s = sb.toString();
                s = s.trim();
                String sql = "insert into information_schema" + "(name, content) values ( \""  +  nameList.get(index) +" \"" + "," + "\"" + s +"\"" + ")";
                System.out.println(sql);
                jdbcUtil.executeSQL(sql);
                logger.info("sql执行成功" + sql);
                index ++;
                }
            }else{
                //从0开始
                index = 0;
                nameList = resultItems.get("name");
                nameSize = nameList.size();
                }
        }
}