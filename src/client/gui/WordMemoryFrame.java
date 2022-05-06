package client.gui;

import client.socket.SocketClient;
import server.database.data.EnglishWord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: WordMemoryFrame
 * @description: 单词记忆窗口
 * @author: HMX
 * @date: 2022-05-04 19:34
 */
public class WordMemoryFrame extends JFrame implements ActionListener
{
    final static int WIDTH=800;
    final static int HEIGHT=600;

    private JPanel bgPanel=new JPanel();
    private JPanel labelPanel=new JPanel();
    private JPanel infoPanel=new JPanel();

    private JButton controlBtn=new JButton("已掌握");
    private JButton uncontrolBtn=new JButton("未掌握");
    private String username;

    //构造函数
    WordMemoryFrame(String username){
        this.username=username;
        //为按钮添加事件
        controlBtn.addActionListener(this);
        uncontrolBtn.addActionListener(this);

        //最上方的标题
        labelPanel.add(controlBtn);
        labelPanel.add(uncontrolBtn);
        labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER,100,10));

        //下方的内容

        //Dimension封装了电脑屏幕的宽度和高度
        //获取屏幕宽度和高度，使窗口位于屏幕正中间
        Dimension width=Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)(width.getWidth()-WIDTH)/2,(int)(width.getHeight()-HEIGHT)/2);

        bgPanel.add(labelPanel);
        bgPanel.add(infoPanel);
        bgPanel.setLayout(new BoxLayout(bgPanel,BoxLayout.Y_AXIS));


        this.add(bgPanel);
        this.setTitle("单词记忆");
        this.setSize(WIDTH,HEIGHT);
        this.setVisible(true);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
    
    
    public void showInfo(boolean isControl) throws Exception
    {
        infoPanel.removeAll();
        List<EnglishWord> words= SocketClient.memoryRequest(username,isControl);
        for (EnglishWord word : words)
        {
            JPanel itemPanel = new JPanel();

            itemPanel.add(new JLabel(word.getWord()));
            itemPanel.add(new JLabel(word.getWordDes()));
            infoPanel.add(itemPanel);

        }
        //对info面板中的组件重新布局并绘制
        infoPanel.revalidate();
        //盒式布局纵向分布
        infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));

        //对info面板本身进行重绘
        infoPanel.repaint();
    }
    

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("当前有事件发生");
        try {
            if (e.getSource() == controlBtn)
            {
                //展示已掌握的单词
                System.out.println("当前即将展示已掌握的单词");
                showInfo(true);
            }
            else if (e.getSource() == uncontrolBtn)
            {
                //展示未掌握的单词
                showInfo(false);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

}
