package com.glory.gloryUtils.example.IDENTControl;



import com.glory.gloryUtils.utils.HexUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 */
public class EfidTool {
    private static Socket device_client = null;
    private static OutputStream device_client_output_stream = null;
    private static InputStream device_client_input_stream = null;
    public static ConcurrentLinkedQueue<String> ef_queue = new ConcurrentLinkedQueue<>();

    /**
     * 关闭设备连接，即关闭TCP/IP连接
     *
     * @return
     */
    public static void closeDeviceClient() {
        if (device_client == null) {
        } else if (device_client.isClosed()) {
            device_client = null;
        } else {
            try {
                if (device_client.isInputShutdown() == true) {
                } else {
                    device_client.getOutputStream().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                device_client_input_stream = null;
            }
            try {
                if (device_client.isOutputShutdown() == true) {

                } else {
                    device_client.getInputStream().close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                device_client_output_stream = null;
            }
            try {
                device_client.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                device_client = null;
            }
        }
    }

    /**
     * 连接设备
     *
     * @param deviceIp 设备IP （设备IP是可以设置的，端口是固定的10000）
     * @return
     */
    private static boolean connectDevice(String deviceIp) {

        // 向本机的4700端口发出客户请求
        try {
            device_client = new Socket(deviceIp, 10000);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("deviceIp错误；" + e.getMessage());
        } catch (IOException e) {
            throw new IllegalArgumentException("使用TCP/IP连接设备出错，请重启设备后重试；" + e.getMessage());
        }
        try {
            device_client_output_stream = device_client.getOutputStream();
        } catch (IOException e) {
            closeDeviceClient();
            throw new IllegalArgumentException("使用TCP/IP连接设备成功，但获取设备相对应的客户端输出流失败，请重启设备后重试；" + e.getMessage());
        }
        try {
            device_client_input_stream = device_client.getInputStream();
        } catch (IOException e) {
            closeDeviceClient();
            throw new IllegalArgumentException("使用TCP/IP连接设备成功，但获取设备相对应的客户端输入流失败，请重启设备后重试；" + e.getMessage());
        }
        return device_client.isConnected();
    }

    /**
     * 发送EF命令
     *
     * @return
     */
    private static boolean sendEfCommand() {
        if (device_client == null || device_client_output_stream == null || device_client_input_stream == null) {
            throw new IllegalArgumentException("请先调用connectDevice方法，进行初始化");
        }
        byte[] efBytes = new byte[]{0x00, 0x04, 0x1d, 0x02};//报文长度高位，报文长度低位，命令，通道
        try {
            device_client_output_stream.write(efBytes);
            device_client_output_stream.flush();
            /*
            confirmation  Byte4==FF
             */
            byte[] confirmationBytes = new byte[1024];
            device_client_input_stream.read(confirmationBytes);
            if (
                    efBytes[2] == confirmationBytes[2]//echo Command Code
                            && efBytes[3] == confirmationBytes[3] //echo Channel
                            && (byte) 0xff == confirmationBytes[4] //echo Status FFh  is right
            ) {
//                System.err.println(HexUtil.toHex(confirmationBytes));
            } else {
                closeDeviceClient();
                throw new IllegalArgumentException("发送EF命令的后，返回的确认命令信息错误。请联系编码人员！" + HexUtil.toHex(confirmationBytes));
            }
            /*
            response  Byte4==status  更具status判断指令是否执行成功
             */
            byte[] responseBytes = new byte[1024];
            device_client_input_stream.read(responseBytes);
//            System.err.println(HexUtil.toHex(responseBytes));
            if (efBytes[2] == confirmationBytes[2]//echo Command Code
                    && efBytes[3] == confirmationBytes[3] //echo Channel
            ) {
                String resByte4Hex = HexUtil.toHex(new byte[]{responseBytes[4]});
                String message = null;
                switch (resByte4Hex) {
                    case "01": {
                        message = "The battery of the read/write tag is weak.";
                    }
                    break;
                    case "02": {
                        message = "Reserved";
                    }
                    break;
                    case "03": {
                        message = "Reserved";
                    }
                    break;
                    case "04": {
                        message = "Incorrect or incomplete command or parameter not in the valid range.";
                    }
                    break;
                    case "05": {
                        message = "No data carrier in the detection range.";
                    }
                    break;
                    case "06": {
                        message = "Hardware error, e.g. error during self-test or R/W head defect.";
                    }
                    break;
                    case "07": {
                        message = "Internal device error.";
                    }
                    break;
                    case "08": {
                        message = "Reserved";
                    }
                    break;
                    case "09": {
                        message = "The parameterized tag type is not compatible with the connected reading head.";
                    }
                    break;
                    case "0A": {
                        message = "Several tags in the detection range (UHF).";
                    }
                    break;
                    case "0B": {
                        message = "Reserved";
                    }
                    break;
                    case "0C": {
                        message = "Reserved";
                    }
                    break;
                    case "0D": {
                        message = "Reserved";
                    }
                    break;
                    case "0E": {
                        message = "Internal buffer overflow.";
                    }
                    break;
                    case "0F": {
                        message = "Reserved";
                    }
                    break;
                }
                if ("05".equals(resByte4Hex)) {
//                    正常返回
                } else {
                    closeDeviceClient();
                    throw new IllegalArgumentException("发送EF命令的后，返回的response信息错误中的状态错误;" +
                            "正确值为05，当前值" + resByte4Hex + "。" + message);
                }
            } else {
                closeDeviceClient();
                throw new IllegalArgumentException("发送EF命令的后，返回的response信息错误。请联系编码人员！");
            }
        } catch (SocketException e) {
            closeDeviceClient();
            throw new IllegalArgumentException("建立连接失败，当前设备只支持建立一条TCP/IP连接，存在其他连接没有关闭；" +
                    "建议关闭其他连接，或重启设备后重试");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        读取卡片
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    byte[] bytes = new byte[1024];
                    try {
                        device_client_input_stream.read(bytes);
                    } catch (IOException e) {
                        closeDeviceClient();
                        throw new IllegalArgumentException("读取信息失败" + e.getMessage());
                    }
                    String uuid = HexUtil.toHex(bytes);
                    if (uuid.startsWith(HexUtil.toHex(new byte[]{0x00, 0x0b, 0x1d, 0x02, 0x00}))) {//报文长度高位，报文长度低位，命令，通道，0x00
                        uuid = uuid.substring(12, 22);
                        ef_queue.add(uuid);
                    }
                }
            }
        }).start();
        return true;
    }

    /**
     * 外部使用接口用于启用ef命令
     *
     * @param deviceIp
     */
    public static void initDeviceAndSendEfCommand(String deviceIp) {
        connectDevice(deviceIp);
        sendEfCommand();
    }

    public static void main(String[] args) {
        String deviceIp = "192.168.31.250";
        initDeviceAndSendEfCommand(deviceIp);
        while (true) {
            if (ef_queue.size() > 0) {
                System.err.println(ef_queue.remove());
                // TODO: 2021/6/7 业务逻辑
            }
        }
        // TODO: 2021/6/7 关闭项目的时候，尽量调用一下 EfidTool.closeDeviceClient();

    }
}
