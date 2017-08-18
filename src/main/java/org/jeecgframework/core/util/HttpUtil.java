package org.jeecgframework.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);


    /**
     * POST请求，字符串形式数据
     *
     * @param url     请求地址
     * @param param   请求数据
     * @param charset 编码方式
     */
    public static String sendUrl(String url, String param, String requestMethod, String charset) {

        PrintWriter out = null;
        BufferedReader in = null;
        HttpURLConnection conn = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);

            conn.setDoInput(true);

            if ("GET".equals(requestMethod))
                conn.connect();

            if (!StringUtil.isEmpty(requestMethod)) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param.getBytes(charset));
                // flush输出流的缓冲
                out.flush();
            }

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("http request error:{}" + e.getMessage());
            e.printStackTrace();
        } catch (MalformedURLException e) {
            logger.info("http request error:{}" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.info("http request error:{}" + e.getMessage());
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                conn.disconnect();
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.info("http close error:{}" + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String respose = sendUrl("http://14.23.71.164:50114/cmis/CMISHttpService4Channel","","POST","utf-8");
        System.err.println(respose);
    }

}
