package modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import bot.Config;
import bot.Message;
import bot.Module;
import bot.Server;
import bot.info.Info;

public class Forwarding implements Module {
	
	public JsonObject json;
	public File jsonfile;
	
	public Forwarding() {
		try {
			jsonfile = new File(this.getClass().getResource("json/forwarding.json").toURI());
			Scanner scan;
			scan = new Scanner(jsonfile);
			String jsonstring = "";
			while(scan.hasNext()){
				jsonstring += scan.next() + " ";
			}
			scan.close();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void parse(Message m) {
		if(m.command().equals("PRIVMSG") || m.command().equals("NOTICE")){
			for(Entry<String, JsonElement> en : json.entrySet()){
				if(!Info.hasUserInfo(en.getKey())) continue;
				if(get(en.getKey()).contains(m.sender())){
					if(Info.isRegistered(en.getKey()) && Config.getAdmins().contains(en.getKey())){
						Server.pm(en.getKey(), String.format("4%s %s 2%s > %s", m.sender(), m.command(), m.param(), m.trailing()));
					}
				}
			}
		}
		if(m.botCommand().equals("forward")){
			if(m.senderIsAdmin()){
				for(String s : m.botParamsArray()){
					add(m.sender(), s);
				}
			}
		}
		if(m.botCommand().equals("unforward")){
			if(m.senderIsAdmin()){
				for(String s : m.botParamsArray()){
					remove(m.sender(), s);
				}
			}
		}
	}
	
	private void save(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			Writer writer = new FileWriter(jsonfile);
			gson.toJson(json,writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public HashSet<String> get(String nick){
		if(!json.has(nick))return new HashSet<String>();
		JsonArray jsonarray = json.get(nick).getAsJsonArray();
		HashSet<String> set = new HashSet<String>();
		for(int i = 0; i < jsonarray.size(); i++){
			set.add(jsonarray.get(i).getAsString());
		}
		return set;
	}
	
	public void add(String nick, String user){
		String toadd = "\"" + user + "\"";
		Gson gson = new GsonBuilder().create();
		JsonArray jsonarray = new JsonArray();
		if(json.has(nick)){
			jsonarray = json.get(nick).getAsJsonArray();
			for(JsonElement je : jsonarray){
				if(je.getAsString().equals(user)) return;
			}
		}
		jsonarray.add(gson.fromJson(toadd, JsonElement.class));
		json.add(nick, jsonarray);
		save();
	}
	
	public boolean remove(String nick, String user){
		if(!json.has(nick))return false;
		JsonArray jsonarray = json.get(nick).getAsJsonArray();
		boolean found = true;
		for(int i = 0; i < jsonarray.size(); i++){
			if(jsonarray.get(i).getAsString().equals(user)){
				jsonarray.remove(i);
				found = true;
			}
		}
		json.add(nick, jsonarray);
		save();
		return found;
	}

}
