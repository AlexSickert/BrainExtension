/*


To generate a keystore:

		$ keytool -genkey -alias alias -keypass simulator \
		-keystore lig.keystore -storepass simulator


		keytool -genkey -alias alias -keypass p0NvCgSuw1e9dgkbq9NRQALMP -keystore brainextension.keystore -storepass p0NvCgSuw1e9dgkbq9NRQALMP

*/

package server;

import Log.Log;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class SimpleHttpsServer {
	private int port;
	private HttpsServer server;
	private static String protocol = "TLS";

	public void Start(int port) {

		ExecutorService   executor;


		try {
			this.port = port;
			// load certificate
            // brainextension.keystore

			String keystoreFilename = getPath() + "mycert.keystore";
//            String keystoreFilename = getPath() + "brainextension3.keystore";

//			char[] storepass = "p0NvCgSuw1e9dgkbq9NRQALMP".toCharArray();
//			char[] keypass = "p0NvCgSuw1e9dgkbq9NRQALMP".toCharArray();

			char[] storepass = "mypassword".toCharArray();
			char[] keypass = "mypassword".toCharArray();


			String alias = "alias";
			FileInputStream fIn = new FileInputStream(keystoreFilename);
			KeyStore keystore = KeyStore.getInstance("JKS");
			keystore.load(fIn, storepass);
			// display certificate
			Certificate cert = keystore.getCertificate(alias);
			System.out.println(cert);

			// setup the key manager factory
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(keystore, keypass);

			// setup the trust manager factory
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(keystore);

			// create https server
			server = HttpsServer.create(new InetSocketAddress(port), 0);
			// create ssl context
			SSLContext sslContext = SSLContext.getInstance(protocol);
			// setup the HTTPS context and parameters
			sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			server.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
				public void configure(HttpsParameters params) {
					try {
						// initialise the SSL context
						SSLContext c = SSLContext.getDefault();
						SSLEngine engine = c.createSSLEngine();
						params.setNeedClientAuth(false);
						params.setCipherSuites(engine.getEnabledCipherSuites());
						params.setProtocols(engine.getEnabledProtocols());

						// get the default parameters
						SSLParameters defaultSSLParameters = c.getDefaultSSLParameters();
						params.setSSLParameters(defaultSSLParameters);
					} catch (Exception ex) {
						ex.printStackTrace();
						Log.log("info", "Failed to create HTTPS server");
					}
				}
			});

			Log.log("info", "Https server started at " + port);

			server.createContext("/", new Handlers.RootHandler());
			server.createContext("/brainextension", new Handlers.BrainExtensionHandler());
			server.createContext("/echoHeader", new Handlers.EchoHeaderHandler());
			server.createContext("/jsOrCssFile", new Handlers.EchoGetHandler());
			server.createContext("/echoGet", new Handlers.EchoGetHandler());
			server.createContext("/post", new Handlers.EchoPostHandler());
			server.createContext("/crm", new Handlers.EchoCrmHandler());
			// Handler for handling file uploads
			server.createContext("/file", new Handlers.FilePostHandler());
			server.createContext("/fileDownload", new Handlers.GetFileDownloadHandler());

			// use thread pool
			executor = Executors.newFixedThreadPool(20);
			server.setExecutor(executor);


			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getPath() {
//		return this.getClass().getClassLoader().getResource("").getPath() + "com/happylife/demo/";
                return "./";
	}

	public void Stop() {
		server.stop(0);
		Log.log("info", "server stopped");
	}
}
