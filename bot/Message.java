package bot;


import bot.config.Config;

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
	private String commandChar;
	private boolean hasBotParams = false;

	
	public Message(String message, Server server){
		this.message = message;
		this.server = server;
		commandChar = Config.getChar();
		String[] messageSplit = message.split("\\s+");
		if(messageSplit.length == 2){
			command = messageSplit[0];
			param = "";
			if(messageSplit[1].startsWith(":"))trailing = messageSplit[1].substring(1);
			else trailing = messageSplit[1];
			for(int i = 2; i < messageSplit.length; i++){
				trailing += " " + messageSplit[i];
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
		else if(messageSplit.length == 3){
			senderWhole = messageSplit[0].substring(1);
			command = messageSplit[1];
			param = "";
			if(messageSplit[2].startsWith(":"))trailing = messageSplit[2].substring(1);
			else trailing = messageSplit[2];
			for(int i = 3; i < messageSplit.length; i++){
				trailing += " " + messageSplit[i];
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
		else{
			senderWhole = messageSplit[0].substring(1);
			command = messageSplit[1];
			param = messageSplit[2];
			if(messageSplit[3].startsWith(":"))trailing = messageSplit[3].substring(1);
			else trailing = messageSplit[3];
			for(int i = 4; i < messageSplit.length; i++){
				trailing += " " + messageSplit[i];
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
			boolean cmd = trailing.startsWith(commandChar);
			if(commandChar.equals(Config.getNick() + ": ")){
				if(trailing.startsWith(Config.getNick() + ", ")) cmd = true;
				if(trailing.startsWith(Config.getNick() + ": ")) cmd = true;
				if(trailing.startsWith(Config.getNick())) cmd = true;
				else cmd = false;
				
			}
			
			if(cmd && command.equals("PRIVMSG")){
				String[] trailingSplit = trailing.split("\\s+");
				int i;
				int diff;
				if(commandChar.equals(Config.getNick() + ": ")){
					if(trailingSplit.length == 1){
						cmd = false;
						return;
					}
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
					botParamsArray[i-diff] = trailingSplit[i];
					botParams += trailingSplit[i] + " ";
				}
				botParams = botParams.trim();
			}
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
	
	/**
	 * PRIVMSG for room
	 * NOTICE for user
	 */
	public void say(String target, String message){
		if(target.startsWith("#")) server.pm(target, message);
		else server.notice(target, message);
	}
	
}
