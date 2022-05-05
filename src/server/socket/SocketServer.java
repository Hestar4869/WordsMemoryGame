package server.socket;

import server.database.dao.UserDAO;
import server.database.UserDAOImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @className: SocketServer
 * @description: 游戏服务端，处理客户端请求，单例模式，Socket实现
 * @author: HMX
 * @date: 2022-04-09 14:57
 */
public class SocketServer
{
    //生成单例的SocketServer
    private static SocketServer socketServer=new SocketServer();

    private static ServerSocket loginServer;

    private SocketServer() {
        try
        {
            loginServer=new ServerSocket(17775);
            acceptLogin();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private void acceptLogin()
    {
        //匿名对象单独开一线程处理登录请求
        new Thread(new Runnable()
        {
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
        }).start();
    }

    //对外的获取SocketServer实例的接口
    public static SocketServer getInstance(){
        return  socketServer;
    }



    void CreateThread(Object lock) throws Exception
    {

        System.out.println("start to serve");
        ServerSocket server = null;

        server = new ServerSocket(6666);
        Socket socket1 = server.accept();
        System.out.println("this is socket 1");
        Socket socket2 = server.accept();
        System.out.println("this is socket 2");

        return;
    }

    public static void main(String[] args) throws Exception
    {
        SocketServer socketServer = SocketServer.getInstance();
        socketServer.CreateThread(new Object());
    }


}
