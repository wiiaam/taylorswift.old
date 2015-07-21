package bot;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	
	// Fields
	public static Scanner in;
	public static PrintStream out;
	private static Socket socket;
	private static boolean isConnected;
	private static String address;
	private static int port;
	
	public static void connectTo(String address, int port){
		Server.address = address;
		Server.port = port;
		if(socket != null){
			try {
				socket.close();
				
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		try{
			if(socket != null)socket.close();
			if(out != null) out.close();
			if(in != null) in.close();
			socket = new Socket(address, port);
			in = new Scanner(socket.getInputStream());
			out = new PrintStream(socket.getOutputStream());
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
			connectTo(address,port);
		}
	}
	
	public static void send(String message){
		System.out.println("sending " + message);
		out.println(message + "\r\n");
		out.flush();
	}
	
	public static void pm(String target, String message){
		send(String.format("PRIVMSG %s :%s", target, message));
	}
	
	public static void notice(String target, String message){
		send(String.format("NOTICE %s :%s", target, message));
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
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
			connectTo(address,port);
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
}
