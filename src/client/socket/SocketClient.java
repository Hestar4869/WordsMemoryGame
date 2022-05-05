package client.socket;

import java.io.*;
import java.net.Socket;

/**
 * @className: SocketClient
 * @description: 游戏客户端,单例模式，Socket实现
 * @author: HMX
 * @date: 2022-04-09 15:07
 */
public class SocketClient
{
    private static Socket loginSocket;

    /**
     * @param: username
     * @param: passwd
     * @description: 根据传入的账号密码，对远端服务器进行请求验证
     * @return: boolean
     * @author: HMX
     * @date: --
     */
    public static boolean login(String username, String passwd) throws Exception{
        loginSocket=new Socket("127.0.0.1",17775);
        //输入流
        InputStream is=loginSocket.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        //输出流
        OutputStream os=loginSocket.getOutputStream();
        PrintStream ps=new PrintStream(os);

        //传送账号密码
        ps.println(username);
        ps.println(passwd);

        //读取信息
        String info= br.readLine();
        if(info.equals("succeed")){
            System.out.println("登录成功");
            return true;
        }
        else{
            System.out.println("账号或密码错误");
            return false;
        }
    }
    public static void main(String[] args) throws Exception
    {

//        Socket socket1 = new Socket("127.0.0.1", 66666);
//        Thread.sleep(1000);
//        Socket socket2 = new Socket("127.0.0.1", 66666);
//        System.out.println(socket1.getLocalPort() + " " + socket2.getLocalPort());
//        Thread.sleep(1000);
        SocketClient.login("root","root");
    }
}
