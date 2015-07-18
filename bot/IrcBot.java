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
	private HashSet<Module> modules;
	private Server server;
	private PrintStream out = System.out;
	
	
	public IrcBot(){
		out.println("Reading config");
			try {
				readConfig();
			} catch (FileNotFoundException | URISyntaxException e) {
				e.printStackTrace();
				return;
			}
			
		out.println("Loading modules");
		loadModules();
		out.println("Connecting to server");
		server = new Server();
		server.connectTo(Config.getServer(), Config.getPort());
		
		out.println("Logging in");
		if(attemptLogin()){
			out.println("Sending onlogin commands");
			sendOnLogin();
			listenToServer();
		}
	}
	
	public void loadModules(){
		modules = new HashSet<Module>();
		
		modules.add(new IBIP());
		
		modules.add(new Admin());
		//modules.add(new Autojoin());
		modules.add(new Cowsay());
		modules.add(new Fortune());
		modules.add(new Google());
		modules.add(new Help());
		modules.add(new Ignores());
		modules.add(new KiwiIRC());
		modules.add(new London());
		modules.add(new Money());
		modules.add(new NoBro());
		modules.add(new Ping());
		modules.add(new Quotes());
		modules.add(new Rainbow());
		modules.add(new Random());
		modules.add(new SongLyrics());
		modules.add(new Spam());
		modules.add(new Steam());
		modules.add(new Time());
		modules.add(new TopKek());
		modules.add(new Triggers());
		modules.add(new TitleReporting());
		modules.add(new UrbanDictionary());
		modules.add(new modules.UserInfo());
		modules.add(new Version());
		modules.add(new Voting());
		modules.add(new Youtube());
		modules.add(new Weather());
		
	}
	
	public void sendOnLogin(){
		server.send(Config.getIdentification());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(String s : Config.getRooms()){
			server.send("JOIN #" + s);
			server.send("WHO #" + s);
		}
	}
	
	public boolean readConfig() throws FileNotFoundException, URISyntaxException{
		Config.load(new File(this.getClass().getResource("config/config.json").toURI()));
		return true;
	}
	
	/**
	 * Attempts to login to the server
	 */
	public boolean attemptLogin(){
		server.send("NICK " + Config.getNick());
		server.send(String.format("USER %s %s %s :%s", Config.getUsername(), Config.getUsername(), Config.getServer(), Config.getRealname()));
		while(true){
			if(server.in.hasNextLine()){
				Message m = new Message(server.in.nextLine(), server);
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
					final Message m = new Message(server.in.nextLine(), server);
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
