package modules;

import bot.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.*;
import bot.UserInfo;
import bot.config.Config;

public class NoBro implements Module {
	private String[] triggers = {"kittykatt","man","shut up","python"};
	private JsonObject json;
	private File jsonfile;
	private HashMap<String, Integer> offences = new HashMap<String,Integer>();
	
	public NoBro() {
		try {
			jsonfile = new File(this.getClass().getResource("json/bros.json").toURI());
			Scanner scan;
			scan = new Scanner(new FileInputStream(jsonfile));
			String jsonstring = "";
			while(scan.hasNext()){
				jsonstring += scan.next() + " ";
			}
			scan.close();
			Gson gson = new GsonBuilder().create();
			json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void parse(Message m) {
		if(m.sender().equals(Config.getNick()))return;
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.botCommand().equals("addbro") && m.senderIsAdmin()){
			if(m.hasBotParams())addBro(m.botParams());
		}
		if(m.botCommand().equals("delbro") && m.senderIsAdmin()){
			if(m.hasBotParams())delBro(m.botParams());
		}
		if(m.command().equals("PRIVMSG")){
			String bro = checkBro(m);
			if(bro.equals("possible")){
				for(String trigger : triggers){
					if(m.trailing().toLowerCase().contains(trigger)){
						if(!offences.containsKey(m.sender())){
							offences.put(m.sender(), 1);
						}
						else{
							offences.put(m.sender(),offences.get(m.sender())+1);
						}
						if(offences.get(m.sender()) == 3){
							addBro(m.senderWhole());
							m.say(target, "shut up bro");
						}
					}
				}
			}
			else if(bro.equals("yes")){
				m.say(target, "shut up bro");
			}
		}
		if(m.command().equals("JOIN")){
			String checkbro = checkBro(m);
			if(checkbro.equals("yes")){
				m.say(m.trailing(), "4ALERT ALERT BRO DETECTED");
				offences.put(m.sender(), 4);
			}
			else if(checkbro.equals("possible")){
				m.say(m.trailing(), "4ALERT ALERT POSSIBLE BRO DETECTED");
			}
		}
	}
	
	private void addBro(String sender){
		if(!sender.contains("!")){
			HashMap<String, String> info = UserInfo.getInfo(sender);
			sender = sender + "!" + info.get("user") + "@" + info.get("host");
		}
		String nickname = sender.split("!")[0];
		String host = "";
		String user = "";
		try{
			host = sender.split("@")[1];
			user = sender.split("@")[0].split("!")[1];
			if(user.startsWith("~"))user = user.substring(1);
		}
		catch(ArrayIndexOutOfBoundsException e){
			return;
		}
		JsonArray array = json.get("bros").getAsJsonArray();
		/*
		for(JsonElement je : array){
			if(je.getAsJsonObject().get("nickname").getAsString().equals(nickname))return;
			if(je.getAsJsonObject().get("user").getAsString().equals(user))return;
			if(je.getAsJsonObject().get("host").getAsString().equals(host))return;
		}
		*/
		JsonObject newBro = new JsonObject();
		newBro.addProperty("nickname", nickname);
		newBro.addProperty("user", user);
		newBro.addProperty("host", host);
		array.add(newBro);
		json.add("bros", array);
		save();
		
	}
	
	private void delBro(String nick){
		JsonArray array = json.get("bros").getAsJsonArray();
		for(int i = 0; i < array.size(); i++){
			if(array.get(i).getAsJsonObject().get("nickname").getAsString().equals(nick)){
				array.remove(i);
				 break;
			}
		}
		json.add("bros", array);
		save();
		
		if(offences.containsKey(nick)){
			offences.remove(nick);
		}
		
	}
	private void save(){
		Gson gson = new GsonBuilder().create();
		try {
			Writer writer = new FileWriter(jsonfile);
			gson.toJson(json,writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private String checkBro(Message m){
		if(m.sender().equals(Config.getNick()))return "no";
		boolean isbro = false;
		for(JsonElement je : json.get("bros").getAsJsonArray()){
			
			JsonObject tocheck = je.getAsJsonObject();
			
			if(tocheck.get("nickname").getAsString().equals(m.sender())){
				isbro = true;
			}
			if(tocheck.get("host").getAsString().equals(m.senderHost())) {
				isbro = true;
			}
		}
		try{
			if(isbro)return "yes";
			if( m.sender().startsWith("poo") 
				|| m.username().contains("zero@") 
				|| m.senderHost().contains("bigpond")
			)return "possible";
		}
		catch(NullPointerException e){
			return "no";
		}
		return "no";
	}
}
