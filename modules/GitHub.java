package modules;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import bot.Message;
import bot.Module;
import bot.config.Config;

public class GitHub implements Module{
	
	
	public void parse(Message m){
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.botCommand().equals("gh") || m.botCommand().equals("github")){
			if(m.hasBotParams()){
				try {
					URL url = new URL("https://api.github.com/users/" + m.botParamsArray()[0]);
					InputStream in = url.openStream();
					Scanner scan = new Scanner(in);
					String jsonstring = "";
					while(scan.hasNext()){
						jsonstring += scan.next() + " ";
					}
					scan.close();
					Gson gson = new GsonBuilder().create();
					JsonObject json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
					if(json.has("message")){
						m.say(target, "Could not find user: " + m.botParamsArray()[0]);
						return;
					}
					String login = json.get("login").getAsString();
					String htmlUrl = json.get("html_url").getAsString();
					String name = json.get("name").getAsString();
					name = "Real Name : " + name + " | ";
					int followers = json.get("followers").getAsInt();
					int following = json.get("following").getAsInt();
					int repos = json.get("public_repos").getAsInt();
					String tosay = String.format("Github for %s: %s%d followers | Following %d users | %d repos (%s)", login, name, followers, following, repos, htmlUrl);
					m.say(target, tosay);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(m.botCommand().equals("repos")){
			if(!m.hasBotParams())return;
			try {
				URL url = new URL("https://api.github.com/users/" + m.botParamsArray()[0] + "/repos");
				InputStream in = url.openStream();
				Scanner scan = new Scanner(in);
				String jsonstring = "";
				while(scan.hasNext()){
					jsonstring += scan.next() + " ";
				}
				scan.close();
				Gson gson = new GsonBuilder().create();
				JsonArray repos;
				try{
					repos = gson.fromJson(jsonstring, JsonElement.class).getAsJsonArray();
				}
				catch(Exception e){
					m.say(target, "Could not find user: " + m.botParamsArray()[0]);
					return;
				}
				String tosay = "Repos for " + m.botParamsArray()[0] + ": ";
				for(int i = 0; i < repos.size(); i++){
					JsonObject repo = repos.get(i).getAsJsonObject();
					String name = repo.get("name").getAsString();
					JsonElement e = repo.get("language");
					String language;
					if(e.isJsonNull())language = "none";
					else language = repo.get("language").getAsString();
					String toappend = "2" + name + ", " + language + " | " ;
					tosay += toappend;
				}
				tosay = tosay.substring(0, tosay.length()-2);
				m.say(target, tosay);
			} catch (IOException e) {
				m.say(target, "Could not find user: " + m.botParamsArray()[0]);
				return;
			}
		}
		if(m.botCommand().equals("repo")){
			if(m.botParamsArray().length > 1 || m.botParamsArray()[0].contains("/")){
				try {
					String user;
					String repo;
					if(m.botParamsArray()[0].contains("/")){
						user = m.botParamsArray()[0].split("/")[0];
						repo = m.botParamsArray()[0].split("/")[1];
					}
					else{
						user = m.botParamsArray()[0];
						repo = m.botParamsArray()[1];
					}
					URL url = new URL("https://api.github.com/repos/" + user + "/" + repo);
					InputStream in = url.openStream();
					Scanner scan = new Scanner(in);
					String jsonstring = "";
					while(scan.hasNext()){
						jsonstring += scan.next() + " ";
					}
					scan.close();
					Gson gson = new GsonBuilder().create();
					JsonObject json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
					String name = json.get("name").getAsString();
					String fullName = json.get("full_name").getAsString();
					String htmlUrl = json.get("html_url").getAsString();
					String fork = "";
					if(json.get("fork").getAsBoolean()){
						JsonObject parent = json.get("parent").getAsJsonObject();
						fork = "Fork of " + parent.get("full_name").getAsString() + " | "; 
					}
					int watchers = json.get("subscribers_count").getAsInt();
					int stars = json.get("stargazers_count").getAsInt();
					int forks = json.get("forks_count").getAsInt();
					String language = json.get("language").getAsString();
					String tosay = String.format("%s: %sLanguage: %s | Watchers: %d Stars: %d Forks: %d (%s)", fullName, fork, language, watchers, stars, forks, htmlUrl );
					m.say(target, tosay);
				}catch (IOException e) {
					m.say(target, "Could not find repo");
					return;
				}
			}
		}
	}
}
