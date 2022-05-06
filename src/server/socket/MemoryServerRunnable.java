package server.socket;

import server.database.EnglishWordDAOImpl;
import server.database.dao.EnglishWordDAO;
import server.database.dao.UserDAO;
import server.database.data.EnglishWord;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * @className: MemoryServerRunnable
 * @description: 单词记忆功能服务。能够处理获取记忆单词请求，以及存储传送过来的单词
 * @author: HMX
 * @date: 2022-05-05 15:12
 */
public class MemoryServerRunnable implements Runnable
{

    private void storeMemoryWord(){
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {

            }
        }).start();
    }
    private void getMemoryWords(){
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {


            }
        }).start();
    }
    @Override
    public void run()
    {
        storeMemoryWord();
        getMemoryWords();
    }
}
