package modules;

import java.util.HashSet;

import bot.Message;
import bot.Module;
import bot.Server;

public class Spam implements Module {
	
	private boolean started;
	private Thread thread;
	HashSet<String> spams = new HashSet<String>();
	
	public Spam(){
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					if(!Server.isConnected()) continue;
					for(String meServerServerage : spams){
						Server.send(meServerServerage);
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}
					if(started == false)break;
				}
				return;
			}
		});
	}
	
	@Override
	public void parse(Message m) {
		if(m.botCommand().equals("Serverpam") && m.senderIsAdmin()){
			if(m.hasBotParams()){
				spams.add(m.botParams());
				if(!started){
					started = true;
					thread.start();
				}
			}
		}
		if(m.botCommand().equals("stopspamming")){
			spams = new HashSet<String>();
			thread.interrupt();
		}
	}
}
