package modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

import bot.Message;
import bot.config.Config;
import bot.info.Info;

public class Money implements bot.Module{
	private Properties bank = new Properties();
	private HashMap<String,Long> lastpaid = new HashMap<String,Long>();
	private HashMap<String, Long> jail = new HashMap<String,Long>();
	private HashSet<String> pros = new HashSet<String>();
	
	public Money(){
		pros.add("gen2");
		pros.add("wiiaamm");
		try {
			bank.load(new FileInputStream(new File(this.getClass().getResource("properties/money.properties").toURI())));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public void parse(Message m){
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.botCommand().equals("jailstatus")){
			if(!jail.containsKey(m.sender())){
				m.say(target, "ur not in jail u helmet");
				return;
			}
			else{
				int timeleft = (int)Math.floor((jail.get(m.sender()) - (System.currentTimeMillis() - (60*5*1000)))/1000);
				m.say(target, "ur in jail for another " + timeleft + " seconds. dont drop the soap!");
			}
		}
		checkJail();
		if(jail.containsKey(m.sender()))return;
		if(m.botCommand().equals("bene")){
			long lastPaid;
			if(!lastpaid.containsKey(m.sender())) lastPaid = 0; 
			else lastPaid = lastpaid.get(m.sender());
			if(lastPaid > System.currentTimeMillis() - (3600*1000)){
				int minutesleft = 0; 
				int secondsleft = (int)Math.floor((lastPaid - (System.currentTimeMillis() - (3600*1000)))/1000);
				if(secondsleft > 60){
					minutesleft = (int)Math.floor(secondsleft / 60);
					secondsleft = minutesleft % 60;
				}
				if(minutesleft == 0){
					m.say(target, "bro ur next payment is in " + secondsleft + " seconds");
				}
				else{
					m.say(target, "bro ur next payment is in " + minutesleft + " minutes");
				}
				return;
			}
			double userbalance;
			if(!bank.containsKey(m.sender())) userbalance = 0;
			else userbalance = get(m.sender());
			userbalance += 500;
			m.say(target, String.format("winz just gave u 3$500. u now have3 $%.0f", userbalance));
			lastpaid.put(m.sender(), System.currentTimeMillis());
			write(m.sender(), userbalance);
		}
		if(m.botCommand().equals("money") || m.botCommand().equals("wallet") || m.botCommand().equals("bank")){
			if(!Info.isRegistered(m.sender())){
				m.say(target, "pls login m9");
				return;
			}
			if(!bank.containsKey(m.sender())){
				m.say(target, "You don't have an account yet. Use " + Config.getChar() + "bene to get some cash");
			}
			else m.say(target, String.format("You currently have3 $%.0f in the bnz", Double.parseDouble(bank.getProperty(m.sender())) ));
		}
		if(m.botCommand().equals("pokies")){
			if(!Info.isRegistered(m.sender())){
				m.say(target, "pls login m9");
				return;
			}
			if(m.hasBotParams()){
				if(!bank.containsKey(m.sender())){
					m.say(target, "winz hasnt given u any money yet");
					return;
				}
				long bet;
				try{
					bet = Long.parseLong(m.botParamsArray()[0]);
				}
				catch (NumberFormatException e){
					m.say(target, "u gotta put coins in the machine mate");
					return;
				}
				if(bet < 1){
					m.say(target, "stop being a poor cunt and put money in the machine");
					return;
				}
				double usercash = Double.parseDouble(bank.getProperty(m.sender()));
				if(usercash < bet){
					m.say(target, "u dont have enough money for that mate");
					return;
				}
				if(Math.random() > 0.7){
					m.say(target, "bro you won! wow 3$" + bet + ", thats heaps g! drinks on u ay");
					write(m.sender(),usercash + bet);
				}
				else{
					m.say(target, "shit man, u lost 3$" + bet + ". better not let the middy know");
					write(m.sender(),usercash - bet);
				}
			}
		}
		if(m.botCommand().equals("mug")){
			if(!Info.isRegistered(m.sender())){
				m.say(target, "pls login m9");
				return;
			}
			if(m.hasBotParams()){
				String tomug = m.botParamsArray()[0];
				if(!bank.containsKey(m.sender())){
					m.say(target, "u dont even have an account to put that in");
					return;
				}
				if(!bank.containsKey(tomug) || get(tomug) < 1){
					m.say(target, "they dont have any money to steal");
					return;
				}
				if(pros.contains(m.sender())){
					double targetmoney = get(tomug);
					double tosteal = Math.floor(Math.random()*(targetmoney/2));
					m.say(target, String.format("oh shit, its the notorious %s! %s ran off at the sight of them, but accidentally dropped 3$%.0f",m.sender(),tomug,tosteal));
					write(tomug, targetmoney - tosteal);
					write(m.sender(), get(m.sender())+tosteal);
					return;
				}
				if(Math.random() > 0.1){
					jail.put(m.sender(), System.currentTimeMillis());
					m.say(target, "4█2█0,1POLICE4█2█ Its the police! looks like u got caught. thats five minutes the big house for you!");
				}
				else{
					double targetmoney = get(tomug);
					double tosteal = Math.floor(Math.random()*(targetmoney/3));
					m.say(target, String.format("u manage to steal 3$%.0f off %s",tosteal,tomug));
					write(tomug, targetmoney - tosteal);
					write(m.sender(), get(m.sender())+tosteal);
				}
			}
		}
		if(m.botCommand().equals("durry")){
			if(!Info.isRegistered(m.sender())){
				m.say(target, "pls login m9");
				return;
			}
			if(!bank.containsKey(m.sender())){
				m.say(target, "winz hasnt given u any money yet");
				return;
			}
			double usercash = get(m.sender());
			if(usercash < 10){
				m.say(target, "u dont have enough money for that mate");
				return;
			}
			m.notice(m.sender(), "uve bought a durry for 3$10");
			m.say(target,"               )");
			m.say(target,"              (");
			m.say(target," _ ___________ )");
			m.say(target,"[_[___________4#");
			
		}
		if(m.botCommand().equals("give")){
			if(!Info.isRegistered(m.sender())){
				m.say(target, "pls login m9");
				return;
			}
			if(m.hasBotParams()){
				String togiveto = m.botParamsArray()[0];
				double togive;
				try{
					togive = Integer.parseInt(m.botParamsArray()[1]);
				}
				catch (NumberFormatException e){
					m.say(target, "cmon man help a brother out");
					return;
				}
				if(togive < 1){
					m.say(target, "dont be a cheap cunt");
					return;
				}
				if(!bank.containsKey(m.sender())){
					m.say(target, "u dont even have an account");
					return;
				}
				if(get(m.sender()) < togive){
					m.say(target, "u dont have enuf money bro");
					return;
				}
				write(m.sender(), get(m.sender()) - togive);
				write(togiveto, get(togiveto) + togive);
				m.say(target, String.format("you gave %s 3$%.0f", togiveto, togive));
			}
		}
	}
	
	private void write(String nick, double amount){
		bank.setProperty(nick, String.valueOf(amount));
		try {
			bank.store(new FileWriter(new File(this.getClass().getResource("properties/money.properties").toURI())), "");
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private double get(String nickname){
		return Double.parseDouble(bank.getProperty(nickname));
	}
	
	private void checkJail(){
		for(String s : jail.keySet()){
			if(System.currentTimeMillis() > (jail.get(s) + (60*5*1000))){
				System.out.println(s + " is now out of jail");
				jail.remove(s);
			}
		}
	}
}
