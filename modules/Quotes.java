package modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import bot.Message;

public class Quotes implements Module{
	private File jsonfile;
	private JsonObject quotes;
	private JsonObject json;
	private Gson gson;
	public Quotes() {
		try{
			jsonfile = new File(this.getClass().getResource("json/quotes.json").toURI());
			Scanner scan;
			scan = new Scanner(new FileInputStream(jsonfile));
			String jsonstring = "";
			while(scan.hasNext()){
				jsonstring += scan.next() + " ";
			}
			scan.close();
			gson = new GsonBuilder().create();
			json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
			quotes = json.get("quotes").getAsJsonObject();
		}
		catch(IOException e){
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.command().equals("PRIVMSG") && m.botCommand().equals("")){
			if(m.sender().equals("py-ctcp"))return;
			if(quotes.has(m.sender())){
				String quote = makeJsonString(m.trailing());
				JsonArray quotearray = quotes.get(m.sender()).getAsJsonArray();
				quotearray.add(gson.fromJson("\"" + quote + "\"", JsonElement.class));;
				save();
			}
			else{
				String quote = makeJsonString(m.trailing());
				JsonArray quotearray = new JsonArray();
				quotearray.add(gson.fromJson("\"" + quote + "\"", JsonElement.class));
				quotes.add(m.sender(), quotearray);
				save();
			}
		}
		if(m.botCommand().equals("quote")){
			if(m.hasBotParams()){
				if(quotes.has(m.botParamsArray()[0])){
					String user = m.botParamsArray()[0];
					int random = (int)Math.floor(Math.random()*quotes.get(m.botParamsArray()[0]).getAsJsonArray().size());
					m.say(target,"<" + user + "> " + quotes.get(user).getAsJsonArray().get(random).getAsString());
				}
				else{
					m.say(target, "I don't know who that person is");
				}
			}
		}
	}

	private void save(){
		Gson gson = new GsonBuilder().create();
		try {
			PrintWriter writer = new PrintWriter(jsonfile);
			writer.println(gson.toJson(json));
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private String makeJsonString(String s){
		s = s.replace("\"", "\\\"");
		
		return s;
	}
}
