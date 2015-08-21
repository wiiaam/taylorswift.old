package modules;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bot.Config;
import bot.Message;
import bot.Module;
import bot.Server;

public class WolframAlpha implements Module {

	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.botCommand().equals("wa")){
			if(m.hasBotParams()){
				String search = m.botParams().replace(" ", "%20");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				try {
					DocumentBuilder db = dbf.newDocumentBuilder();
					Document dom = db.parse("http://api.wolframalpha.com/v2/query?appid=" + Config.getWolframAPIKey() + "&input=" + search);
					Element ele = dom.getDocumentElement();
					if(ele.getAttribute("success").equals("true")){
						NodeList nl = ele.getElementsByTagName("pod");
						Element pod1 = (Element)nl.item(1);
						Element subpod = (Element)pod1.getElementsByTagName("subpod").item(0);
						Element plaintext = (Element)subpod.getElementsByTagName("plaintext").item(0);
						String result = plaintext.getTextContent();
						Server.say(target, "[WA] Result: " + result);
						
					}
					
				} catch (ParserConfigurationException | SAXException | IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
