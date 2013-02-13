
package com.notnoop.apns.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notnoop.apns.ApnsDelegate;
import com.notnoop.apns.ApnsNotification;
import com.notnoop.apns.DeliveryError;
import com.notnoop.apns.ReconnectPolicy;
import com.notnoop.apns.SimpleApnsNotification;
import com.notnoop.exceptions.NetworkIOException;

public class ApnsConnectionImpl implements ApnsConnection {
    private static final Logger logger = LoggerFactory.getLogger(ApnsConnectionImpl.class);

    private final SocketFactory factory;
    private final String host;
    private final int port;

    private final Proxy proxy;
    private final ReconnectPolicy reconnectPolicy;
    private final ApnsDelegate delegate;
    private final boolean errorDetection;

    // proxySocket should be reinitialized at every reconnection, to protect
    // any stale connection issues.  This field is only needed so we can
    // close the connection properly
    private Socket proxySocket;

    public ApnsConnectionImpl(SocketFactory factory, String host, int port) {
        this(factory, host, port, new ReconnectPolicies.Never(), ApnsDelegate.EMPTY);
    }

    public ApnsConnectionImpl(SocketFactory factory, String host,
            int port, ReconnectPolicy reconnectPolicy,
            ApnsDelegate delegate) {
        this(factory, host, port, null, reconnectPolicy, delegate, false);
    }

    public ApnsConnectionImpl(SocketFactory factory, String host,
            int port, Proxy proxy,
            ReconnectPolicy reconnectPolicy, ApnsDelegate delegate, boolean errorDetection) {
        this.factory = factory;
        this.host = host;
        this.port = port;
        this.reconnectPolicy = reconnectPolicy;
        this.delegate = delegate == null ? ApnsDelegate.EMPTY : delegate;
        this.proxy = proxy;
        this.errorDetection = errorDetection;

        this.proxySocket = null;
    }


    public synchronized void close() {
        Utilities.close(socket);
        Utilities.close(proxySocket);
    }

    private void monitorSocket(final Socket socket) {
	
//	if (socket==null) {
//	    System.out.println("ApnsConnectionImpl MonitoringThread socket==null");
//            
//	} else {
//
//	    System.out.println("ApnsConnectionImpl MonitoringThread socket is not null");
//            
//	}
	
        class MonitoringThread extends Thread {
            @Override 
            public void run() {
                try {
                    InputStream in = socket.getInputStream();

//                    if (in==null) {
//                	System.out.println("ApnsConnectionImpl MonitoringThread in==null");
//		    } else {
//                	System.out.println("ApnsConnectionImpl MonitoringThread in is not null");
//    		    }
                    
                    final int expectedSize = 6;
                    byte[] bytes = new byte[expectedSize];
                    
//                    logger.debug("ApnsConnectionImpl MonitoringThread before DeliveryError ");
                    
                    while (in.read(bytes) == expectedSize) {
                        int command = bytes[0] & 0xFF;
                        assert command == 8;
                        int statusCode = bytes[1] & 0xFF;
//                        System.out.println("ApnsConnectionImpl MonitoringThread before DeliveryError =");
//                        logger.debug("ApnsConnectionImpl MonitoringThread before DeliveryError ");
                        
                        DeliveryError e = DeliveryError.ofCode(statusCode);
                        
//                        System.out.println("ApnsConnectionImpl MonitoringThread before DeliveryError ="+e);
//                        logger.debug("ApnsConnectionImpl MonitoringThread before DeliveryError =",e);
                        int id = Utilities.parseBytes(bytes[2], bytes[3], bytes[4], bytes[5]);
                        delegate.connectionClosed(e, id);
                    }
                } catch (Exception e) {
//                    logger.warn("Exception while waiting for error code", e);
                }
            };
        }
        Thread t = new MonitoringThread();
        t.setDaemon(true);
        t.start();
    }

    // This method is only called from sendMessage.  sendMessage
    // has the required logic for retrying
    private Socket socket;
    private synchronized Socket socket() throws NetworkIOException {
        if (reconnectPolicy.shouldReconnect()) {
            Utilities.close(socket);
            Utilities.close(proxySocket);
            socket = null;
        }

        if (socket == null || socket.isClosed()) {
            try {
                if (proxy == null) {
                    socket = factory.createSocket(host, port);
                } else {
                    // always start a new proxy connection
                    Utilities.close(proxySocket);

                    proxySocket = new Socket(proxy);
                    proxySocket.connect(new InetSocketAddress(host, port));
                    socket = ((SSLSocketFactory)factory).createSocket(proxySocket, host, port, false);
                }

                if (errorDetection) {
                    monitorSocket(socket);
                }
                reconnectPolicy.reconnected();
//                System.out.println("Made a new connection to APNS");
                logger.debug("Made a new connection to APNS");
            } catch (IOException e) {
//        	System.out.println("Couldn't connect to APNS server");
                logger.error("Couldn't connect to APNS server", e);
                throw new NetworkIOException(e);
            }
        }
        return socket;
    }

    int DELAY_IN_MS = 1000;

    private static final int RETRIES = 1;
    public synchronized void sendMessage(ApnsNotification m) throws NetworkIOException {
        int attempts = 0;
        while (true) {
            try {
                attempts++;
                Socket socket = socket();
//                if (m==null) {
//                    System.out.println("ApnsConnectionImpl sendMessage m==null");                
//		} else {
//		    System.out.println("ApnsConnectionImpl sendMessage m is not null socket isConnected()= "+socket.isConnected());
//		}
                

                socket.getOutputStream().write(m.marshall());
                socket.getOutputStream().flush();
                delegate.messageSent(m);

                System.out.println("ApnsConnectionImpl sendMessage Message \"{}\" sent "+ m);
//                logger.debug("Message \"{}\" sent", m);

                attempts = 0;
                break;
            } catch (Exception e) {
                if (attempts >= RETRIES) {
                    System.out.println("Couldn't send message "+e);
                    logger.error("Couldn't send message " + m, e);
                    delegate.messageSendFailed(m, e);
                    Utilities.wrapAndThrowAsRuntimeException(e);
                }
                System.out.println("Failed to send message "+e);
                logger.warn("Failed to send message " + m + "... trying again", e);
                // The first failure might be due to closed connection
                // don't delay quite yet
                if (attempts != 1)
                    Utilities.sleep(DELAY_IN_MS);
                Utilities.close(socket);
                socket = null;
            }
        }
    }

    public ApnsConnectionImpl copy() {
        return new ApnsConnectionImpl(factory, host, port, proxy, reconnectPolicy.copy(), delegate, errorDetection);
    }

    public void testConnection() throws NetworkIOException {
        ApnsConnectionImpl testConnection = new ApnsConnectionImpl(factory, host, port, reconnectPolicy.copy(), ApnsDelegate.EMPTY);
        testConnection.sendMessage(new SimpleApnsNotification(new byte[] {0}, new byte[]{0}));
        testConnection.close();
    }
}
