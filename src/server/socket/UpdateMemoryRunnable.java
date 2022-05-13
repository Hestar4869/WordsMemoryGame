package server.socket;

import server.database.MemoryDAOImpl;
import server.database.dao.MemoryDAO;
import server.database.data.Memory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * @className: UpdateMemoryRunnable
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-05-05 22:01
 */
public class UpdateMemoryRunnable implements Runnable
{
    @Override
    public void run()
    {
        try
        {
            //初始化socket服务器
            ServerSocket server = new ServerSocket(16668);
            while (true)
            {
                Socket socket = server.accept();
                //todo 获取未掌握或已掌握的单词
                //输入流
                InputStream is=socket.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                ObjectInputStream ois=new ObjectInputStream(is);
                //输出流
                OutputStream os=socket.getOutputStream();
                PrintStream ps=new PrintStream(os);

                try
                {
                    //读取远端要保存的历史记录
                    List<Memory> memories=(List<Memory>) ois.readObject();
                    MemoryDAO memoryDAO=new MemoryDAOImpl();
                    for (Memory memory : memories)
                    {
                        //将数据插入memory表中
                        memoryDAO.insert(memory);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
