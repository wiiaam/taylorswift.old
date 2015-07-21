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
	
	public static void loadAll(){
		
		try {
			File directory = new File(new Modules().getClass().getResource("../modules/").toURI());
			if(directory.exists()){
				String[] files = directory.list();
				for(int i = 0; i < files.length; i++){
					if(files[i].endsWith(".class") && !files[i].endsWith("$1.class")){
						String className = files[i].substring(0, files[i].length()-6);
						//if(className.equals("Quotes")) continue;
						new Thread(new Runnable(){
							public void run(){
								try {
									load(className);
								} catch (ClassNotFoundException | IllegalArgumentException e) {
									e.printStackTrace();
								}									
							}
						}).start();
					}
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void load(String module) throws ClassNotFoundException, IllegalArgumentException{
		for(int i = 0; i < modules.size(); i++){
			if(modules.get(i).getClass().getSimpleName().equals(module)){
				throw new IllegalArgumentException("Module already loaded"); 
			}
		}
		Class cl;
		cl = Class.forName("modules." + module);
		Class[] interfaces = cl.getInterfaces();
		boolean isModule = false;
		for(int i = 0; i < interfaces.length; i++){
			if(interfaces[i].equals(bot.Module.class)) isModule = true;
		}
		if(!isModule)throw new IllegalArgumentException("Class does not implement module");
		Constructor con;
		try {
			con = cl.getConstructor();
			Module toadd = (Module)con.newInstance();
			add(toadd);
		} catch (NoSuchMethodException | SecurityException | InstantiationException 
				| IllegalAccessException | IllegalArgumentException 
				| InvocationTargetException e) {
			e.printStackTrace();
		}
			
	}
	
	private static void add(Module m){
		final ArrayList<Module> modulesloaded = new ArrayList<Module>(modules);
		for(Module module : modulesloaded){
			if(m.getClass().getSimpleName().equals(module.getClass().getSimpleName())) return;
		}
		modules.add(m);
		Collections.sort(modules, new Comparator<Module>() {

			@Override
			public int compare(Module o1, Module o2) {
				// TODO Auto-generated method stub
				return o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName());
			}
		});
		System.out.println("added " + m.getClass().getSimpleName());
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
	
	@SuppressWarnings("rawtypes")
	public static HashMap<String, String> getModuleStatuses(){
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			File directory = new File(new Modules().getClass().getResource("../modules/").toURI());
			if(directory.exists()){
				String[] files = directory.list();
				for(int i = 0; i < files.length; i++){
					if(files[i].endsWith(".class") && !files[i].endsWith("$1.class")){
						String className = files[i].substring(0, files[i].length()-6);
						Class cl;
						boolean isModule = false;
						try {
							cl = Class.forName("modules." + className);
							Class[] interfaces = cl.getInterfaces();
							
							for(int j = 0; j < interfaces.length; j++){
								if(interfaces[j].equals(bot.Module.class)) isModule = true;
							}
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						if(!isModule)continue;
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
