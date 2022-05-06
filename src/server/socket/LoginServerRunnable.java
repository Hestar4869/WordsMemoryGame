package server.socket;

import server.database.UserDAOImpl;
import server.database.dao.UserDAO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @className: LoginServerRunnable
 * @description: 处理登录请求的服务器进程
 * @author: HMX
 * @date: 2022-05-05 14:49
 */
public class LoginServerRunnable implements Runnable
{
    private ServerSocket loginServer;

    LoginServerRunnable() throws IOException
    {
        loginServer=new ServerSocket(17775);

    }
    @Override
    public void run()
    {
        while(true){
            try
            {
                Socket socket=loginServer.accept();
                //输入流
                InputStream is=socket.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                //输出流
                OutputStream os=socket.getOutputStream();
                PrintStream ps=new PrintStream(os);

                //获取从客户端传送的账号密码
                String username=br.readLine(),passwd= br.readLine();

                UserDAO userDAO=new UserDAOImpl();
                if(userDAO.findPasswordByUsername(username).equals(passwd))
                    ps.println("succeed");
                else
                    ps.println("failed");

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
