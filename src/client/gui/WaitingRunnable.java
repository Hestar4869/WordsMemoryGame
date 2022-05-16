package client.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @className: WaitingRunnable
 * @description: 匹配时的等待对话框
 * @author: HMX
 * @date: 2022-05-13 18:45
 */
public class WaitingRunnable extends JDialog implements Runnable
{

    JPanel panel=new JPanel();
    JLabel label=new JLabel();
    String msg="当前正在匹配，请等待";
    boolean isRunning=true;
    //构造函数
    public WaitingRunnable(Frame owner)
    {
        super(owner);
        this.setSize(200,100);
        label.setText(msg);
        panel.add(label);
        this.setContentPane(panel);

        //Dimension封装了电脑屏幕的宽度和高度
        //获取屏幕宽度和高度，使窗口位于屏幕正中间
        Dimension width = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (width.getWidth() - 200) / 2, (int) (width.getHeight() - 100) / 2);
    }

    @Override
    public void run()
    {

        this.setVisible(true);
        int count=0;
        String dot=".";
        while (isRunning){
            label.setText(msg+dot.repeat(count));
            count++;
            count%=10;

            try
            {
                //等待
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.dispose();
    }
    public void stop(){
        isRunning=false;
    }
    public static void main(String[] args)
    {
        WaitingRunnable waitingRunnable=new WaitingRunnable(new JFrame());
        Thread t1=new Thread(waitingRunnable);
        t1.start();
        new Thread(new WaitingRunnable(new JFrame())).start();
        try
        {
            Thread.sleep(3000);
            waitingRunnable.stop();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
