package com.cj.gongju;

import com.cj.common.tuple.Tuple;
import com.cj.common.tuple.Tuple2;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 功能:
 *
 * @author miracle
 */
public class HttpUtils {
    private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

    private static final int CONN_TIME_OUT = 20000; // 20秒
    private static final int SOCK_TIME_OUT = 60000; // 60秒

    private static final RequestConfig DEFULT_CONFIG = RequestConfig.custom()
            .setConnectTimeout(CONN_TIME_OUT).setSocketTimeout(SOCK_TIME_OUT).build();

    private static final int RETRY_NUM = 3;

    /**
     * 实现功能：GET 请求
     * @param url
     * @param param
     * @return [responseCode, obj]  EntityUtils.toString(response.getEntity(), "UTF-8")
     *        -1：异常, obj: str
     *        实际：200， obj: 数据
     *             非200，obj: 异常
     *             <code>org.apache.http.HttpStatus#SC_OK</code>
     * @author miracle
     */
    public static Tuple2<Integer, byte[]> doGet(String url, Map<String, String> param) {
        LOG.debug("HttpUtils.doGet -> url:{}, param:{}.", url, param);
        long startTime = System.currentTimeMillis();

        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            // 创建http GET请求
            URIBuilder builder = new URIBuilder(url);
            if (MapUtils.isNotEmpty(param)) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(DEFULT_CONFIG);
            
            httpclient = HttpClients.createDefault();

            // 执行请求
            response = httpclient.execute(httpGet);
            LOG.debug("doGet cost:{} ms.", (System.currentTimeMillis() - startTime));

            // 成功200
            return Tuple.of(response.getStatusLine().getStatusCode(), EntityUtils.toByteArray(response.getEntity()));
        } catch (Exception e) {
            LOG.error("doGet exception:{}.", e.getMessage(), e);
            return Tuple.of(-1, ("request exception:" + e.getMessage()).getBytes(Charset.forName("UTF-8")));
        } finally {
            StreamUtils.closeStream(response);
            StreamUtils.closeStream(httpclient);
        }
    }

    @Deprecated
    public static String doPostForm(String url, Map<String, String> param, File file) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null && file.exists()) {
                FileBody content = new FileBody(file);
                MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create()
                        .addPart("file", content);
                for (String key : param.keySet()) {
                    entityBuilder.addTextBody(key,param.get(key));
                }
                HttpEntity entity = entityBuilder.build();
                // 模拟表单
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 实现功能：POST 请求
     *
     * @author miracle
     */
    public static byte[] doPostWithRetry(String url, String jsonParam) {
        for (int i = 0; i < RETRY_NUM; i++) {
            Tuple2<Integer, byte[]> postResp = doPostInternal(url, jsonParam);

            Integer httpStatus = postResp._1().get();
            if (httpStatus != HttpStatus.SC_OK) {
                // 提交失败
                LOG.error("status from url[{}] is [{}] in [{}] time.", url, httpStatus, i);
                ThreadUtils.threadSleep(2000);
                continue;
            }

            return postResp._2().get();
        }

        throw new RuntimeException("url[" + url + "] over 3 time.");
    }


    private static Tuple2<Integer, byte[] > doPostInternal (String url, String jsonParam) {
        LOG.debug("HttpUtils.doPost -> url:{}, jsonParam:{}.", url, jsonParam);
        long startTime = System.currentTimeMillis();

        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            if (StringUtils.isNotEmpty(jsonParam)) {
                httpPost.setEntity(new StringEntity(jsonParam, ContentType.APPLICATION_JSON));
            }

            httpPost.setConfig(DEFULT_CONFIG);

            httpclient = HttpClients.createDefault();
            // 执行http请求
            response = httpclient.execute(httpPost);
            LOG.debug("doPost cost:{} ms.", (System.currentTimeMillis() - startTime));

            // 成功200
            return Tuple.of(response.getStatusLine().getStatusCode(), EntityUtils.toByteArray(response.getEntity()));

        } catch (Exception e) {
            LOG.error("doPost exception:{}.", e.getMessage(), e);
            return Tuple.of(-1, ("request exception:" + e.getMessage()).getBytes(Charset.forName("UTF-8")));
        } finally {
            StreamUtils.closeStream(response);
            StreamUtils.closeStream(httpclient);
        }
    }
}
