package client.socket;

import server.database.dao.EnglishWordDAO;
import server.database.data.EnglishWord;

import java.io.*;
import java.net.Socket;
import java.util.List;

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
     * @param: username 登录的账号
     * @param: passwd   登录的密码
     * @description: 根据传入的账号密码，对远端服务器进行请求验证
     * @return: boolean 返回是否成功登录
     * @author: HMX
     * @date: --
     */
    public static boolean loginRequest(String username, String passwd) throws Exception{
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

    public static List<EnglishWord> memoryRequest(String username,boolean isMaster) throws Exception
    {
        Socket socket=new Socket("127.0.0.1",6667);
        //输入流
        InputStream is=socket.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        //输出流
        OutputStream os=socket.getOutputStream();
        PrintStream ps=new PrintStream(os);

        //传送账号以及选择已掌握还是未掌握的记忆单词
        ps.println(username);
        ps.println(isMaster);
        System.out.println("已传送"+username);

        //读取传送回来的List<EnglishWord>类
        ObjectInputStream ois=new ObjectInputStream(is);
        List<EnglishWord> words= (List<EnglishWord>) ois.readObject();

        for (EnglishWord word: words)
        {
            System.out.println(word.getWord());
        }
        return words;
    }
    public static void main(String[] args) throws Exception
    {

//        Socket socket1 = new Socket("127.0.0.1", 66666);
//        Thread.sleep(1000);
//        Socket socket2 = new Socket("127.0.0.1", 66666);
//        System.out.println(socket1.getLocalPort() + " " + socket2.getLocalPort());
//        Thread.sleep(1000);
        SocketClient.loginRequest("root","root");
        SocketClient.memoryRequest("root",true);
    }
}
