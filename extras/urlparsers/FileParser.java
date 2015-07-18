package extras.urlparsers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

public class FileParser {
	public static String find(URLConnection urlc){
		double bytes = (double)urlc.getContentLengthLong();
		double kilobytes = (bytes / 1024);
		double megabytes = (kilobytes / 1024);
		double gigabytes = (megabytes / 1024);
		double terabytes = (gigabytes / 1024);
		double petabytes = (terabytes / 1024);
		double exabytes = (petabytes / 1024);
		double zettabytes = (exabytes / 1024);
		double yottabytes = (zettabytes / 1024);
		
		String filesize = "";
		if(bytes < 1000){
			filesize = String.format("%dB ", Math.round(bytes));
		}
		else if(kilobytes < 1000){
			filesize = String.format("%dKB ", Math.round(kilobytes));
		}
		else if(megabytes < 1000){
			filesize = String.format("%dMB ", Math.round(megabytes));
		}
		else if(gigabytes < 1000){
			filesize = String.format("%dGB ", Math.round(gigabytes));
		}
		else if(terabytes < 1000){
			filesize = String.format("%dTB ", Math.round(terabytes));
		}
		else if(petabytes < 1000){
			filesize = String.format("%dPB ", Math.round(petabytes));
		}
		else if(exabytes < 1000){
			filesize = String.format("%dEB ", Math.round(exabytes));
		}
		else if(zettabytes < 1000){
			filesize = String.format("%dZB ", Math.round(zettabytes));
		}
		else if(yottabytes < 1000){
			filesize = String.format("%dYB ", Math.round(yottabytes));
		}
		String type = "" + urlc.getContentType() + "";
		if(urlc.getContentType().startsWith("image")){
			try{
				BufferedImage image = ImageIO.read(urlc.getURL());
				type += " (" + image.getWidth() + " x " + image.getHeight() + ")";
			} catch(IOException e){}
		}
		if(urlc.getContentType().startsWith("application")){
			type = "application";
		}
		String title = String.format("File Type: %s size: %s", type, filesize);
		return title;
	}
}
