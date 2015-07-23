package modules;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import bot.Config;
import bot.Message;

public class Steam implements bot.Module {

	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.botCommand().equals("steaminfo")){
			if(m.hasBotParams()){
				String id = m.botParams();
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
							m.say(target, "Steam vanity ID not found");
							return;
						}
					}
					catch(ArrayIndexOutOfBoundsException | IOException e){
						m.say(target, "Invalid steam vanity ID");
						return;
					}
					
				}
				if(id.toLowerCase().contains("steam_0")){
					m.say(target, "Only 64bit IDs are supported right now");
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
						m.say(target, "No steam info could be found");
						return;
					}
					JsonObject player = players.get(0).getAsJsonObject();
					
					if(player.get("profilestate").getAsInt() != 1){
						title.equals("This user has not set up their community profile yet");
					}
					else if(player.get("communityvisibilitystate").getAsInt() == 1){
						String personaname = player.get("personaname").getAsString();
						String profileurl = player.get("profileurl").getAsString();
						title = String.format("%s | %s", personaname, profileurl);
						onlinestatus = "This user has marked their profile as 4private";
					}
					else{
						String personaname = player.get("personaname").getAsString();
						String profileurl = player.get("profileurl").getAsString();
						String info = "";
						if(player.has("realname")) info += player.get("realname").getAsString() + " ";
						if(player.has("loccountrycode")) info += "| Location: " + player.get("loccountrycode").getAsString() + " ";
						//if(player.has("realname")) info += player.get("realname").getAsString() + " ";
						//if(player.has("realname")) info += player.get("realname").getAsString();
						if(info.equals(""))title = String.format("%s | %s", personaname, profileurl);
						else title = String.format("%s | %s| %s", personaname, info, profileurl);
						
						int personastate = player.get("personastate").getAsInt();
						switch(personastate){
						case 0: onlinestatus = "This user is currently 14offline"; break;
						case 1: onlinestatus = "This user is currently 12Online"; break;
						case 2: onlinestatus = "This user is currently 12Busy"; break;
						case 3: onlinestatus = "This user is currently 12Away"; break;
						case 4: onlinestatus = "This user is currently 12Snooze"; break;
						case 5: onlinestatus = "This user is currently 12Looking to Trade"; break;
						case 6: onlinestatus = "This user is currently 12Looking to Play"; break;
						}
						if(player.has("gameextrainfo")){
							onlinestatus = "This user is currently 3In game: " + player.get("gameextrainfo").getAsString() + "";
						}
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
					friends = String.format("This user has %d friends", friendslist.size());
					
				} catch (IOException e) {
					m.say(target, "No steam info could be found");
					return;
				}
				m.say(target, title);
				if(!onlinestatus.equals(""))m.say(target, onlinestatus);
				if(!friends.equals(""))m.say(target, friends);
			}
		}
	}
	

}
