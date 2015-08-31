package modules;

import bot.Config;
import bot.Message;
import bot.Module;
import bot.Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.*;

import bot.info.Info;
import bot.info.User;

public class NoBro implements Module {
	private String[] triggers = {"kittykatt","man","shut up","python","denko","muslims","liberals","reddit"};
	private final int MAX_OFFENCES = 5;
	private final int WARN_OFFENCES = 3;
	private HashMap<String, Integer> offences = new HashMap<String,Integer>();

	@Override
	public void parse(Message m) {
		if(m.sender().equals(Config.getNick()))return;
		if(m.command().equals("NICK")){
			if(offences.containsKey(m.sender())){
				offences.put(m.trailing(), offences.get(m.sender()));
				offences.remove(m.sender());
			}
		}
		if(m.command().equals("PRIVMSG") && m.param().equals("#pasta")){
			if(checkBro(m)){
				if(m.trailing().toUpperCase().equals(m.trailing())){
					offence(m);
				}
				else if(m.trailing().trim().endsWith(",")){
					offence(m);
				}
				else for(String trigger : triggers){
					if(m.trailing().toLowerCase().contains(trigger)){
						offence(m);
						break;
					}
				}
			}
		}
		if(m.command().equals("JOIN") && m.trailing().equals("#pasta")){
			if(checkBro(m)){
				Server.say(m.trailing(), "4ALERT ALERT POSSIBLE BRO DETECTED");
			}
		}
	}
	
	private void offence(Message m){
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(!offences.containsKey(m.sender())){
			offences.put(m.sender(), 1);
		}
		else{
			offences.put(m.sender(),offences.get(m.sender())+1);
		}
		if(offences.get(m.sender()) == WARN_OFFENCES){
			Server.say(target,"4" + m.sender() + ": Warning, you are being very liquid like.");
		}
		if(offences.get(m.sender()) >= MAX_OFFENCES){
			offences.put(m.sender(),offences.get(m.sender())+1);
			if(m.param().equals("#pasta")){
				Server.say(target, "shut up bro");
				Server.send("KICK " + m.param() + " " + m.sender() + " :shut up bro");
				offences.remove(m.sender());
			}
		}
	}
	
	/**
	 * 
	 * @param m
	 * @return true if a possible bro
	 */
	private boolean checkBro(Message m){
		if(m.sender().equals(Config.getNick()))return false;
		if( m.sender().startsWith("poo") 
			|| m.username().contains("zero@")
			|| m.senderAddress().endsWith("bigpond.net.au") 
			)return true;

		return false;
	}
}
