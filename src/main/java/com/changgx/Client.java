package com.changgx;

/**
 * Created by Administrator on 2016/12/6.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 客户端
 */
public class Client extends Thread {
    /**
     * 请求的host地址
     */
    private String host;
    /**
     * 请求的端口
     */
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            //获取键盘输入
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            //readline() 会一直等待键盘输入，只有输入后才会去创建socket连接
            //如果先创建socket，会导致运行mian方法的时候，对方的server还没有启动，这边就去请求，会报错
            //所以使用readline()来阻塞
            String line = input.readLine();
            //创建socket连接
            Socket socket = new Socket(host, port);
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            //输入quit，退出客户端
            while (!"quit".equalsIgnoreCase(line)) {
                pw.println(line);
                pw.flush();
                line = input.readLine();
            }
            pw.println(line);
            pw.close();
            input.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Thread server = new Server(10001);
        Thread client = new Client("localhost",10000);
        server.start();
        client.start();
    }
}
