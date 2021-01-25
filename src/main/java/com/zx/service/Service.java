package com.zx.service;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Service {

    public static void main(String args[]) throws Exception{

        ServerSocket serverSocket = new ServerSocket(10000);
        System.out.println("正在等待连接。。。");

        Socket socket = serverSocket.accept();
        System.out.println("服务器连接成功");

        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        ScreenOutputThread scr = new ScreenOutputThread(dataOutputStream);
        scr.start();

    }




}
