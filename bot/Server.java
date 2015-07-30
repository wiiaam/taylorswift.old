package bot;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Server {
	
	// Fields
	private static SSLSocketFactory sslfact;
	public static Scanner in;
	public static PrintStream out;
	private static Socket socket;
	private static SSLSocket sslsocket;
	private static boolean isConnected;
	private static String address;
	private static int port;
	private static boolean useSSL;
	private static boolean reading = false;
	private static ArrayDeque<String> toserver;
	private static ArrayDeque<String> toserverlesspriority;
	static Thread thread;
	
	public static void connectTo(String address, int port, boolean useSSL){
		Server.address = address;
		Server.port = port;
		Server.useSSL = useSSL;
		try{
			
			toserver = new ArrayDeque<String>();
			toserverlesspriority = new ArrayDeque<String>();
			if(socket != null)socket.close();
			if(sslsocket != null)sslsocket.close();
			if(out != null) out.close();
			if(in != null) in.close();
			
			if(useSSL){
				System.setProperty("javax.net.ssl.trustStore", Config.getPathToKeystore());
				try {
					X509TrustManager[] tm = new X509TrustManager[] { new X509TrustManager(){
						public void checkClientTrusted ( X509Certificate[] cert, String authType ) throws CertificateException {
						}
						public void checkServerTrusted ( X509Certificate[] cert, String authType ) throws CertificateException {
						}
						public X509Certificate[] getAcceptedIssuers (){
							return null; 
						}
					}};
					SSLContext context = SSLContext.getInstance ("SSL");
					context.init( new KeyManager[0], tm, new SecureRandom( ) );

					sslfact = (SSLSocketFactory) context.getSocketFactory ();

				    } catch (KeyManagementException e) {
				    } catch (NoSuchAlgorithmException e) {
				    }
				sslsocket = (SSLSocket)sslfact.createSocket(address, port);
				sslsocket.startHandshake();
				in = new Scanner(sslsocket.getInputStream());
				out = new PrintStream(sslsocket.getOutputStream());
			}
			else{
				socket = new Socket(address, port);
				in = new Scanner(socket.getInputStream());
				out = new PrintStream(socket.getOutputStream());
			}
			readToServerStream();
			isConnected = true;
		}
		catch(Exception e){
			IrcBot.out.println("Could not connect: " + e.toString());
			IrcBot.out.println("Retrying in 10 seconds");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		if(!isConnected){
			connectTo(address,port,useSSL);
		}
	}
	
	public static void send(String message){
		System.out.println("sending " + message);
		toserver.add(message);
	}
	
	public static void pm(String target, String message){
		send(String.format("PRIVMSG %s :%s", target, message));
	}
	
	public static void notice(String target, String message){
		send(String.format("NOTICE %s :%s", target, message));
	}
	
	public static void lessPrioritySend(String message){
		//System.out.println("sending " + message);
		toserverlesspriority.add(message);
	}
	
	public static void lessPriorityPm(String target, String message){
		lessPrioritySend(String.format("PRIVMSG %s :%s", target, message));
	}
	
	public static void lessPriorityNotice(String target, String message){
		lessPrioritySend(String.format("NOTICE %s :%s", target, message));
	}
	
	public static void prioritySend(String message){
		//System.out.println("sending " + message);
		toserver.addFirst(message);
	}
	
	public static void priorityPm(String target, String message){
		prioritySend(String.format("PRIVMSG %s :%s", target, message));
	}
	
	public static void priorityNotice(String target, String message){
		prioritySend(String.format("NOTICE %s :%s", target, message));
	}
	
	/**
	 * PRIVMSG for room
	 * NOTICE for user
	 */
	public static void say(String target, String message){
		if(target.startsWith("#")) Server.pm(target, message);
		else Server.notice(target, message);
	}
	
	public static void say(String target, String[] messagearray){
		for(int i = 0; i < messagearray.length; i++){
			if(target.startsWith("#")) Server.pm(target, messagearray[i]);
			else Server.notice(target, messagearray[i]);
		}
	}
	
	/**
	 * Priority
	 */
	public static void prioritySay(String target, String message){
		if(target.startsWith("#")) Server.priorityPm(target, message);
		else Server.priorityNotice(target, message);
	}
	
	public static void prioritySay(String target, String[] messagearray){
		for(int i = 0; i < messagearray.length; i++){
			if(target.startsWith("#")) Server.priorityPm(target, messagearray[i]);
			else Server.priorityNotice(target, messagearray[i]);
		}
	}
	
	
	/**
	 * Less Priority say
	 */
	public static void lessPrioritySay(String target, String message){
		if(target.startsWith("#")) Server.lessPriorityPm(target, message);
		else Server.lessPriorityNotice(target, message);
	}
	
	public static void lessPrioritySay(String target, String[] messagearray){
		for(int i = 0; i < messagearray.length; i++){
			if(target.startsWith("#")) Server.lessPriorityPm(target, messagearray[i]);
			else Server.lessPriorityNotice(target, messagearray[i]);
		}
	}
	
	public static boolean isConnected(){
		return isConnected;
	}
	
	public static void resetConnection(String reason){
		IrcBot.out.println("Resetting connection: " + reason);
		isConnected = false;
		IrcBot.stop();
		try {
			connectTo(address,port,useSSL);
			Thread.sleep(2000);
			if(IrcBot.attemptLogin()){
				IrcBot.sendOnLogin();
				isConnected = true;
				IrcBot.listenToServer();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void disconnect(){
		try {
			socket.close();
		} catch (IOException e) {
		}
		System.exit(0);
	}
	
	private static void readToServerStream(){
		if(reading)return;
		reading = true;
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				while(true){
					try {
						Thread.sleep(1);
						
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try{
						if(toserver.size() != 0){
							String tosend = toserver.poll();
							System.out.println(tosend);
							out.println(tosend + "\r\n");
							out.flush();
							thread.sleep(500);
						}
						else if(toserverlesspriority.size() != 0){
							String tosend = toserverlesspriority.poll();
							out.println(tosend + "\r\n");
							out.flush();
							thread.sleep(500);
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}
	
}
