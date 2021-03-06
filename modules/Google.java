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
import bot.Server;

public class Google implements bot.Module {

	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		
		if(m.botCommand().equals("g") || m.botCommand().equals("google")){
			if(m.botParamsArray().length == 0)return;
			String query = m.botParams().replaceAll("\\s+", "%20");
			try {
				URL url = new URL("http://ajax.googleapis.com/ajax/services/search/web?v=1.0&safe=off&q=" + query);
				InputStream in = url.openStream();
				Scanner scan = new Scanner(in);
				String jsonstring = "";
				while(scan.hasNext()){
					jsonstring += scan.next() + " ";
				}
				scan.close();
				Gson gson = new GsonBuilder().create();
				JsonObject json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
				JsonObject responseData = json.get("responseData").getAsJsonObject();
				JsonObject results = responseData.get("results").getAsJsonArray().get(0).getAsJsonObject();
				String urltitle = results.get("titleNoFormatting").getAsString();
				if(urltitle.length() > 100) urltitle = urltitle.substring(0, 99).trim() + "...";
				//String description = "";
				String title = String.format("Results for %s: (%s) %s", m.botParams(),  results.get("unescapedUrl").getAsString(), urltitle);
				Server.say(target, title);
			} catch (IOException e) {
				e.printStackTrace();
			}
			catch (IndexOutOfBoundsException e){
				String title = "No results found for " + m.botParams();
				Server.say(target, title);
			}
		}
	}

}
