package server.database.dao;

import server.database.data.EnglishWord;
import server.database.data.User;

import java.util.List;

/**
 * @className: UserDAO
 * @description: User的抽象接口类
 * @author: HMX
 * @date: 2022-04-07 16:57
 */
public interface UserDAO
{
    //查询
    public List<User> getAll() throws Exception;
    public String findPasswordByUsername(String username) throws Exception;



    //插入
    public void insert(User user) throws Exception;
}
