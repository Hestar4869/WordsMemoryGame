package server.socket;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @className: SocketServer
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-04-09 14:57
 */
public class SocketServer
{
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
        SocketServer socketServer = new SocketServer();
        socketServer.CreateThread(new Object());
    }


}
