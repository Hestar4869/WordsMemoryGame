package server.socket;

import server.database.EnglishWordDAOImpl;
import server.database.dao.EnglishWordDAO;
import server.database.data.EnglishWord;

import java.io.*;
import java.net.Socket;

/**
 * @className: GameRunnable
 * @description: 传入两个socket, 对两个匹配的客户端进行游戏所需服务
 * @author: HMX
 * @date: 2022-05-10 20:19
 */
public class GameRunnable implements Runnable
{
    private Socket socket1, socket2;
    BufferedReader br1, br2;
    PrintStream ps1, ps2;
    ObjectOutputStream oos1, oos2;

    public GameRunnable(Socket socket1, Socket socket2) throws IOException
    {
        this.socket1 = socket1;
        this.socket2 = socket2;

        //初始化输入流
        InputStream is1 = socket1.getInputStream(), is2 = socket2.getInputStream();
        br1 = new BufferedReader(new InputStreamReader(is1));
        br2 = new BufferedReader(new InputStreamReader(is2));
        //初始化输出流
        OutputStream os1 = socket1.getOutputStream(), os2 = socket2.getOutputStream();
        ps1 = new PrintStream(os1);
        ps2 = new PrintStream(os2);
        oos1 = new ObjectOutputStream(os1);
        oos2 = new ObjectOutputStream(os2);

        ps1.println("匹配成功");
        //传输对手名字
        ps1.println(br2.readLine());
        ps2.println("匹配成功");
        //传输对手名字
        ps2.println(br1.readLine());

    }

    @Override
    public void run()
    {
        try
        {
            //再开两个线程分别处理两个socket的输入信息
            waitSocket1();
            waitSocket2();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

    private void sendRandomWord() throws Exception
    {
        int id = (int) (Math.random() * 3000 % 2119 + 1);
        EnglishWordDAO DAO = new EnglishWordDAOImpl();
        //根据随机id查询单词
        EnglishWord word = DAO.findWordById(id);
        System.out.println("发送单词"+word.getWord());
        oos1.writeObject(word);
        oos2.writeObject(word);
    }

    private void waitSocket1() throws Exception
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    while (true)
                    {
                        String msg = br1.readLine();
                        if (msg.equals("word"))
                        {

                            //传送随机单词
                            sendRandomWord();
                        }
                        else if (msg.equals("over"))
                        {
                            //游戏结束
                            System.out.println("over");
                            oos2.writeObject(new EnglishWord("zzz","zzz"));
                            socket1.shutdownOutput();
                            socket2.shutdownOutput();
                            break;
                        }
                    }
                }
                catch (Exception ex)
                {
                }

            }
        }).start();
    }

    private void waitSocket2() throws Exception
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    while (true)
                    {
                        String msg = br2.readLine();
                        if (msg.equals("word"))
                        {
                            //传送随机单词
                            sendRandomWord();
                        }
                        else if (msg.equals("over"))
                        {
                            //游戏结束
                            System.out.println("over");
                            oos1.writeObject(new EnglishWord("zzz","zzz"));
                            socket1.shutdownOutput();
                            socket2.shutdownOutput();
                            break;
                        }
                    }
                }
                catch (Exception ex)
                {
                }

            }
        }).start();

    }
}
