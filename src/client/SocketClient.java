package client;

import java.net.Socket;

/**
 * @className: SocketClient
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-04-09 15:07
 */
public class SocketClient
{

    public static void main(String[] args) throws Exception
    {

        Socket socket1 = new Socket("127.0.0.1", 6666);
        Thread.sleep(1000);
        Socket socket2 = new Socket("127.0.0.1", 6666);
        System.out.println(socket1.getLocalPort() + " " + socket2.getLocalPort());
        Thread.sleep(1000);

    }
}
