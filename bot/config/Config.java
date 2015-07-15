package bot.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
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
	
	public static void addRoom(String s){
		s = s.replace("#", "");
		String toadd = "\"" + s + "\"";
		Gson gson = new GsonBuilder().create();
		JsonArray jsonarray = json.get("rooms").getAsJsonArray();
		for(JsonElement je : jsonarray){
			if(je.getAsString().equals(s)) return;
		}
		jsonarray.add(gson.fromJson(toadd, JsonElement.class));
		save();
	}
	
	public static boolean removeRoom(String s){
		JsonArray jsonarray = json.get("rooms").getAsJsonArray();
		if(s.startsWith("#")){
			s = s.substring(1);
		}
		boolean found = true;
		for(int i = 0; i < jsonarray.size(); i++){
			if(jsonarray.get(i).getAsString().equals(s)){
				jsonarray.remove(i);
				found = true;
			}
		}
		save();
		return found;
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
		String toadd = "\"" + s + "\"";
		Gson gson = new GsonBuilder().create();
		JsonArray jsonarray = json.get("admins").getAsJsonArray();
		for(JsonElement je : jsonarray){
			if(je.getAsString().equals(s)) return;
		}
		jsonarray.add(gson.fromJson(toadd, JsonElement.class));
		save();
	}
	
	public static boolean removeAdmin(String s){
		JsonArray jsonarray = json.get("admins").getAsJsonArray();
		boolean found = true;
		for(int i = 0; i < jsonarray.size(); i++){
			if(jsonarray.get(i).getAsString().equals(s)){
				jsonarray.remove(i);
				found = true;
			}
		}
		save();
		return found;
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
		String toadd = "\"" + s + "\"";
		Gson gson = new GsonBuilder().create();
		JsonArray jsonarray = json.get("ignores").getAsJsonArray();
		for(JsonElement je : jsonarray){
			if(je.getAsString().equals(s)) return;
		}
		jsonarray.add(gson.fromJson(toadd, JsonElement.class));
		save();
	}
	
	public static boolean removeIgnore(String s){
		JsonArray jsonarray = json.get("ignores").getAsJsonArray();
		boolean found = false;
		for(int i = 0; i < jsonarray.size(); i++){
			if(jsonarray.get(i).getAsString().equals(s)){
				jsonarray.remove(i);
				found = true;
			}
		}
		save();
		return found;
	}
	
	public static String getChar(){
		return json.get("commandchar").getAsString();
	}
	
	public static void setChar(String s){
		json.addProperty("commandchar", s);
		save();
	}
	
	public static String getGoogleApiKey(){
		return json.get("googleapikey").getAsString();
	}
	
	public static String getSteamApiKey(){
		return json.get("steamapikey").getAsString();
	}

	public static String getUploadApiKey(){
		return json.get("uploadapikey").getAsString();
	}
	
	private static void save(){
		Gson gson = new GsonBuilder().create();
		try {
			Writer writer = new FileWriter(jsonfile);
			gson.toJson(json,writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	    
	public static String get(String key){
		if(!json.has(key)){
			return null;
		}
		return json.get(key).getAsString();
	}
	
	public static void set(String key, String value){
		String toadd = "\"" + value + "\"";
		Gson gson = new GsonBuilder().create();
		json.add(key, gson.fromJson(toadd, JsonElement.class));
	}
}
