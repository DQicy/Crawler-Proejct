import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.*;
import java.util.Map;

public class filePipeline extends MyFilePersistentBase implements Pipeline {

    public filePipeline(String path) {
        setPath(path);
    }
    @Override
    public void process(ResultItems resultItems, Task task) {
        String path = this.path;
        System.out.println(path);
        try {
            String URL = resultItems.getRequest().getUrl();
            int size = URL.length();
            if(URL.substring(size - 8, URL.length()).matches("[A-Z]{2}[0-9]{6}")){
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile(path + URL.substring(size - 8 , size) + ".html")), "UTF-8"));
            //放入文件中的url
            //resultItems的key"name",value是"值"
            //resultItems中也封装了request
            System.out.println(resultItems.getAll().size());
            printWriter.println("url:\t" + resultItems.getRequest().getUrl());
            for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
                String string = (String) entry.getValue();
                printWriter.println(entry.getKey() + ":");
                printWriter.println(string);
            }
                printWriter.close();

    }
    } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}


