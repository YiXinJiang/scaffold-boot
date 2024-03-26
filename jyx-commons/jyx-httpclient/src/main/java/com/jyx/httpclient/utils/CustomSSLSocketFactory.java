package com.jyx.httpclient.utils;

import com.jyx.httpclient.constant.CustomConstant;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @ClassName: CustomSSLSocketFactory
 * @Description: CustomSSLSocketFactory 自定义Socket工厂类
 * @Author: pengmingming
 * @Date: 2024-03-04 17:21
 * @Version: 1.0
 **/
public class CustomSSLSocketFactory extends SSLSocketFactory {
    private final SSLSocketFactory delegate;

    public CustomSSLSocketFactory(SSLSocketFactory delegate) {
        this.delegate = delegate;
    }

    /**
     * 返回默认启用的密码套件,除非一个列表启用,对SSL连接的握手会使用这些密码套件
     * 这些默认的服务的最低质量要求保密保护和服务器身份验证
     * */
    @Override
    public String[] getDefaultCipherSuites() {
        return delegate.getDefaultCipherSuites();
    }

    /**
     * 返回的密码套件可用于SSL连接启用的名字
     * */
    @Override
    public String[] getSupportedCipherSuites() {
        return delegate.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(final Socket socket, final String host, final int port, final boolean autoClose)
            throws IOException {
        final Socket underlyingSocket = delegate.createSocket(socket, host, port, autoClose);
        return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(final String host, final int port) throws IOException {
        final Socket underlyingSocket = delegate.createSocket(host, port);
        return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(final String host, final int port, final InetAddress localAddress, final int localPort)
            throws IOException {
        final Socket underlyingSocket = delegate.createSocket(host, port, localAddress, localPort);
        return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(final InetAddress host, final int port) throws IOException {
        final Socket underlyingSocket = delegate.createSocket(host, port);
        return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(final InetAddress host, final int port, final InetAddress localAddress, final int localPort)
            throws IOException {
        final Socket underlyingSocket = delegate.createSocket(host, port, localAddress, localPort);
        return overrideProtocol(underlyingSocket);
    }

    private Socket overrideProtocol(final Socket socket) {
        if (socket instanceof SSLSocket) {
            ((SSLSocket)socket).setEnabledProtocols(new String[]{CustomConstant.CUSTOM_SSL_SOCKET_PROTOCOL_V1,
                    CustomConstant.CUSTOM_SSL_SOCKET_PROTOCOL_V1_1,CustomConstant.CUSTOM_SSL_SOCKET_PROTOCOL_V1_2});
            return socket;
        }
        throw new RuntimeException(CustomConstant.CUSTOM_SSL_SOCKET_SOCKET_EXCEPTION);
    }
}
