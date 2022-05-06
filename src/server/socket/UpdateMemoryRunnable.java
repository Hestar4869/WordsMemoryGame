package server.socket;

import server.database.EnglishWordDAOImpl;
import server.database.dao.EnglishWordDAO;
import server.database.data.EnglishWord;

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
            ServerSocket server=new ServerSocket(6667);
            while(true){
                Socket socket=server.accept();
                //输入流
                InputStream is=socket.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                //输出流
                OutputStream os=socket.getOutputStream();
                PrintStream ps=new PrintStream(os);

                System.out.println("已连接到MemoryServer");
                //获取从客户端传送的账号
                String username= br.readLine();
                boolean isMaster= Boolean.parseBoolean(br.readLine());
//

                System.out.println(isMaster);
                System.out.println("当前是"+username+"请求更新Memory表");

                EnglishWordDAO wordDAO=new EnglishWordDAOImpl();
                List<EnglishWord> words=wordDAO.findMemoryByUsername(username,isMaster);

                //传送对象List<EnglishWord>对象
                ObjectOutputStream oos=new ObjectOutputStream(os);
                oos.writeObject(words);

                ps.println("test");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
