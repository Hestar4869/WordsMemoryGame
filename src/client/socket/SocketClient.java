package client.socket;

import server.database.data.EnglishWord;
import server.database.data.Memory;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * @className: SocketClient
 * @description: 游戏客户端, 单例模式，Socket实现
 * @author: HMX
 * @date: 2022-04-09 15:07
 */
public class SocketClient
{
    private Socket socket;
    private String mUsername;
    private String pUsername;
    private BufferedReader br = null;
    private PrintStream ps = null;
    private ObjectInputStream ois = null;



    public SocketClient(String mUsername) throws Exception
    {
        this.mUsername = mUsername;
    }

    //匹配用户
    public boolean matchRequest() throws Exception {
        socket = new Socket("127.0.0.1", 6669);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ps = new PrintStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());

        ps.println(mUsername);
        //阻塞，等待匹配成功消息
        String msg = br.readLine();
        System.out.println(msg);
        if (msg.equals("匹配成功"))
        {
            //获取对手名字
            pUsername = br.readLine();
            System.out.println("你匹配的对手是：" + pUsername);
            return true;
        }
        else {
            return false;
        }
    }

    //单词获取
    public EnglishWord getWord() throws Exception
    {

        ps.println("word");
        EnglishWord word;
        word= (EnglishWord) ois.readObject();
        return word;
    }

    public String getpUsername()
    {
        return pUsername;
    }

    //主动结束游戏
    public void endGameRequest(){
        ps.println("over");
    }

    //根据传入的账号密码，对远端服务器进行请求验证
    public static boolean loginRequest(String username, String passwd) throws Exception {
        Socket loginSocket = new Socket("127.0.0.1", 17775);
        //输入流
        InputStream is = loginSocket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        //输出流
        OutputStream os = loginSocket.getOutputStream();
        PrintStream ps = new PrintStream(os);

        //传送账号密码
        ps.println(username);
        ps.println(passwd);

        //读取信息
        String info = br.readLine();
        if (info.equals("succeed"))
        {
            System.out.println("登录成功");
            return true;
        }
        else
        {
            System.out.println("账号或密码错误");
            return false;
        }
    }

    //从远端获取已掌握或未掌握的单词
    public static List<EnglishWord> getMemoryRequest(String username, boolean isMaster) throws Exception {
        Socket socket = new Socket("127.0.0.1", 6667);
        //输入流
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        //输出流
        OutputStream os = socket.getOutputStream();
        PrintStream ps = new PrintStream(os);

        //传送账号以及选择已掌握还是未掌握的记忆单词
        ps.println(username);
        ps.println(isMaster);
        System.out.println("已传送" + username);

        //读取传送回来的List<EnglishWord>类
        ObjectInputStream ois = new ObjectInputStream(is);
        List<EnglishWord> words = (List<EnglishWord>) ois.readObject();

        for (EnglishWord word : words)
        {
            System.out.println(word.getWord());
        }
        return words;
    }

    //传送已掌握或未掌握的单词给远端
    public static void sendMemoryRequest(List<Memory> memories) throws IOException
    {
        Socket socket = new Socket("127.0.0.1", 16668);
        //输入流
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        //输出流
        OutputStream os = socket.getOutputStream();
        PrintStream ps = new PrintStream(os);
        ObjectOutputStream oos=new ObjectOutputStream(os);

        //传送List对象
        oos.writeObject(memories);
    }

    //传入账号密码，进行注册
    public static boolean registerRequest(String username,String passwd) throws Exception
    {
        Socket registerSocket = new Socket("127.0.0.1", 17779);
        //输入流
        InputStream is = registerSocket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        //输出流
        OutputStream os = registerSocket.getOutputStream();
        PrintStream ps = new PrintStream(os);

        //传送账号密码
        ps.println(username);
        ps.println(passwd);

        //读取信息
        String info = br.readLine();
        if (info.equals("succeed"))
        {
            System.out.println("注册成功");
        }
        return true;
    }
    public static void main(String[] args) throws Exception
    {

//        Socket socket1 = new Socket("127.0.0.1", 66666);
//        Thread.sleep(1000);
//        Socket socket2 = new Socket("127.0.0.1", 66666);
//        System.out.println(socket1.getLocalPort() + " " + socket2.getLocalPort());
//        Thread.sleep(1000);
//        SocketClient.loginRequest("root","root");
//        SocketClient.memoryRequest("root",true)

        SocketClient.registerRequest("hmx","hmx");
//        SocketClient.matchRequest("cjq");
    }
}
