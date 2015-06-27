package modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import bot.Message;

public class TopKek implements Module {

	Properties keks = new Properties();
	
	public TopKek(){
		try {
			keks.load(new FileInputStream(new File(this.getClass().getResource("properties/keks.properties").toURI())));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.botCommand().equals("kek")){
			if(m.hasBotParams()){
				for(String s : m.botParamsArray()){
					if(keks.getProperty(s) != null) m.say(target, keks.getProperty(s));
				}
			}
		}
		if(m.botCommand().equals("setkek")){
			if(m.hasBotParams()){
				String kek = "";
				for(int i = 1; i < m.botParamsArray().length; i++){
					kek += m.botParamsArray()[i] + " ";
				}
				if(kek.equals("null ")) kek = null;
				keks.setProperty(m.botParamsArray()[0], kek);
				try {
					keks.store(new FileWriter(new File(this.getClass().getResource("properties/keks.properties").toURI())), "");
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
