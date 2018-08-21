import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Administrator
 */
public class Client {
    private OutputStream out;
    private InputStream in;
    private Socket socket;
    private byte[] bos=new byte[2024];
    //private static ByteArrayOutputStream baos;
    private static byte[] bis=new byte[2024];

    public Client() {
        startClient();
    }

    private void startClient() {
        try {
            //这里需要根据自己的ip修改
            socket = new Socket("localhost", 9000);

            out = socket.getOutputStream();
            System.out.println("客户端:连接成功");
            // 保持通讯
            in = socket.getInputStream();

            TargetDataLine targetDataLine = AudioUtils.getTargetDataLine();

            SourceDataLine sourceDataLine = AudioUtils.getSourceDataLine();
            while (true) {
                System.out.println("Client:");

                //发
                //获取音频流
                int writeLen = targetDataLine.read(bos,0,bos.length);
                sendSound(writeLen,bos,out);

                //收
                //获取输入的音频流
                int readLen = in.read(bis);
                getSound(readLen,bis,sourceDataLine);


            }

        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private void sendSound(int writeLen,byte[] bos,OutputStream out ) throws IOException {
        if (bos != null) {
            //向对方发送拾音器获取到的音频
            System.out.println("Client 发");
            out.write(bos,0,writeLen);
        }
    }

    private void getSound(int readLen,byte[] bis,SourceDataLine sourceDataLine ) throws IOException {
        if (bis != null) {
            //播放对方发送来的音频
            System.out.println("Client 收");
            //语音降噪代码，未开发成功
//            DoubleByteUtils doubleByteUtils = new DoubleByteUtils();
//            bis = doubleByteUtils.doubles2bytes(MyFliter.useFliter(doubleByteUtils.bytes2DoubleArrays(bis)));
            sourceDataLine.write(bis, 0, readLen);
        }
    }




    public static void main(String args[]) {
        new Client();
    }
}