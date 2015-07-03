package extras.urlparsers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import bot.config.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SteamParser {
	public static String find(String id){
		
		if(id.contains("/profiles/")){
			id = id.split(".*/profiles/")[1].split("/")[0];
		}
		if(id.toLowerCase().contains("/id/")){
			String vanityurl;
			try{
				vanityurl = id.split(".*/id/")[1].split("/")[0];
				URL url = new URL("http://api.steampowered.com/ISteamUser/ResolveVanityURL/v0001/?key=" + Config.getSteamApiKey() + "&vanityurl=" + vanityurl);
				InputStream in = url.openStream();
				Scanner scan = new Scanner(in);
				String jsonstring = "";
				while(scan.hasNext()){
					jsonstring += scan.next() + " ";
				}
				scan.close();
				Gson gson = new GsonBuilder().create();
				JsonObject json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
				JsonObject response = json.get("response").getAsJsonObject();
				if(response.get("success").getAsInt() == 1){
					id = response.get("steamid").getAsString();
				}
				else{
					return "Steam vanity ID not found";
				}
			}
			catch(ArrayIndexOutOfBoundsException | IOException e){
				return "Invalid steam vanity ID";
			}
			
		}
		String title = "";
		String friends = "";
		//String games = "";
		String onlinestatus = "";
		try {
			
			// Find title
			URL url = new URL("http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=" + Config.getSteamApiKey() + "&steamids=" + id);
			InputStream in = url.openStream();
			Scanner scan = new Scanner(in);
			String jsonstring = "";
			while(scan.hasNext()){
				jsonstring += scan.next() + " ";
			}
			scan.close();
			Gson gson = new GsonBuilder().create();
			JsonObject json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
			JsonObject response = json.get("response").getAsJsonObject();
			JsonArray players = response.get("players").getAsJsonArray();
			if(players.size() < 1){
				return "No steam info could be found";
			}
			JsonObject player = players.get(0).getAsJsonObject();
			
			if(player.get("profilestate").getAsInt() != 1){
				title.equals("This user has not set up their community profile yet");
			}
			else if(player.get("communityvisibilitystate").getAsInt() == 1){
				String personaname = player.get("personaname").getAsString();
				String profileurl = player.get("profileurl").getAsString();
				title = String.format("%s | %s", personaname, profileurl);
				onlinestatus = "4Private";
			}
			else{
				String personaname = player.get("personaname").getAsString();
				//String profileurl = player.get("profileurl").getAsString();
				String info = "";
				if(player.has("realname")) info += player.get("realname").getAsString() + " ";
				if(player.has("loccountrycode")) info += "| Location: " + player.get("loccountrycode").getAsString() + " ";
				//if(player.has("realname")) info += player.get("realname").getAsString() + " ";
				//if(player.has("realname")) info += player.get("realname").getAsString();
				
				
				int personastate = player.get("personastate").getAsInt();
				switch(personastate){
				case 0: onlinestatus = "14offline"; break;
				case 1: onlinestatus = "12Online"; break;
				case 2: onlinestatus = "12Busy"; break;
				case 3: onlinestatus = "12Away"; break;
				case 4: onlinestatus = "12Snooze"; break;
				case 5: onlinestatus = "12Looking to Trade"; break;
				case 6: onlinestatus = "12Looking to Play"; break;
				}
				if(player.has("gameextrainfo")){
					onlinestatus = "3In game: " + player.get("gameextrainfo").getAsString() + "";
				}
				if(info.equals(""))title = String.format("Steam: %s | %s ", personaname, onlinestatus);
				else title = String.format("Steam: %s | %s| %s ", personaname, info, onlinestatus);
			}
			
			// Find friends
			
			url = new URL("http://api.steampowered.com/ISteamUser/GetFriendList/v0001/?key=" + Config.getSteamApiKey() + "&steamid=" + id + "&relationship=friend");
			in = url.openStream();
			scan = new Scanner(in);
			jsonstring = "";
			while(scan.hasNext()){
				jsonstring += scan.next() + " ";
			}
			scan.close();
			gson = new GsonBuilder().create();
			json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
			JsonArray friendslist = json.get("friendslist").getAsJsonObject().get("friends").getAsJsonArray();
			friends = String.format("%d friends", friendslist.size());
			
			title += "| " + friends;
			
		} catch (IOException e) {
			return "No steam info could be found";
		}
		return title;
	}
}
