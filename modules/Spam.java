package modules;

import java.util.HashSet;

import bot.Message;
import bot.Server;

public class Spam implements Module {

	private Server s;
	private boolean started = false;
	private Thread thread;
	HashSet<String> spams = new HashSet<String>();
	
	public Spam(){
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					for(String message : spams){
						s.send(message);
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
		if(m.botCommand().equals("spam") && m.senderIsAdmin()){
			if(m.hasBotParams()){
				spams.add(m.botParams());
				if(!started){
					s = m.server;
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
