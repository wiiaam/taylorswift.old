package bot.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Config {
	
	private static File jsonfile;
	private static JsonObject json;
	public static void load(File file) throws FileNotFoundException{
		jsonfile = file;
		Scanner scan;
		scan = new Scanner(new FileInputStream(file));
		String jsonstring = "";
		while(scan.hasNext()){
			jsonstring += scan.next() + " ";
		}
		scan.close();
		Gson gson = new GsonBuilder().create();
		json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
		
	}
	public static String getNick(){
		return json.get("nickname").getAsString();
	}
	
	public static void setNick(String s){
		json.addProperty("nickname", s);
		save();
	}
	
	public static String getUsername(){
		return json.get("username").getAsString();
	}
	
	public static String getRealname(){
		return json.get("realname").getAsString();
	}
	
	public static String getServer(){
		return json.get("server").getAsString();
	}
	
	public static int getPort(){
		return json.get("port").getAsInt();
	}
	
	public static String getIdentification(){
		return json.get("identification").getAsString();
	}
	
	public static HashSet<String> getRooms(){
		JsonArray jsonarray = json.get("rooms").getAsJsonArray();
		HashSet<String> set = new HashSet<String>();
		for(int i = 0; i < jsonarray.size(); i++){
			set.add(jsonarray.get(i).getAsString());
		}
		return set;
	}
	
	public static HashSet<String> getAdmins(){
		JsonArray jsonarray = json.get("admins").getAsJsonArray();
		HashSet<String> set = new HashSet<String>();
		for(int i = 0; i < jsonarray.size(); i++){
			set.add(jsonarray.get(i).getAsString());
		}
		return set;
	}
	
	public static void addAdmin(String s){
		Gson gson = new GsonBuilder().create();
		JsonArray admins = json.get("admins").getAsJsonArray();
		for(JsonElement je : admins){
			if(je.getAsString().equals(s)) return;
		}
		admins.add(gson.fromJson(s, JsonElement.class));
		save();
	}
	
	public static void removeAdmin(String s){
		JsonArray admins = json.get("admins").getAsJsonArray();
		for(int i = 0; i < admins.size(); i++){
			if(admins.get(i).getAsString().equals(s)) admins.remove(i);
		}
		save();
	}
	
	public static HashSet<String> getIgnores(){
		JsonArray jsonarray = json.get("ignores").getAsJsonArray();
		HashSet<String> set = new HashSet<String>();
		for(int i = 0; i < jsonarray.size(); i++){
			set.add(jsonarray.get(i).getAsString());
		}
		return set;
	}
	
	public static void addIgnore(String s){
		Gson gson = new GsonBuilder().create();
		JsonArray jsonarray = json.get("ignores").getAsJsonArray();
		for(JsonElement je : jsonarray){
			if(je.getAsString().equals(s)) return;
		}
		jsonarray.add(gson.fromJson(s, JsonElement.class));
		save();
	}
	
	public static void removeIgnore(String s){
		JsonArray jsonarray = json.get("ignores").getAsJsonArray();
		for(int i = 0; i < jsonarray.size(); i++){
			if(jsonarray.get(i).getAsString().equals(s)) jsonarray.remove(i);
		}
		save();
	}
	
	public static String getChar(){
		return json.get("commandchar").getAsString();
	}
	
	public static void setChar(String s){
		json.addProperty("commandchar", s);
		save();
	}
	
	private static void save(){
		Gson gson = new GsonBuilder().create();
		try {
			PrintWriter writer = new PrintWriter(jsonfile);
			writer.println(gson.toJson(json));
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
