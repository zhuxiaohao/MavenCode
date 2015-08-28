package library.util;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

public class SSLSocketFactoryEx extends org.apache.http.conn.ssl.SSLSocketFactory {

    javax.net.ssl.SSLContext sslContext = javax.net.ssl.SSLContext.getInstance("TLS");
    public SSLSocketFactoryEx(java.security.KeyStore truststore) throws java.security.NoSuchAlgorithmException, java.security.KeyManagementException, java.security.KeyStoreException, java.security.UnrecoverableKeyException {
        super(truststore);
        javax.net.ssl.TrustManager tm = new javax.net.ssl.X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
            }
            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
            }
        };
        sslContext.init(null, new javax.net.ssl.TrustManager[] { tm }, null);
    }
    @Override
    public java.net.Socket createSocket(java.net.Socket socket, String host, int port, boolean autoClose) throws java.io.IOException, java.net.UnknownHostException {
        return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
    }
    @Override
    public java.net.Socket createSocket() throws java.io.IOException {
        return sslContext.getSocketFactory().createSocket();
    }
}
