package modules;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import extras.urlparsers.YoutubeParser;
import bot.Message;
import bot.config.Config;

public class Youtube implements Module {

	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		
		if(m.botCommand().equals("yt") || m.botCommand().equals("youtube")){
			if(m.botParamsArray().length == 0)return;
			String query = m.botParams().replace(" ", "_");
			try {
				URL url = new URL("https://www.googleapis.com/youtube/v3/search?part=snippet&key=" + Config.getGoogleApiKey() + "&q=" + query);
				InputStream in = url.openStream();
				Scanner scan = new Scanner(in);
				String jsonstring = "";
				while(scan.hasNext()){
					jsonstring += scan.next() + " ";
				}
				scan.close();
				Gson gson = new GsonBuilder().create();
				JsonObject json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
				JsonObject items = json.get("items").getAsJsonArray().get(0).getAsJsonObject();
				JsonObject id = items.get("id").getAsJsonObject();
				String videoId = id.get("videoId").getAsString();
				String title = "https://youtu.be/" + videoId + " | " + YoutubeParser.findById(videoId);
				m.say(target, title);
			} catch (IOException e) {
				e.printStackTrace();
			}
			catch(IndexOutOfBoundsException e){
				m.say(target,"No results found for " + m.botParams());
			}
		}
	}

}
