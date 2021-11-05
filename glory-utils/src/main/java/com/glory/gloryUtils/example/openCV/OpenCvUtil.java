package com.glory.gloryUtils.example.openCV;

import com.glory.gloryUtils.utils.PathUtil;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class OpenCvUtil {
    public static void main(String[] args) {
        loadLibrary();
        VideoCapture capture = new VideoCapture(0);
        Mat matrix = new Mat();
        while (capture.isOpened()){
            capture.read(matrix);
            byte[] bytes=matToByte(matrix,".jpg");
            showImg(bytes);
        }


    }

    public static void main1(String[] args) {
        String str = "C:\\Users\\Administrator\\Pictures\\140\\1.jpg";
        String str2 = PathUtil.getDesktopPathAndSeparatorChar() + "1_1.jpg";
        String str3 = PathUtil.getDesktopPathAndSeparatorChar() + "Snipaste_2021-08-10_17-00-18.png";
        loadLibrary();
        Mat mat = imread(new File(str));
        System.err.println(mat.cols());
        System.err.println(mat.rows());
//        byte[] bytes = matToByte(mat, ".jpg");
//        System.err.println(HexUtil.toHex(bytes));
//        showImg(bytes);
        Mat dstMat = colorRgb2gray(mat);
//        Imgproc.blur(mat, dstMat, new Size(10d,10d), new Point(), Core.BORDER_DEFAULT);
        Imgproc.GaussianBlur(mat, dstMat, new Size(45, 45), 0);
        byte[] bytes = matToByte(dstMat, ".jpg");
        showImg(bytes);

    }


    /**
     * 彩色图像转化为灰度图像
     *
     * @param mat
     * @return
     */
    public static Mat colorRgb2gray(Mat mat) {
        Mat dstMat = new Mat();
        Imgproc.cvtColor(mat, dstMat, Imgproc.COLOR_RGB2GRAY);
        return dstMat;
    }

    /**
     * 灰度图像转为二进制图像
     *
     * @param mat
     * @return
     */
    public static Mat colorGry2binary(Mat mat) {
        Mat dstMat = new Mat();
        Imgproc.threshold(mat, dstMat, 200, 500, Imgproc.THRESH_BINARY);
        return dstMat;
    }

    /**
     * jwt展示图片
     *
     * @param imgBytes
     */
    public static void showImg(byte[] imgBytes) {
        try {
            InputStream inputStream = new ByteArrayInputStream(imgBytes);
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            /*
            show
             */
            JFrame frame = new JFrame();
            frame.getContentPane().add(new JLabel(new ImageIcon(bufferedImage)));
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 矩阵转换为字节矩阵
     *
     * @param mat
     * @param imgSuffix [.jpg,]
     * @return
     */
    public static byte[] matToByte(Mat mat, String imgSuffix) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(imgSuffix, mat, matOfByte);
        return matOfByte.toArray();
    }

    /**
     * 写入文件
     *
     * @param file
     * @param mat
     */
    public static void imwrite(File file, Mat mat) {
        new Imgcodecs().imwrite(file.getAbsolutePath(), mat);
    }

    /**
     * 加载文件
     *
     * @param file
     * @return
     */
    public static Mat imread(File file) {
        return new Imgcodecs().imread(file.getAbsolutePath());
    }

    /**
     * 加载opencv本地库
     */
    public static void loadLibrary() {
        //Loading the OpenCV core library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}
