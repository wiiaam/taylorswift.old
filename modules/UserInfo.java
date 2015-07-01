package modules;

import bot.Message;
import bot.config.Config;

public class UserInfo implements Module {
	
	private Message m;
	@Override
	public void parse(Message m) {
		this.m = m;
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
	}
	
	public UserInfo(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					if(m != null){
						for(String s : Config.getRooms()){
							try {
								long waittime = (long)((double)1 / Config.getRooms().size()*60000);
								Thread.sleep(waittime);
							} catch (InterruptedException e) {
							m.send("WHO #" + s);
							
							}
						}
					}
				}
				
			}
		}).start();
	}

}
