package com.minlc.demo.laboratory.itext;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2020-08-14
 **/
public class StreamUtils {

    /**
     * @param url
     * @return
     * @throws IOException
     */
    public static byte[] getBytes(String url) throws IOException {

        InputStream inputStream = getInputStream(url);
        try {
            if (null != inputStream) {
                byte[] pdfBytes = new byte[inputStream.available()];
                inputStream.read(pdfBytes);
                return pdfBytes;
            }
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }
        return null;
    }

    /**
     * http://flm-tra-asset.oss-ap-southeast-1.aliyuncs.com/loan_contract.pdf
     *
     * @param url
     * @return
     */
    private static InputStream getInputStream(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                return inputStream;
            }
        } catch (IOException e) {
            System.out.println("获取网络图片出现异常，图片路径为：" + url);
            e.printStackTrace();
        }
        return null;
    }


}
