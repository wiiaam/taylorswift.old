package bot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import bot.config.Config;
import bot.Modules;

public class IrcBot {

	// Constants
	
	// Fields
	public static HashSet<Module> modules;
	public static PrintStream out = System.out;
	private static Thread serverListener;
	private static boolean listening = false;
	
	public static void start(){
		out.println("Reading config");
			try {
				readConfig();
			} catch (FileNotFoundException | URISyntaxException e) {
				e.printStackTrace();
				return;
			}
			
		out.println("Loading modules");
		new Thread(new Runnable(){
			public void run(){
				Modules.loadAll();
				out.println("All modules loaded");
			}
		}).start();
		
		out.println("Connecting to Server");
		Server.connectTo(Config.getServer(), Config.getPort());
		
		out.println("Logging in");
		if(attemptLogin()){
			out.println("Sending onlogin commands");
			sendOnLogin();
			listenToServer();
		}
	}
	
	
	protected static void sendOnLogin(){
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
	protected static boolean attemptLogin(){
		Server.send("NICK " + Config.getNick());
		Server.send(String.format("USER %s %s %s :%s", Config.getUsername(), Config.getUsername(), Config.getServer(), Config.getRealname()));
		while(true){
			if(Server.in.hasNextLine()){
				String next = Server.in.nextLine();
				System.out.println(next);
				Message m = new Message(next);
				if(m.command().equals("001")) break;
				if(m.command().equals("433")){
					Server.send("NICK " + Config.getNick() + "_");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {		
					}
					Server.pm("NickServ", "GHOST " + Config.getNick() + " " + Config.getIdentification().split("\\s+")[Config.getIdentification().split("\\s+").length-1]);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {		
					}
					Server.send("NICK " + Config.getNick());
				}
			}
		}
		return true;
		
	}
	
	protected static void listenToServer() {
		listening = true;
		serverListener = new Thread(new Runnable(){
			public void run() {
				while(listening){
				if(Server.in.hasNextLine()){
					final Message m = new Message(Server.in.nextLine());
					if(!Config.getAdmins().contains(m.sender()) && Config.getIgnores().contains(m.sender())) continue;
					out.println(m.message());
					final ArrayList<Module> modules = Modules.getModules();
					for(int i = 0; i < modules.size(); i++){
						final int iter = i;
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								final Module mod = modules.get(iter);
								mod.parse(m);
							}
						}).start();;
					}
				}
			}
			}
		});
		serverListener.start();
	}
	
	public static void stop(){
		listening = false;
		serverListener.interrupt();
	}
}
