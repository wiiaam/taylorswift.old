package bot;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Properties;

public class Message {
	
	// fields
	private String message;
	private String sender = "";
	private String senderAddress = "";
	private String senderWhole = "";
	private String command = "";
	private String param  = "";
	private String trailing = "";
	private String botCommand = "";
	private String[] botParamsArray;
	private String botParams = "";
	public Server server;
	private Properties config;
	private String commandChar;
	private boolean hasBotParams = false;
	public HashSet<String> admins;
	public HashSet<String> ignores;
	
	public Message(String message, Properties properties, Server server, HashSet<String> admins, HashSet<String> ignores){
		this.admins = admins;
		this.ignores = ignores;
		this.message = message;
		config = properties;
		this.server = server;
		commandChar = config.getProperty("commandchar");
		String[] messageSplit = message.split("\\s+");
		if(messageSplit.length == 2){
			command = messageSplit[0];
			param = "";
			if(messageSplit[1].startsWith(":"))trailing = messageSplit[1].substring(1);
			else trailing = messageSplit[1];
			for(int i = 2; i < messageSplit.length; i++){
				trailing += " " + messageSplit[i];
			}
		}
		else if(messageSplit.length == 3){
			senderWhole = messageSplit[0].substring(1);
			command = messageSplit[1];
			param = "";
			if(messageSplit[2].startsWith(":"))trailing = messageSplit[2].substring(1);
			else trailing = messageSplit[2];
			for(int i = 3; i < messageSplit.length; i++){
				trailing += " " + messageSplit[i];
			}
		}
		else{
			senderWhole = messageSplit[0].substring(1);
			command = messageSplit[1];
			param = messageSplit[2];
			if(messageSplit[3].startsWith(":"))trailing = messageSplit[3].substring(1);
			else trailing = messageSplit[3];
			for(int i = 4; i < messageSplit.length; i++){
				trailing += " " + messageSplit[i];
			}
			boolean cmd = trailing.startsWith(commandChar);
			if(commandChar.equals(config.getProperty("nickname") + ": ")){
				if(trailing.startsWith(properties.getProperty("nickname") + ", ")) cmd = true;
				else cmd = false;
				
			}
			
			if(cmd && command.equals("PRIVMSG")){
				String[] trailingSplit = trailing.split("\\s+");
				int i;
				int diff;
				if(commandChar.equals(config.getProperty("nickname") + ": ")){
					botCommand = trailingSplit[1];
					i = 2;
					diff = 2;
					botParamsArray = new String[trailingSplit.length - 2];
				}
				else{
					botCommand = trailingSplit[0].substring(commandChar.length());
					i = 1;
					diff = 1;
					botParamsArray = new String[trailingSplit.length - 1];
				}
				
				hasBotParams = true;
				for(;  i < trailingSplit.length; i++){
					System.out.println(i + " " + diff);
					botParamsArray[i-diff] = trailingSplit[i];
					botParams += trailingSplit[i] + " ";
				}
				botParams = botParams.trim();
			}
		}
		String[] sendersplit = senderWhole.split("!");
		if(sendersplit.length > 1){
			sender = sendersplit[0];
			senderAddress = sendersplit[1].split("@")[0].substring(1);
			senderAddress = sendersplit[1].split("@")[1];
		}
		else{
			senderAddress = sender;
		}
		
	}
	public String message(){
		return message;
	}
	
	public String sender(){
		return sender;
	}
	
	public String senderAddress(){
		return senderAddress;
	}
	
	public String senderWhole(){
		return senderWhole;
	}
	
	public String command(){
		return command;
	}
	
	public String param(){
		return param;
	}
	
	public String trailing(){
		return trailing;
	}
	
	public String botCommand(){
		return botCommand;
	}
	
	public String[] botParamsArray(){
		return botParamsArray;
	}
	
	public String botParams(){
		return botParams;
	}
	
	public void send(String message){
		server.send(message);
	}
	
	public void notice(String target, String message){
		server.notice(target, message);
	}
	
	public void pm(String target, String message){
		server.pm(target, message);
	}
	
	public String commandChar(){
		return commandChar;
	}
	
	public boolean hasBotParams(){
		return hasBotParams;
	}
	
	public Properties getConfig(){
		return config;
	}
	
	/**
	 * PRIVMSG for room
	 * NOTICE for user
	 */
	public void say(String target, String message){
		if(target.startsWith("#")) server.pm(target, message);
		else server.notice(target, message);
	}
	
	public void configure(String key, String value){
		try{
			config.setProperty(key, value);
			config.store(new FileOutputStream(new File(this.getClass().getResource("config.properties").toURI())), null);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
