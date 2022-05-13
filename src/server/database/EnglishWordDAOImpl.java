package server.database;

import server.database.dao.EnglishWordDAO;
import server.database.dao.UserDAO;
import server.database.data.EnglishWord;
import server.database.data.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: EnglishWordDAOImpl
 * @description: EnglishWordDAO接口实现
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
            EnglishWord word = new EnglishWord(rs.getString("word"), rs.getString("word_description"));
            wordList.add(word);
        }
        return wordList;
    }

    /**
     * @param: username
     * @param: isMaster
     * @description: 根据username和isMaster查找该用户已掌握或未掌握的单词
     * @return: java.util.List<server.database.data.User>
     * @author: HMX
     * @date: --
     */
    @Override
    public List<EnglishWord> findMemoryByUsername(String username, boolean isMaster) throws Exception
    {
        //建立连接
        Connection connection = BaseDAO.getConnection();
        int boolInt=isMaster?1:0;
        //要执行的sql语句
        String sql = String.format("SELECT a.word,a.word_description FROM english_word a,memory b WHERE a.word=b.word AND b.username='%s' AND b.is_master=%d", username, boolInt);
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        List<EnglishWord> words = new ArrayList<>();
        while (rs.next())
        {
            EnglishWord word = new EnglishWord(rs.getString("word"), rs.getString("word_description"));
            words.add(word);
        }
        return words;
    }

    @Override
    public EnglishWord findWordById(int id) throws Exception
    {
        //建立连接
        Connection connection = BaseDAO.getConnection();
        //要执行的sql语句
        String sql = "SELECT word,word_description FROM english_word WHERE id=" + "\"" + id + "\"";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
        {
            EnglishWord word=new EnglishWord(resultSet.getString("word"),resultSet.getString("word_description"));
            return word;
        }
        return null;
    }

    @Override
    public void insert(EnglishWord word) throws Exception
    {
        //建立连接
        Connection connection=BaseDAO.getConnection();
        //要执行的sql语句
        String sql=String.format("INSERT into USER (username,password) VALUES (%s,%s)",word.getWord(),word.getWordDes());
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.execute();
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
