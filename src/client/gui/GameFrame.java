package client.gui;

import client.socket.SocketClient;
import server.database.data.EnglishWord;
import server.database.data.Memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @className: GameFrame
 * @description: 具体的游戏界面
 * @author: HMX
 * @date: 2022-05-06 21:54
 */
public class GameFrame extends JFrame implements ActionListener
{
    final static int WIDTH=800;
    final static int HEIGHT=600;
    //标记当前正在游戏的用户以及匹配的用户
    private String myName;
    private String partnerName;

    //连接服务器的Socket
    private SocketClient sc;

    //计时器
    private Timer timer=new Timer(100,this);
    //界面UI元素
    private JTextField wordText=new JTextField(this.getWidth());
    private JPanel gamePanel=new JPanel();
    private JPanel scorePanel=new JPanel();
    private JPanel jpl=new JPanel();

    private int myScore=10;
    private int partnerScore=10;
    private JLabel myScoreLabel=new JLabel(),pScoreLabel=new JLabel();
    //当前界面的英语单词
    private JLabel wordLabel=new JLabel();
    private EnglishWord word;
    private int wordY=0;

    //游戏结束后要保存的记忆单词列表
    java.util.List<Memory> memories=new ArrayList<>();
    public GameFrame(String myName) throws Exception
    {
        this.sc=new SocketClient(myName);
        this.partnerName=sc.getpUsername();
        //初始化游戏用户
        this.myName = myName;

        //初始化单词
//        wordLabel.setText(word.getWordDes());
//        wordLabel.setLocation(gamePanel.getX()/2,wordY);

        gamePanel.add(wordLabel);
        myScoreLabel.setText("你当前的成绩为："+myScore);
        pScoreLabel.setText("对手当前的成绩为："+partnerScore+" ");

        //成绩面板初始化
        scorePanel.setLayout(new BoxLayout(scorePanel,BoxLayout.Y_AXIS));
        scorePanel.add(new JLabel("当前用户："+myName));
        scorePanel.add(new JLabel("您的对手："+partnerName));
        scorePanel.add(new JLabel(" "));
        scorePanel.add(myScoreLabel);
//        scorePanel.add(new JLabel(" "));
        scorePanel.add(pScoreLabel);

        wordText.addActionListener(this);
        //背景面板初始化
        jpl.setLayout(new BorderLayout());
        jpl.add(wordText,BorderLayout.SOUTH);
        jpl.add(scorePanel,BorderLayout.EAST);
        jpl.add(gamePanel,BorderLayout.CENTER);

        //Dimension封装了电脑屏幕的宽度和高度
        //获取屏幕宽度和高度，使窗口位于屏幕正中间
        Dimension width=Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)(width.getWidth()-WIDTH)/2,(int)(width.getHeight()-HEIGHT)/2);

        this.add(jpl);
        this.setTitle("游戏界面");
        this.setSize(WIDTH,HEIGHT);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        timer.start();
        Thread.sleep(3000);
        initialWord();
    }

    //初始化单词位置及信息
    void initialWord() throws Exception
    {
        word= sc.getWord();
        wordLabel.setText(word.getWordDes());
        wordY=0;
        wordLabel.setLocation(gamePanel.getWidth()/2,wordY);

        System.out.println(myName+"当前单词为："+word.getWord());
    }

    //显示本局结果
    void showMessage(int opt)
    {
        //暂停单词的下降
        timer.stop();
        switch (opt){
            case 0:
                JOptionPane.showMessageDialog(this,"您没有回答，正确答案是"+word.getWord());
                break;
            case 1:
                JOptionPane.showMessageDialog(this,"恭喜回答正确");
                break;
            case 2:
                JOptionPane.showMessageDialog(this,"回答错误，答案是"+word.getWord());
                break;
        }

        //存储记忆单词
        Memory memory=new Memory(opt==1?true:false,word.getWord(),myName);
        memories.add(memory);

        //重新开启timer,保证单词能够继续下降
        timer.start();
    }

    //timer以及提交单词的触发事件
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==timer){
//            System.out.println("Timer在工作");
            wordY+=1;
            wordLabel.setLocation(gamePanel.getSize().width/2,wordY);
            if (wordY>=this.getHeight()){
                //当前单词已经跳出,下一局
                try
                {
                    myScore--;
                    myScoreLabel.setText("你当前的成绩为："+myScore);
                    showMessage(0);
                    initialWord();

                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        else if(e.getSource()==wordText){
            //判断是否回答正确
            if(wordText.getText().equals(word.getWord())){
                System.out.println("回答正确 +1分");
                myScore++;
                myScoreLabel.setText("你当前的成绩为："+myScore);
                showMessage(1);
            }
            else{
                System.out.println("回答错误 -2分");
                myScore-=2;
                myScoreLabel.setText("你当前的成绩为："+myScore);
                showMessage(2);
            }

            //进行下一局
            try
            {
                initialWord();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        if (myScore<=0){
            //游戏结束
            gameOver(true);
        }
    }
    void gameOver(boolean isFailed){
        try
        {
            //保存数据到服务器
            SocketClient.sendMemoryRequest(memories);
            //游戏结束信息
            JOptionPane.showMessageDialog(this,isFailed?"你输了":"你赢了");
            //构造已经登录好的登录页面
            new LoginFrame(sc.getpUsername());

            //释放当前窗口资源
            this.dispose();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception
    {
        //新建线程
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    new GameFrame("root");
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        }).start();

        new GameFrame("rt");
    }
}
