

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * 主要实现局域网通讯中服务端的功能
 *
 * @author Administrator
 */
public class Server {
    private OutputStream out;
    private InputStream in;
    private ServerSocket serverSocket;
    private Socket socket;
    //private int counter = 1;
    private byte[] bos=new byte[2024];
    //private static ByteArrayOutputStream baos;
    private  byte[] bis=new byte[2024];

    public Server() {

        startServer();

    }

    private void startServer() {
        try {
            socketConection();
            out = socket.getOutputStream();
            // out.flush();
            System.out.println("服务端：连接成功");
            // 保持通讯
            in = socket.getInputStream();

            TargetDataLine targetDataLine = AudioUtils.getTargetDataLine();
            SourceDataLine sourceDataLine = AudioUtils.getSourceDataLine();

            /**
             * 这里一定要先发再收  不然socket的读取流会阻塞
             */
            while (true) {
                System.out.println("server:");
                //发 ,获取音频流
                int writeLen = targetDataLine.read(bos,0,bos.length);
                sendSound(writeLen);
                //收, 获取输入的音频流
                int readLen = in.read(bis);
                getSound(readLen,sourceDataLine);
            }

        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void socketConection() throws IOException {
        serverSocket = new ServerSocket(9000, 20);
        // 等待连接
        System.out.println("服务端:等待连接");
        socket = serverSocket.accept();
    }


    private void sendSound(int writeLen) throws IOException {
        if (bos != null) {
            //向对方发送拾音器获取到的音频
            System.out.println("rerver 发");
            out.write(bos,0,writeLen);
        }
    }

    private void getSound(int readLen,SourceDataLine sourceDataLine ) throws IOException {
        if (bis != null) {
            System.out.println("server 收");
//语音降噪功能，失败
//            DoubleByteUtils doubleByteUtils = new DoubleByteUtils();
//            bis = doubleByteUtils.doubles2bytes(MyFliter.useFliter(doubleByteUtils.bytes2DoubleArrays(bis)));
            sourceDataLine.write(bis, 0, readLen);
        }
    }

    public static void main(String args[]) {
        new Server();
    }

}