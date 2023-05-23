import java.sql.*;

public class DataSource  {
    public static void main(String[] args) {
        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbURL = "jdbc:sqlserver://DBCAG01LNR:1433;DatabaseName=AIS20140922094343";
        String userName = "sa";
        String userPwd = "P@55word";
        Connection dbConn = null;
        try {
            // 注册驱动
            Class.forName(driverName);
            // 获取数据库连接
            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
            System.out.println("连接数据库成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("连接失败");
        }

    }
}