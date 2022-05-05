package client.gui;

import client.socket.SocketClient;

import javax.swing.*;
import java.awt.*;

/**
 * @className: WordFrame
 * @description: 单词记忆窗口
 * @author: HMX
 * @date: 2022-05-04 19:34
 */
public class WordFrame extends JFrame
{
    final static int WIDTH=800;
    final static int HEIGHT=600;

    WordFrame(){
        //Dimension封装了电脑屏幕的宽度和高度
        //获取屏幕宽度和高度，使窗口位于屏幕正中间
        Dimension width=Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)(width.getWidth()-WIDTH)/2,(int)(width.getHeight()-HEIGHT)/2);

        this.setTitle("单词记忆");
        this.setSize(WIDTH,HEIGHT);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    void getData(){

    }
    public static void main(String[] args)
    {
        WordFrame wordFrame=new WordFrame();
    }
}
