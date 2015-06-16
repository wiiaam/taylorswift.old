package bot;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	
	// Fields
	public Scanner in;
	public PrintStream out;
	private Socket socket;
	
	public Server(){
		
	}
	
	public void connectTo(String address, int port){
		if(socket != null){
			try {
				socket.close();
				
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		try{
			socket = new Socket(address, port);
			in = new Scanner(socket.getInputStream());
			out = new PrintStream(socket.getOutputStream());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void send(String s){
		out.println(s + "\r\n");
	}
	
	public void pm(String target, String message){
		send(String.format("PRIVMSG %s :%s", target, message));
	}
	
	public void notice(String target, String message){
		send(String.format("NOTICE %s :%s", target, message));
	}
}
