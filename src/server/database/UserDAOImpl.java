package server.database;

import server.database.DAO.EnglishWordDAO;
import server.database.DAO.UserDAO;
import server.database.data.EnglishWord;
import server.database.data.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: UserDAOImpl
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-04-07 17:08
 */
public class UserDAOImpl extends BaseDAO implements UserDAO
{
    @Override
    public List<User> getAll() throws Exception
    {
        //建立连接
        Connection connection=BaseDAO.getConnection();
        //要执行的sql语句
        String sql="SELECT * FROM USER";
        PreparedStatement statement=connection.prepareStatement(sql);
        ResultSet resultSet=statement.executeQuery();
        List<User> userList=new ArrayList<>();
        while(resultSet.next()){
            //拆分出resultSet中的user类
            User user=new User(resultSet.getString("username"),resultSet.getString("password"));
            userList.add(user);
        }
        return userList;
    }

    /**
     * @param: username
     * @description: 根据传入的用户名返回对应的密码
     * @return: java.lang.String
     * @author: HMX
     * @date: 2022-4-7 17:29
     */
    @Override
    public String findPasswordByUsername(String username) throws Exception
    {
        //建立连接
        Connection connection=BaseDAO.getConnection();
        //要执行的sql语句
        String sql="SELECT password FROM USER WHERE username="+username;
        PreparedStatement statement=connection.prepareStatement(sql);
        ResultSet resultSet=statement.executeQuery();
        return resultSet.getString("password");
    }

    @Override
    public void Insert(User user) throws Exception
    {
        //建立连接
        Connection connection=BaseDAO.getConnection();
        //要执行的sql语句
        String sql="INSERT into USER (username,password) VALUES "+'('+user.getUsername()+","+user.getPassword()+')';
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.execute();
    }

}
