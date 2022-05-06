package server.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @className: GettingMemoryRunnable
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-05-05 22:01
 */
public class GettingMemoryRunnable implements Runnable
{
    @Override
    public void run()
    {
        try
        {
            //初始化socket服务器
            ServerSocket server = new ServerSocket(6668);
            while (true)
            {
                Socket socket = server.accept();
                //todo 获取未掌握或已掌握的单词
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
