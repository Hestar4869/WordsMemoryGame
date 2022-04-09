package server.database;

import server.database.DAO.EnglishWordDAO;
import server.database.DAO.UserDAO;
import server.database.data.EnglishWord;
import server.database.data.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: EnglishWordDAOImpl
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-04-07 17:07
 */
public class EnglishWordDAOImpl extends BaseDAO implements EnglishWordDAO
{

    @Override
    public List<EnglishWord> getAll() throws Exception
    {
        //建立连接
        Connection connection = BaseDAO.getConnection();
        //要执行的sql语句
        String sql = "SELECT * FROM ENGLISH_WORD";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        List<EnglishWord> wordList = new ArrayList<>();
        while (rs.next())
        {
            //拆分出resultSet中的EnglishWord类
            EnglishWord word = new EnglishWord(rs.getString("word"), rs.getString("word_description"), rs.getString("word_class"));
            wordList.add(word);
        }
        return wordList;
    }

    //UserDAO功能测试
    //TODO 用户唯一性检验
    public static void main(String[] args) throws Exception
    {
        UserDAO userDAO=new server.database.UserDAOImpl();
        userDAO.insert(new User("rt","rt"));
        List<User> userList=userDAO.getAll();
        for (User i:userList){
            System.out.println(i.getUsername()+" "+i.getPassword());
        }
    }
}
