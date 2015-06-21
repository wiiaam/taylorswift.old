package bot;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;

import modules.*;

public class IrcBot {

	// Constants
	
	// Fields
	private HashSet<Module> modules;
	private Server server;
	private Properties config;
	private HashSet<String> admins = new HashSet<String>();
	private PrintStream out = System.out;
	
	
	public IrcBot(){
		out.println("Reading config");
		readConfig();
		out.println("Loading modules");
		loadModules();
		out.println("Connecting to server");
		server = new Server();
		server.connectTo(config.getProperty("server"), Integer.parseInt(config.getProperty("port")));
		
		out.println("Logging in");
		if(attemptLogin()){
			out.println("Sending onlogin commands");
			sendOnLogin();
			listenToServer();
		}
	}
	
	public void loadModules(){
		modules = new HashSet<Module>();
		
		modules.add(new Admin());
		modules.add(new Autojoin());
		modules.add(new Fortune());
		modules.add(new Help());
		modules.add(new IBIP());
		modules.add(new Ping());
		modules.add(new KiwiIRC());
		modules.add(new Random());
		modules.add(new SongLyrics());
		modules.add(new Time());
		modules.add(new Version());
		modules.add(new Voting());
		
	}
	
	public void sendOnLogin(){
		try {
			Scanner scan = new Scanner(new File(this.getClass().getResource("onlogin.txt").toURI()));
			while(scan.hasNextLine()){
				String next = scan.nextLine();
				if(next.startsWith("#"))continue;
				else server.send(next);
			}
			scan.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean readConfig(){
		try{
			config = new Properties();
			config.load(new FileInputStream(new File(this.getClass().getResource("config.properties").toURI())));
			
			admins.add(config.getProperty("superadmin"));
			
			Scanner scan = new Scanner(new File(this.getClass().getResource("admins.txt").toURI()));
			while(scan.hasNextLine()){
				String next = scan.nextLine();
				if(next.startsWith("#")) continue;
				else admins.add(next);
			}
			scan.close();
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Attempts to login to the server
	 */
	public boolean attemptLogin(){
		server.send("NICK " + config.getProperty("nickname"));
		server.send(String.format("USER %s %s %s :%s", config.getProperty("username"), config.getProperty("username"), config.getProperty("server"), config.getProperty("realname")));
		while(true){
			if(server.in.hasNextLine()){
				Message m = new Message(server.in.nextLine(), config, server, admins);
				if(m.command().equals("002")) out.println("Connected");
				if(m.command().equals("433")) out.println("Nick in use");
				if(m.command().equals("451")) out.println("Register first");
				if(m.command().equals("376")) break;
			}
		}
		return true;
		
	}
	
	public void listenToServer() {
		new Thread(new Runnable(){
			public void run() {
				while(true){
				if(server.in.hasNextLine()){
					final Message m = new Message(server.in.nextLine(), config, server, admins);
					out.println(m.message());
					for(Module module : modules){
						new Thread(new Runnable(){
							public void run(){
								module.parse(m);
							}
						}).start();
					}
				}
			}
			}
		}).start();
	}
	
	public static void main(String[] args){
		new IrcBot();
	}
	
}
