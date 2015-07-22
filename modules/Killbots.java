package modules;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

import bot.Message;
import bot.Module;
import bot.Server;
import bot.config.Config;

public class Killbots implements Module {
	
	private HashSet<String> kicks = new HashSet<String>();
	private HashMap<String, String> room = new HashMap<String, String>();
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#"))target = m.sender();
		if(m.command().equals("JOIN")){
			if(kicks.contains(m.sender()) && m.trailing().equals(room.get(m.sender()))){
				Server.send("KICK " + room.get(m.sender()) + " " + m.sender());
				Server.send("INVITE " + m.sender() + " " + room.get(m.sender()));
				//Server.send("TOPIC " + m.trailing() + " :RIP " + m.sender() + " died on " + LocalDate.now().getDayOfMonth() + " " + LocalDate.now().getMonth());
			}
		}
		
		if(m.command().equals("QUIT")){
			if(kicks.contains(m.sender())){
				Server.send("PART " + room.get(m.sender()));
				kicks.remove(m.sender());
				room.remove(m.sender());
			}
		}
		
		if(m.botCommand().equals("kill") && m.senderIsAdmin()){
			if(m.botParamsArray().length == 2){
				String tokill = m.botParamsArray()[0];
				String channel = m.botParamsArray()[1];
				kicks.add(tokill);
				room.put(tokill, channel);
				Server.send("JOIN " + channel);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Server.send("INVITE " + tokill + " " + channel);
				autoclose(tokill);
				
			}
		}
	}
	
	private void autoclose(String nick){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Server.send("PART " + room.get(nick));
				kicks.remove(nick);
				room.remove(nick);
			}
		}).start();
	}

}
