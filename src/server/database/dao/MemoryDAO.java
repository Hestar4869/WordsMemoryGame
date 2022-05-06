package server.database.dao;

import server.database.data.EnglishWord;
import server.database.data.Memory;

import java.util.List;

/**
 * @className: MemoryDAO
 * @description: Memory的抽象接口
 * @author: HMX
 * @date: 2022-05-05 19:01
 */
public interface MemoryDAO
{
    public void insert(Memory memory) throws Exception;

}
