package com.glory.gloryApiConsumeFeign.config;

import feign.Feign;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.net.ssl.*;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@PropertySource(value = "classpath:application-apiConsumeFeign.properties")
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignOkHttpConfig {

    /**
     * 整个程序共用一个client
     */
    private static OkHttpClient okHttpClient = null;

    @Bean
    public okhttp3.OkHttpClient okHttpClient() {
        if (okHttpClient == null) {
            try {
                okHttpClient = new OkHttpClient.Builder()
                        .sslSocketFactory(createSSLSocketFactory())
                        .hostnameVerifier(new TrustAllHostnameVerifier())
                        .connectionPool(new ConnectionPool(10, 1, TimeUnit.MINUTES))// 连接池，最大空闲连接10个，每一个最多空闲1分钟
                        .connectTimeout(10, TimeUnit.SECONDS)// 连接超时
                        .writeTimeout(20, TimeUnit.SECONDS)// 写超时
                        .readTimeout(30, TimeUnit.SECONDS)// 读取超时
                        .retryOnConnectionFailure(true)// 连接失败自动尝试重新连接
                        // .cache(new Cache(new File(getCacheMap().get(CACHE_FILE_NAME)),
                        // Integer.valueOf(getCacheMap().get(CACHE_FILE_SIEZE))))// 缓存设置
//                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)))
                        .build();// 复用连接+连接池+响应缓存
            } catch (UnsupportedOperationException e) {
                okHttpClient = new OkHttpClient.Builder()
                        .sslSocketFactory(getSSLSocketFactory(), getX509TrustManager())
                        .hostnameVerifier(getHostnameVerifier())
                        .connectionPool(new ConnectionPool(10, 1, TimeUnit.MINUTES))// 连接池，最大空闲连接10个，每一个最多空闲1分钟
                        .connectTimeout(10, TimeUnit.SECONDS)// 连接超时
                        .writeTimeout(20, TimeUnit.SECONDS)// 写超时
                        .readTimeout(30, TimeUnit.SECONDS)// 读取超时
                        .retryOnConnectionFailure(true)// 连接失败自动尝试重新连接
                        // .cache(new Cache(new File(getCacheMap().get(CACHE_FILE_NAME)),
                        // Integer.valueOf(getCacheMap().get(CACHE_FILE_SIEZE))))// 缓存设置
//                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)))
                        .build();// 复用连接+连接池+响应缓存
            }
        }
        return okHttpClient;
    }

    //============================== JDK 8==============================================
    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ssfFactory;
    }

    /**
     * 强制信任所有证书
     */
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    //============================== JDK 8==============================================
    //============================== JDK 9+==============================================
    //获取这个SSLSocketFactory
    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //获取TrustManager
    private static TrustManager[] getTrustManager() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
    }

    //获取HostnameVerifier
    private static HostnameVerifier getHostnameVerifier() {
        return (s, sslSession) -> true;
    }

    private static X509TrustManager getX509TrustManager() {
        X509TrustManager trustManager = null;
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            trustManager = (X509TrustManager) trustManagers[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trustManager;
    }

    //============================== JDK 9+==============================================

}