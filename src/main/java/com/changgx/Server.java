package com.changgx;

/**
 * Created by Administrator on 2016/12/6.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 */
public class Server extends Thread {

    /**
     * 监听端口
     */
    private int port;

    public Server(int port) {
        this.port=port;
    }

    @Override
    public void run() {
        try {
            //设置监听端口
            ServerSocket server = new ServerSocket(port);
            //开始监听端口，没有连接，那么该方法会阻塞
            Socket socket = server.accept();
            //获取信息
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = null;
            while (!"quit".equalsIgnoreCase(line = br.readLine())) {
                System.out.println("ta :" + line);
            }
            socket.close();
            server.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread server = new Server(10000);
        Thread client = new Client("localhost",10001);
        server.start();
        client.start();
    }
}
