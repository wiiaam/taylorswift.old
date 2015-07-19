package modules;

import java.util.HashSet;

public class Modules {
	
	public static HashSet<Module> loadAll(){
		HashSet<Module> modules = new HashSet<Module>();
		modules.add(new IBIP());
		modules.add(new Admin());
		modules.add(new Cowsay());
		modules.add(new Fortune());
		modules.add(new Google());
		modules.add(new Help());
		modules.add(new Ignores());
		modules.add(new Invite());
		modules.add(new KiwiIRC());
		modules.add(new London());
		modules.add(new Money());
		modules.add(new NoBro());
		modules.add(new Ping());
		modules.add(new Quotes());
		modules.add(new Rainbow());
		modules.add(new Random());
		modules.add(new SongLyrics());
		modules.add(new Spam());
		modules.add(new Steam());
		modules.add(new Time());
		modules.add(new TopKek());
		modules.add(new Triggers());
		modules.add(new TitleReporting());
		modules.add(new UrbanDictionary());
		modules.add(new modules.UserInfo());
		modules.add(new Version());
		modules.add(new Voting());
		modules.add(new Youtube());
		modules.add(new Weather());
		
		return modules;
	}
}
