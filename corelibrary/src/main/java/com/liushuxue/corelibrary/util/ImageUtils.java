package com.liushuxue.corelibrary.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 图片处理类
 */
public class ImageUtils {


    /**
     * 下载文件保存到本地
     *
     * @param inputStream 输入流
     * @param filePath    保存文件路径
     */
    public static Boolean saveFileByInputStream(InputStream inputStream, String filePath) {
        try {
            File futureStudioIconFile = new File(filePath);
            OutputStream outputStream = new FileOutputStream(futureStudioIconFile);
            byte[] fileReader = new byte[4096];
            while (true) {
                int read = inputStream.read(fileReader);
                if (read == -1) {
                    break;
                }
                outputStream.write(fileReader, 0, read);
            }
            outputStream.flush();
            inputStream.close();
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {

            return false;
        } catch (IOException e) {
            return false;
        }
    }

}
