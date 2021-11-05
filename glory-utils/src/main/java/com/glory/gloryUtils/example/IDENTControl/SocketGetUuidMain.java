package com.glory.gloryUtils.example.IDENTControl;


import com.glory.gloryUtils.utils.HexUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketGetUuidMain {

    public static void main(String[] args) {
        Socket socket = null;
        try {
            // 向本机的4700端口发出客户请求
            socket = new Socket("192.168.31.250", 10000);
            OutputStream outputStream = socket.getOutputStream();
            byte[] efBytes = new byte[]{0x00, 0x04, 0x1d, 0x02};
            outputStream.write(efBytes);
            outputStream.flush();

            while (true) {
                InputStream inputStream = socket.getInputStream();
                byte[] bytes = new byte[1024];
                inputStream.read(bytes);
                String uuid = HexUtil.toHex(bytes);
                System.err.println(uuid);
                if (uuid.startsWith(HexUtil.toHex(new byte[]{0x00, 0x0b, 0x1d, 0x02, 0x00}))) {//报文长度高位，报文长度低位，命令，通道，0x00
                    uuid = uuid.substring(12, 22);
                    System.err.println(uuid);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.getOutputStream().close();
                    socket.getInputStream().close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
