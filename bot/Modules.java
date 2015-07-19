package bot;

import java.util.ArrayList;
import java.util.Iterator;

import modules.*;

public class Modules{
	
	private static ArrayList<Module> modules = new ArrayList<Module>();
	
	public static void loadAll(){
		add(new IBIP());
		add(new Admin());
		add(new Cowsay());
		add(new Fortune());
		add(new Google());
		add(new Help());
		add(new Ignores());
		add(new Invite());
		add(new KiwiIRC());
		add(new London());
		add(new Money());
		add(new NoBro());
		add(new Ping());
		add(new Quotes());
		add(new Rainbow());
		add(new Random());
		add(new SongLyrics());
		add(new Spam());
		add(new Steam());
		add(new Time());
		add(new TopKek());
		add(new Triggers());
		add(new TitleReporting());
		add(new UrbanDictionary());
		add(new modules.UserInfo());
		add(new Version());
		add(new Voting());
		add(new Youtube());
		add(new Weather());
	}
	public static ArrayList<Module> getModules(){
		return modules;
	}
	private static void reload(String module){
		unload(module);
		load(module);
	}
	
	public static boolean load(String module){
		for(int i = 0; i < modules.size(); i++){
			if(modules.get(i).getClass().getSimpleName().equals(module)){
				return false;
			}
		}
		return true;
	}
	
	private static void add(Module m){
		System.out.println("added " + m.getClass().getSimpleName());
		modules.add(m);
	}
	
	public static boolean unload(String module){
		for(int i = 0; i < modules.size(); i++){
			if(modules.get(i).getClass().getSimpleName().equals(module)){
				modules.remove(i);
				return true;
			}
		}
		return false;
	}
}
