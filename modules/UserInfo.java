package modules;

import bot.IrcBot;
import bot.Message;
import bot.Server;
import bot.config.Config;

public class UserInfo implements Module {
	
	private Server s;
	private boolean started = false;
	@Override
	public void parse(Message m) {
		if(m.command().equals("352")){
			bot.UserInfo.parse(m.trailing());
		}
		
		if(m.command().equals("NICK")){
			bot.UserInfo.changeNick(m.sender(), m.trailing());
		}
		if(m.command().equals("PART")){
			bot.UserInfo.removeChan(m.sender(), m.param());
		}
		if(m.command().equals("QUIT")){
			bot.UserInfo.remove(m.sender());
		}
		s = m.server;
		if(!started){
			started = true;
			check();
		}
	}
	
	private void check(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					for(String room : Config.getRooms()){
						try {
							long waittime = (long)((double)1 / Config.getRooms().size()*120000);
							Thread.sleep(waittime);
						} catch (InterruptedException e) {}
						s.send("WHO #" + room);	
					}
				}
			}
		}).start();
	}
}
