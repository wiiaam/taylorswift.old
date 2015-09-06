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

import bot.Config;
import bot.Message;
import bot.Module;
import bot.Server;
import bot.info.Info;

public class Intros implements Module {

	private Gson gson;
	private JsonObject json;
	private File jsonfile;
	private JsonObject intros;
	
	public Intros() {
		try{
			jsonfile = new File(this.getClass().getResource("json/intros.json").toURI());
			Scanner scan;
			scan = new Scanner(new FileInputStream(jsonfile));
			String jsonstring = "";
			while(scan.hasNext()){
				jsonstring += scan.next() + " ";
			}
			scan.close();
			gson = new GsonBuilder().setPrettyPrinting().create();
			json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
			intros = json.get("intros").getAsJsonObject();
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
		if(m.param().startsWith("#") && m.command().equals("PRIVMSG")){
			if(m.botCommand().equals("intros") || m.botCommand().equals("intro")){
				if(Info.isRegistered(m.sender())){
					if(m.hasBotParams()){
						if(m.botParamsArray()[0].equals("list")){
							if(intros.has(m.param())){
								if(intros.get(m.param()).getAsJsonObject().has(m.sender())){
									JsonArray introarray = intros.get(m.param()).getAsJsonObject().get(m.sender()).getAsJsonArray();
									Server.notice(m.sender(), "You have " + introarray.size() + " intros set for " + m.param());
									for(int i = 0; i < introarray.size(); i++){
										Server.notice(m.sender(),"Intro " + (i+1) + ": " + introarray.get(i).getAsString());
									}
								}
								else{
									Server.notice(m.sender(), "You do not have any intros set for " + m.param());
								}
							}
							else{
								Server.notice(m.sender(), "You do not have any intros set for " + m.param());
							}
						}
						else if(m.botParamsArray()[0].equals("add")){
							if(m.botParamsArray().length > 1){
								JsonObject channelintros;
								if(!intros.has(m.param())){
									channelintros = new JsonObject();
									intros.add(m.param(), channelintros);
								}
								else{
									channelintros = intros.get(m.param()).getAsJsonObject();
								}
								JsonArray userintros;
								if(!channelintros.has(m.sender())){
									userintros = new JsonArray();
									channelintros.add(m.sender(), userintros);
								}
								else{
									userintros = channelintros.get(m.sender()).getAsJsonArray();
								}
								String intro = "";
								for(int i = 1; i < m.botParamsArray().length; i++){
									intro += m.botParamsArray()[i] + " ";
								}
								intro = intro.trim()
										.replaceAll("\n", "")
										.replaceAll("\r", "")
										.replace("{", "\\{")
										.replace("}", "\\}")
										.replace("[", "\\[")
										.replace("]", "\\]")
										.replace(";", "\\;")
										.replace(":", "\\:");
								if(userintros.size() == 10){
									Server.say(target, m.sender() + ": Sorry, you have already set the max number of intros. Use " + 
											Config.getChar() + "intros del <intro> to remove some.");
								}
								else{
									userintros.add( gson.fromJson("\"" + intro + "\"", JsonElement.class));
									Server.say(target, m.sender() + ": Intro added. You now have " + userintros.size() + " intros set." );
									save();
								}
							}
							else{
								Server.say(target, m.sender() + ": Usage: " + Config.getChar() + "intros add <intro>");
							}
						}
						else if(m.botParamsArray()[0].equals("del")){
							if(m.botParamsArray().length > 1){
								int num;
								try{
									num = Integer.parseInt(m.botParamsArray()[1]);
								}
								catch(NumberFormatException e){
									Server.say(target, m.sender() + ": Please provide a valid intro number.");
									return;
								}
								if(intros.has(m.param())){
									if(intros.get(m.param()).getAsJsonObject().has(m.sender())){
										JsonArray userintros = intros.get(m.param()).getAsJsonObject().get(m.sender()).getAsJsonArray();
										if(userintros.size() < num){
											Server.say(target, m.sender() + ": Intro " + num + " does not exist");
										}
										else{
											userintros.remove(num-1);
											if(userintros.size() == 0){
												intros.get(m.param()).getAsJsonObject().remove(m.sender());
											}
											save();
											Server.say(target, m.sender() + ": Intro removed. You now have " + userintros.size() + " intros set");
										}
									}
									else{
										Server.say(target, m.sender() + ": Intro " + num + " does not exist");
									}
								}
								else{
									Server.say(target, m.sender() + ": Intro " + num + " does not exist");
								}
							}
							else{
								Server.say(target, m.sender() + ": Usage: " + Config.getChar() + "intros del <num>");
							}
						}
					}
					else{
						Server.say(target, "Usage : " + Config.getChar() + "intros <add | list | del>");
					}
				}
				else{
					Server.say(target, m.sender() + ": You must be a registered user to use this command");
				}
			}
		}
		if(m.command().equals("JOIN")){
			System.out.println(m.trailing() + " " + intros.has(m.param()));
			if(intros.has(m.trailing())){
				
				if(intros.get(m.trailing()).getAsJsonObject().has(m.sender())){
					JsonArray userintros = intros.get(m.trailing()).getAsJsonObject().get(m.sender()).getAsJsonArray();
					int rand = (int)Math.floor(Math.random() * userintros.size());
					Server.say(m.trailing(), "​" + userintros.get(rand).getAsString());
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

}
