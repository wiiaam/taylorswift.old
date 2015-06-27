package modules;


import java.util.HashSet;

import bot.Message;

public class Voting implements Module {
	
	private boolean isvoting = false;
	private HashSet<String> voted = new HashSet<String>();
	private double yes = 0;
	private double votes = 0;
	private String voteroom;
	private Message m;
	private String topic;
	private long lastvote = System.currentTimeMillis();
	//private Thread votethread;
	
	@Override
	public void parse(Message message) {
		m = message;
		if((lastvote + 1000*60) < System.currentTimeMillis()) {
			isvoting = false;
			voted.clear();
		}
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.botCommand().equals("vote")){
			if(!isvoting){
				isvoting = true;
				voteroom = target;
				startVote();
				topic = m.botParams();
				m.say(target, "A vote has been started! Voting will last 60 seconds. The topic is: \"" + topic + "\". Vote with " + m.commandChar() + "voteyes and " + m.commandChar() + "voteno");
				lastvote = System.currentTimeMillis();
			}
			else{
				m.say(target,"A vote for \"" + topic + "\" is already in progress");
			}
		}
		if(m.botCommand().equals("voteyes") || m.botCommand().equals("voteno")){
			if(isvoting){
				if(!voted.contains(m.sender())){
					voted.add(m.sender());
					if(m.botCommand().equals("voteyes")){
						yes++;
						votes++;
						m.say(target,m.sender() + ": You have voted yes");
					}
					if(m.botCommand().equals("voteno")){
						votes++;
						m.say(target,m.sender() + ": You have voted no");
					}
				}
				else{
					m.say(target,m.sender() + ": You have already voted");
					
				}
			}
			else{
				m.say(target, "There is currently no vote being run");
			}
		}
		if(m.botCommand().equals("votestop")){
			m.say(target, "Vote Stopped");
			isvoting = false;
			yes = 0;
			votes = 0;
			lastvote -= 1000*60;
			voted.clear();
		}
	}
	private void startVote(){
		new Thread(new Runnable(){
			public void run(){
				try {
					Thread.sleep(1000*30);
					if(!isvoting)return;
					m.say(voteroom, "The vote will end in 30 seconds\r\n");
					Thread.sleep(1000*20);
					if(!isvoting)return;
					m.say(voteroom, "The vote will end in 10 seconds\r\n");
					Thread.sleep(1000*10);
					if(!isvoting)return;
				} 
				catch (InterruptedException e) {}
				if(votes == 0) m.say(voteroom, "Voting finished! No votes were sent");
				double percentyes = (yes/votes*100);
				double percentno = 100 - percentyes;
				String result = String.format("Voting finished! Results were %.0f yes (%.0f%%), %.0f no (%.0f%%) out of %.0f votes" , yes, percentyes, (votes-yes), percentno, votes);
				m.say(voteroom, result);
				yes = 0;
				votes = 0;
				
			}
		}).start();
	}
}
