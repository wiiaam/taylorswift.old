package modules;

import bot.Config;
import bot.Message;
import bot.Module;
import bot.Server;
import bot.info.Info;

public class UserModes implements Module {
	
	private boolean started = false;
	@Override
	public void parse(Message m) {
		if(m.command().equals("352")){
			bot.info.Info.parse(m.trailing());
		}
		
		if(m.command().equals("NICK")){
			Info.forget(m.sender());
			Server.send("WHO " + m.trailing());
		}
		if(m.command().equals("PART") || m.command().equals("KICK")){
			Info.removeChan(m.sender(), m.param());
		}
		if(m.command().equals("QUIT")){
			Info.forget(m.sender());
		}
		if(m.command().equals("JOIN")){
			Info.parse(String.format("%s %s %s * %s H :0 Unknown", m.trailing(), m.username(), m.senderHost(), m.sender()));
		}
		if(m.command().equals("MODE")){
			Server.send("WHO " + m.param());
		}
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
					if(!Server.isConnected()) continue;
					for(String room : Config.getRooms()){
						try {
							long waittime = (long)((double)1 / Config.getRooms().size()*120000);
							Thread.sleep(waittime);
						} catch (InterruptedException e) {}
						Server.send("WHO " + room);	
					}
				}
			}
		}).start();
	}
}