import java.io.File;

/**
 * 基类
 * @author zhl
 *
 */
public class MyFilePersistentBase {

    String path;

    /**
     * 默认的路径分隔符
     */
    static String path_separator = "/";
    static{
        String property = System.getProperties().getProperty("file.separator");
        if(property != null) {
            path_separator = property;
        }
    }
    public void setPath(String path){
        //如果不是以文件分隔符为结尾的话，则将分隔符放到path末尾
        if(! path.endsWith(path_separator)) {
            path += path_separator;
        }
        this.path = path;
    }
        public File getFile(String fullName){
            check(fullName);
            return new File(fullName);
    }
    public void check(String fullName){
        int index = fullName.lastIndexOf(path_separator);
        if(index > 0){
            String path = fullName.substring(0, index);
            File file = new File(path);
            if(! file.exists()){
                file.mkdirs();
            }
        }
    }
    public String getPath(){
        return path;
    }
}