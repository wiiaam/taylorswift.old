package modules;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import bot.Config;
import bot.Message;
import bot.Module;

public class Weather implements Module {
	@Override
	public void parse(Message m) {
		if(m.botCommand().equals("weather") || m.botCommand().equals("w")){
			String target = m.param();
			if(!m.param().startsWith("#")) target = m.sender();
			if(m.hasBotParams()){
				ArrayList<String> options = new ArrayList<String>();
				String[] botParams = m.botParamsArray();
				String query = "";
				boolean isquery = false;
				for(int i = 0; i < botParams.length; i++){
					if(botParams[i].startsWith("-") && !isquery){
						options.add(botParams[i].substring(1));
					}
					else{
						isquery = true;
						if(query.equals("")) query = botParams[i];
						else query += "_" + botParams[i];
					}
				}
				
				String apiurl = "http://api.wunderground.com/api/" + Config.getWeatherApiKey() + "/conditions/q/" + query + ".json";
				try{
					URL url = new URL(apiurl);
					URLConnection urlc = url.openConnection();
					urlc.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
					urlc.addRequestProperty("User-Agent", "Mozilla");
					urlc.connect();
					Scanner scan = new Scanner(urlc.getInputStream());
					String jsonstring = "";
					while(scan.hasNext()){
						jsonstring += scan.next() + " ";
					}
					scan.close();
					Gson gson = new GsonBuilder().create();
					JsonObject json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
					JsonObject response = json.get("response").getAsJsonObject();
					if(response.has("results")){
						JsonObject firstResult = response.get("results").getAsJsonArray().get(0).getAsJsonObject();
						if(!firstResult.get("state").getAsString().equals("")){
							query = firstResult.get("state").getAsString() + "/" + firstResult.get("name").getAsString();
						}
						else{
							query = firstResult.get("country").getAsString() + "/" + firstResult.get("name").getAsString();
						}
						apiurl = "http://api.wunderground.com/api/" + Config.getWeatherApiKey() + "/conditions/q/" + query + ".json";
						url = new URL(apiurl);
						urlc = url.openConnection();
						urlc.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
						urlc.addRequestProperty("User-Agent", "Mozilla");
						urlc.connect();
						scan = new Scanner(urlc.getInputStream());
						jsonstring = "";
						while(scan.hasNext()){
							jsonstring += scan.next() + " ";
						}
						scan.close();
						gson = new GsonBuilder().create();
						json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
					}
					if(!json.has("current_observation")){
						m.say(target, "No results for " + query.replace("_", " ") + " could be found");
					}
					else{
						JsonObject currentObservation = json.get("current_observation").getAsJsonObject();
						JsonObject displayLocation = currentObservation.get("display_location").getAsJsonObject();
						String location = displayLocation.get("full").getAsString();
						String temp = currentObservation.get("temperature_string").getAsString();
						String weather = currentObservation.get("weather").getAsString();
						String precip;
						if(currentObservation.get("precip_today_in").getAsString().equals("")) precip = "";
						else precip = String.format(", Precipitation: %.0f%% ", (currentObservation.get("precip_today_in").getAsDouble()*100));
						
						String result = String.format("Weather for %s: Currently %s, %s%s", location, weather, temp, precip);
						m.say(target, result);
					}
				}
				catch(IOException e){
					
				}
			}
		}
	}

}
