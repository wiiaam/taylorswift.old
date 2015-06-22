package extras;

public class Rainbowfy {
	public static String convert(String s){
		char[] chars = s.toCharArray();
		String message = "";
		int sequence = 0;
		for(int i = 0; i < chars.length; i++){
			if(i == 7*sequence + 0) message += "20" + chars[i];
			if(i == 7*sequence + 1) message += "23" + chars[i];
			if(i == 7*sequence + 2) message += "24" + chars[i];
			if(i == 7*sequence + 3) message += "25" + chars[i];
			if(i == 7*sequence + 4) message += "12" + chars[i];
			if(i == 7*sequence + 5) message += "22" + chars[i];
			if(i == 7*sequence + 6) {
				message += "13" + chars[i];
				sequence++;
			}
		}
		return message;
	}
}
