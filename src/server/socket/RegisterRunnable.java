package server.socket;

import server.database.UserDAOImpl;
import server.database.dao.UserDAO;
import server.database.data.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @className: RegisterRunnable
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-05-17 16:05
 */
public class RegisterRunnable implements Runnable
{
    ServerSocket ss;

    public RegisterRunnable() throws IOException
    {
        this.ss =new ServerSocket(17779);
    }

    @Override
    public void run()
    {
        while(true){
            try
            {
                Socket socket=ss.accept();
                //输入流
                InputStream is=socket.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                //输出流
                OutputStream os=socket.getOutputStream();
                PrintStream ps=new PrintStream(os);

                //获取从客户端传送的账号密码
                String username=br.readLine(),passwd= br.readLine();

                UserDAO userDAO=new UserDAOImpl();
                userDAO.insert(new User(username,passwd));
                ps.println("succeed");

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }
}
