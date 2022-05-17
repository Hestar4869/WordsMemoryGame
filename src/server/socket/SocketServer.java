package server.socket;

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

    private SocketServer(){

        try {
            //开启处理登录信息的线程
            new Thread(new LoginServerRunnable()).start();
            //开启处理单词记忆功能的线程
            new Thread(new UpdateMemoryRunnable()).start();
            new Thread(new SendMemoryRunnable()).start();
            //开启注册功能
            new Thread(new RegisterRunnable()).start();
            //匹配游戏
            matchSocket();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }


    //对外的获取SocketServer实例的接口
    public static SocketServer getInstance(){
        return  socketServer;
    }

    public void matchSocket() throws IOException
    {
        ServerSocket ss=new ServerSocket(6669);
        Socket socket1,socket2;
        while (true){
            // 匹配两个客户端
            socket1=ss.accept();
            socket2=ss.accept();
            //传出两个客户端，让它们匹配一场游戏
            System.out.println("匹配成功一对");
            new Thread(new GameRunnable(socket1,socket2)).start();
        }
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
