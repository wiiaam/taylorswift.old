package bot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.lang.reflect.*;
import java.net.URISyntaxException;

import modules.*;

public class Modules{
	
	private static ArrayList<Module> modules = new ArrayList<Module>();
	private static HashMap<String, Class<bot.Module>> c = new HashMap<String, Class<Module>>();
	
	public static void loadAll(){
		
		try {
			File directory = new File(new Modules().getClass().getResource("../modules/").toURI());
			if(directory.exists()){
				String[] files = directory.list();
				for(int i = 0; i < files.length; i++){
					if(files[i].endsWith(".class") && !files[i].endsWith("$1.class")){
						String className = files[i].substring(0, files[i].length()-6);
						if(className.equals("Quotes")) continue;
						try {
							load(className);
						} catch (ClassNotFoundException | IllegalArgumentException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		/*
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
		add(new ModuleLoader());
		add(new Money());
		add(new NoBro());
		add(new Ping());
		//add(new Quotes());
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
		*/
	}
	
	public static ArrayList<Module> getModules(){
		return modules;
	}
	public static boolean reload(String module){
		if(!unload(module))return false;
		try {
			load(module);
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}
	
	public static void load(String module) throws ClassNotFoundException, IllegalArgumentException{
		for(int i = 0; i < modules.size(); i++){
			if(modules.get(i).getClass().getSimpleName().equals(module)){
				throw new IllegalArgumentException("Module already loaded"); 
			}
		}
		Class cl;
		cl = Class.forName("modules." + module);
		Constructor con;
		try {
			con = cl.getConstructor();
			Module toadd = (Module)con.newInstance();
			modules.add(toadd);
		} catch (NoSuchMethodException | SecurityException | InstantiationException 
				| IllegalAccessException | IllegalArgumentException 
				| InvocationTargetException e) {
			e.printStackTrace();
		}
			
	}
	
	private static void add(Module m){
		System.out.println("added " + m.getClass().getSimpleName());
		modules.add(m);
		Collections.sort(modules, new Comparator<Module>() {

			@Override
			public int compare(Module o1, Module o2) {
				// TODO Auto-generated method stub
				return o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName());
			}
		});
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
	
	public static HashMap<String, String> getModuleStatuses(){
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			File directory = new File(new Modules().getClass().getResource("../modules/").toURI());
			if(directory.exists()){
				String[] files = directory.list();
				for(int i = 0; i < files.length; i++){
					if(files[i].endsWith(".class") && !files[i].endsWith("$1.class")){
						String className = files[i].substring(0, files[i].length()-6);
						boolean found = false;
						for(int j = 0; j < modules.size(); j++){
							if(modules.get(j).getClass().getSimpleName().equals(className)) {
								map.put(className, "loaded");
								found = true;
								break;
							}
							
						}
						if(!found) map.put(className, "unloaded");
					}
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return map;
	}
}
