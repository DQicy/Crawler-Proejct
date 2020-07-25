import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
/**
 * @author ydq
 */
public class jdbcUtil {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    // 静态方法块，加载驱动
    static {
        InputStream is = jdbcUtil.class.getResourceAsStream("/driver.properties");
        Properties prop = new Properties();
        try {
            prop.load(is);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        driver = prop.getProperty("driver");
        url = prop.getProperty("url");
        username = prop.getProperty("username");
        password = prop.getProperty("password");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void executeSQL(String sql) {
        Connection conn = getConn();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
    }
    private static Connection getConn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    private static void close(Connection conn, Statement stat, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}