package client.gui;

import client.socket.SocketClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @className: LoginFrame
 * @description: 登录界面外加登录后的菜单
 * @author: HMX
 * @date: 2022-04-25 16:46
 */
public class LoginFrame extends JFrame implements ActionListener
{
    private String username;
    final int WIDTH = 300;
    final int HEIGHT = 229;
    private FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 20, 20);

    //登录界面的元素
    private JLabel userLabel = new JLabel("输入账号");
    private JTextField userText = new JTextField(20);
    private JLabel passwdLabel = new JLabel("输入密码");
    private JPasswordField passwdText = new JPasswordField(20);

    private JButton loginButton = new JButton("登录");
    private JButton exitButton = new JButton("取消");

    private JPanel loginPanel = new JPanel();
    private JPanel textPanel = new JPanel();
    private JPanel passwdPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();

    //游戏界面的元素
    private JButton gameButton = new JButton("开始匹配");
    private JButton wordButton = new JButton("单词记忆");
    private JLabel menuLabel = new JLabel();
    private JPanel menuPanel = new JPanel();

    //构造登录页面
    public LoginFrame() throws HeadlessException
    {
        //给四个按钮添加
        loginButton.addActionListener(this);
        exitButton.addActionListener(this);
        gameButton.addActionListener(this);
        wordButton.addActionListener(this);

        //初始化登录面板
        textPanel.setLayout(flowLayout);
        textPanel.add(userLabel);
        textPanel.add(userText);

        passwdPanel.setLayout(flowLayout);
        passwdPanel.add(passwdLabel);
        passwdPanel.add(passwdText);

        buttonPanel.setLayout(flowLayout);
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);

        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        //填充透明盒子
        loginPanel.add(Box.createVerticalGlue());
        loginPanel.add(textPanel);
        loginPanel.add(passwdPanel);
        loginPanel.add(buttonPanel);
        loginPanel.add(Box.createVerticalGlue());


        //设置菜单面板
        gameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        wordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(menuLabel);
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(gameButton);
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(wordButton);
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));


        //Dimension封装了电脑屏幕的宽度和高度
        //获取屏幕宽度和高度，使窗口位于屏幕正中间
        Dimension width = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (width.getWidth() - WIDTH) / 2, (int) (width.getHeight() - HEIGHT) / 2);

        //设置当前显示的是登录面板
        this.add(loginPanel);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("登录界面");
    }

    //构造一个已经登录好的登录页面
    public LoginFrame(String username) throws Exception{
        this();
        //切换至游戏菜单
        this.username=username;
        menuLabel.setText("用户" + username + "您已成功登录");
        this.setTitle("菜单");
        loginPanel.setVisible(false);
        this.add(menuPanel);
        menuPanel.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            if (e.getSource() == loginButton)
            {
                //获取登录账号
                String username = userText.getText(), passwd = String.valueOf(passwdText.getPassword());

                //用SocketClient.login()函数尝试登录
                if (SocketClient.loginRequest(username, passwd)) {

                    //                    this.setTitle("用户"+username+"您已成功登录");
                    //切换至游戏菜单
                    this.username=username;
                    menuLabel.setText("用户" + username + "您已成功登录");
                    this.setTitle("菜单");
                    loginPanel.setVisible(false);
                    this.add(menuPanel);
                    menuPanel.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(this, "登录失败，账号或密码错误");
                }
            }
            else if (e.getSource() == exitButton){
                //退出
                System.exit(0);
            }
            else if (e.getSource() == gameButton){
                //跳转至游戏界面
                new GameFrame(username);
                //释放当前窗口资源
                this.dispose();
            }
            else if (e.getSource() == wordButton){
                //跳转至单词记忆界面
                WordMemoryFrame wordMemoryFrame=new WordMemoryFrame(username);
                //释放当前窗口资源
                this.dispose();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        //LoginFrame loginFrame = new LoginFrame();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                new LoginFrame();
            }
        }).start();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                new LoginFrame();
            }
        }).start();
    }
}
