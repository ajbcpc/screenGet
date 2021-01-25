package com.zx.service;

import sun.awt.image.JPEGImageDecoder;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;

public class Client {

    public static void main(String args[]) throws  Exception{
        //询问框
        int choice = JOptionPane.showConfirmDialog(null, "请求控制对方电脑", "是否控制对方电脑",JOptionPane.YES_NO_CANCEL_OPTION);

        if(choice == JOptionPane.NO_OPTION ||  choice == JOptionPane.CANCEL_OPTION){
            return;
        }
        //输入URL
        String URL = JOptionPane.showInputDialog("请输入对方的URL", "127.0.0.1:10000");
        //ip
        String ip = URL.substring(0, URL.indexOf(":"));
        //port
        String port = URL.substring(URL.indexOf(":")+1, URL.length());
//        System.out.println("ip = "+ip+"，port = "+port);
        Socket socket = new Socket(ip, Integer.parseInt(port));
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        //创建显示框
        JFrame jFrame = new JFrame("远程监控001");
        jFrame.setDefaultCloseOperation(3);
        //屏幕分辨率
        double height = dataInputStream.readDouble();
        double width = dataInputStream.readDouble();
        Dimension dimension = new Dimension((int)height, (int)width);
        jFrame.setSize(dimension);
        jFrame.setLocationRelativeTo(null);
        jFrame.setAlwaysOnTop(true);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        JScrollPane jScrollPane = new JScrollPane(jPanel);
        JLabel jLabel = new JLabel();
        jPanel.add(jLabel);
        jFrame.add(jScrollPane);

        jFrame.setVisible(true);

        while(true){
            //获取压缩后的图片长度
            int length = dataInputStream.readInt();

            byte[] bytes = new byte[length];
            dataInputStream.readFully(bytes);

            ImageIcon imageIcon = new ImageIcon(bytes);
            jLabel.setIcon(imageIcon);

            jFrame.repaint();
        }
    }

}
