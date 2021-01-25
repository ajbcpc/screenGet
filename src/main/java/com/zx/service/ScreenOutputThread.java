package com.zx.service;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ScreenOutputThread extends Thread {

    private DataOutputStream dataOutputStream ;

    public ScreenOutputThread(DataOutputStream outputStream){
        dataOutputStream = outputStream;
    }

    public void run(){
        //获取屏幕分辨路
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();

        try {
            dataOutputStream.writeDouble(screenSize.getHeight());
            dataOutputStream.writeDouble(screenSize.getWidth());
            dataOutputStream.flush();

            //定义分享屏幕大小

            Rectangle rectangle = new Rectangle(screenSize);
            Robot robot = new Robot();
            while (true){
                BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                //压缩
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos);
                encoder.encode(bufferedImage);

                byte[] bytes = baos.toByteArray();
                dataOutputStream.writeInt(bytes.length);
                dataOutputStream.write(bytes);
                dataOutputStream.flush();

                Thread.sleep(500);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
