package com.jyx.httpclient.utils;

import com.jyx.httpclient.constant.CustomConstant;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * @ClassName: CustomClientRequestFactory
 * @Description: CustomClientRequestFactory 自定义客户端请求工厂类
 * @Author: pengmingming
 * @Date: 2024-03-04 17:09
 * @Version: 1.0
 **/
public class CustomClientRequestFactory extends SimpleClientHttpRequestFactory {
    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        if (connection instanceof HttpsURLConnection) {
            try {
                HttpsURLConnection httpsConnection = (HttpsURLConnection)connection;
                TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }
                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
                };
                SSLContext sslContext = SSLContext.getInstance(CustomConstant.CUSTOM_SSL_SOCKET_PROTOCOL);
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                httpsConnection.setSSLSocketFactory(new CustomSSLSocketFactory(sslContext.getSocketFactory()));
                httpsConnection.setHostnameVerifier((s, sslSession) -> true);
                super.prepareConnection(httpsConnection, httpMethod);
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                throw new RuntimeException(e);
            }
        } else {
            super.prepareConnection(connection, httpMethod);
        }
    }
}
