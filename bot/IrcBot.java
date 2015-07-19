package bot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.HashSet;

import bot.config.Config;
import modules.*;

public class IrcBot {

	// Constants
	
	// Fields
	public static HashSet<Module> modules;
	private static PrintStream out = System.out;
	
	public static void start(){
		out.println("Reading config");
			try {
				readConfig();
			} catch (FileNotFoundException | URISyntaxException e) {
				e.printStackTrace();
				return;
			}
			
		out.println("Loading modules");
		modules = Modules.loadAll();
		out.println("Connecting to Server");
		Server.connectTo(Config.getServer(), Config.getPort());
		
		out.println("Logging in");
		if(attemptLogin()){
			out.println("Sending onlogin commands");
			sendOnLogin();
			listenToServer();
		}
	}
	
	
	public static void sendOnLogin(){
		Server.send(Config.getIdentification());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(String s : Config.getRooms()){
			Server.send("JOIN #" + s);
			Server.send("WHO #" + s);
		}
	}
	
	public static boolean readConfig() throws FileNotFoundException, URISyntaxException{
		Config.load(new File(new IrcBot().getClass().getResource("config/config.json").toURI()));
		return true;
	}
	
	/**
	 * Attempts to login to the Server
	 */
	public static boolean attemptLogin(){
		Server.send("NICK " + Config.getNick());
		Server.send(String.format("USER %s %s %s :%s", Config.getUsername(), Config.getUsername(), Config.getServer(), Config.getRealname()));
		while(true){
			if(Server.in.hasNextLine()){
				String next = Server.in.nextLine();
				System.out.println(next);
				Message m = new Message(next);
				if(m.command().equals("001")) break;
			}
		}
		return true;
		
	}
	
	public static void listenToServer() {
		new Thread(new Runnable(){
			public void run() {
				while(true){
				if(Server.in.hasNextLine()){
					final Message m = new Message(Server.in.nextLine());
					if(!Config.getAdmins().contains(m.sender()) && Config.getIgnores().contains(m.sender())) continue;
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
}
