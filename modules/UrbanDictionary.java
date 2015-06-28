package modules;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import bot.Message;

public class UrbanDictionary implements Module {

	@Override
	public void parse(Message m) {
		if(m.botCommand().equals("ud")){
			String target = m.param();
			if(!m.param().startsWith("#")) target = m.sender();
			
			if(m.botParamsArray().length == 0)return;
			String query = m.botParams().replace("\\s+", "%20");
			try {
				URL url = new URL("http://api.urbandictionary.com/v0/define?term=" + query);
				InputStream in = url.openStream();
				Scanner scan = new Scanner(in);
				String jsonstring = "";
				while(scan.hasNext()){
					jsonstring += scan.next() + " ";
				}
				scan.close();
				Gson gson = new GsonBuilder().create();
				JsonObject json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
				if(json.get("result_type").getAsString().equals("no_results")){
					String title = "No results found for " + m.botParams();
					m.say(target, title);
					return;
				}
				JsonObject result = json.get("list").getAsJsonArray().get(0).getAsJsonObject();
				String word = result.get("word").getAsString();
				String permalink = result.get("permalink").getAsString();
				String definition = result.get("definition").getAsString();
				String title = String.format("%s: %s (%s)",word, definition, permalink);
				m.say(target, title);
			} catch (IOException e) {
				e.printStackTrace();
				String title = "No results found for " + m.botParams();
				m.say(target, title);
			}
			catch (IndexOutOfBoundsException e){
				String title = "No results found for " + m.botParams();
				m.say(target, title);
			}
			
		}

	}

}
