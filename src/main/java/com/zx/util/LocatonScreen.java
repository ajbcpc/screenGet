package com.zx.util;

import com.sun.deploy.panel.JavaPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LocatonScreen {

    public static void main(String args[]) throws Exception{
        //询问框
        int choice = JOptionPane.showConfirmDialog(null, "请求控制对方电脑", "是否控制对方电脑",JOptionPane.YES_NO_CANCEL_OPTION);

        if(choice == JOptionPane.NO_OPTION){
            return;
        }
        //输入URL
        String URL = JOptionPane.showInputDialog("请输入对方的URL", "127.0.0.1:10000");

        JFrame jFrame = new JFrame("远程监控");
        jFrame.setDefaultCloseOperation(3);
        jFrame.setSize(600,600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setAlwaysOnTop(true);
        JLabel jPanel = new JLabel();
        jFrame.add(jPanel);

        jFrame.setVisible(true);

        //获取屏幕大小
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();

        Robot robot = new Robot();
        while(true){
            //指定分享的框
            Rectangle rectangle = new Rectangle(jFrame.getWidth(), 0, (int)screenSize.getWidth()-jFrame.getWidth(), (int)screenSize.getHeight());
            BufferedImage screenCapture = robot.createScreenCapture(rectangle);
            jPanel.setIcon(new ImageIcon(screenCapture));
        }
    }
}
