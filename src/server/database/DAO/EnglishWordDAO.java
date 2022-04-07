package server.database.DAO;

import server.database.data.EnglishWord;

import java.util.List;

/**
 * @className: EnglishWordDAO
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-04-07 16:58
 */
public interface EnglishWordDAO
{
    public List<EnglishWord> getAll() throws Exception;
}
