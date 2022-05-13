package server.database.dao;

import server.database.data.EnglishWord;

import java.sql.SQLException;
import java.util.List;

/**
 * @className: EnglishWordDAO
 * @description: EnglishWord的抽象接口类
 * @author: HMX
 * @date: 2022-04-07 16:58
 */
public interface EnglishWordDAO
{
    public List<EnglishWord> getAll() throws Exception;

    //根据用户名查找Memory表，根据isMaster参数判断查找的已掌握或未掌握参数
    public List<EnglishWord> findMemoryByUsername(String username, boolean isMaster) throws Exception;
    public EnglishWord findWordById(int id) throws Exception;
    public void insert(EnglishWord word) throws Exception;
}
