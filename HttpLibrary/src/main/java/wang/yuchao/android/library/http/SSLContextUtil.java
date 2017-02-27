package wang.yuchao.android.library.http;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by wangyuchao on 17/1/18.
 */
public class SSLContextUtil {
    public static HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    public static SSLContext getSSLContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)  {}

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {}

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext;
    }

//    public static SSLContext getSSLContext() {
//        // 生成SSLContext对象
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        // 从assets中加载证书
//        InputStream inStream = Application.getInstance().getAssets().open("srca.cer");
//
//        // 证书工厂
//        CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");
//        Certificate cer = cerFactory.generateCertificate(inStream);
//
//        // 密钥库
//        KeyStore kStore = KeyStore.getInstance("PKCS12");
//        kStore.load(null, null);
//        kStore.setCertificateEntry("trust", cer);// 加载证书到密钥库中
//
//        // 密钥管理器
//        KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//        keyFactory.init(kStore, null);// 加载密钥库到管理器
//
//        // 信任管理器
//        TrustManagerFactory tFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//        tFactory.init(kStore);// 加载密钥库到信任管理器
//
//        // 初始化
//        sslContext.init(keyFactory.getKeyManagers(), tFactory.getTrustManagers(), new SecureRandom());
//        return sslContext;
//    }
}
