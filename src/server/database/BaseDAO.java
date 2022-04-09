package server.database;

import server.database.DAO.EnglishWordDAO;
import server.database.data.EnglishWord;

import java.sql.*;

/**
 * @author HMX
 * @className: BaseDAO
 * @description: TODO 类描述
 * @date 2022-04-07 16:39
 */

public class BaseDAO
{
    //JDBC数据库驱动名
    private static String driver = "com.mysql.jdbc.Driver";
    //数据库地址
    private static String url = "jdbc:mysql://127.0.0.1:3306/words_memory_game?serverTimezone=GMT%2B8";
    //连接数据库的账号密码
    private static String user = "root";
    private static String password = "root";

    static
    {
        try
        {
            Class.forName(driver);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(url, user, password);
    }

    public static void closeAll(Connection conn, Statement stmt, ResultSet rs) throws SQLException
    {
        if (rs != null)
        {
            rs.close();
        }
        if (stmt != null)
        {
            stmt.close();
        }
        if (conn != null)
        {
            conn.close();
        }
    }


    public int executeSQL(String preparedSql, Object[] param) throws ClassNotFoundException
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        /* 处理SQL,执行SQL */
        try
        {
            conn = getConnection(); // 得到数据库连接
            pstmt = conn.prepareStatement(preparedSql); // 得到PreparedStatement对象
            if (param != null)
            {
                for (int i = 0; i < param.length; i++)
                {
                    pstmt.setObject(i + 1, param[i]); // 为预编译sql设置参数
                }
            }
            ResultSet num = pstmt.executeQuery(); // 执行SQL语句
        }
        catch (SQLException e)
        {
            e.printStackTrace(); // 处理SQLException异常
        }
        finally
        {
            try
            {
                BaseDAO.closeAll(conn, pstmt, null);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public void insert(EnglishWord word) throws Exception
    {
        //建立连接
        Connection connection=BaseDAO.getConnection();
        //要执行的sql语句
        String sql=String.format("INSERT into USER (username,password) VALUES (%s,%s,%s)",word.getWord(),word.getWordDes(),word.getWordClass());
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.execute();
    }
}

