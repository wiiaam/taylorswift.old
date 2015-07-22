package extras;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture.AsynchronousCompletionTask;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import bot.config.Config;
public class URLShortener {

	 
	public static String shorten(String url){
		CloseableHttpClient httpclient;
		try {
			String api = "https://www.googleapis.com/urlshortener/v1/url?key=" + Config.getGoogleApiKey() ;
			httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(api);
			
			String jsonstring = "{\"longUrl\": \"" + url + "\"}";
			StringEntity params = new StringEntity(jsonstring);
			httpPost.addHeader("content-type", "application/json");
			httpPost.setEntity(params);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			String jsonoutput = (EntityUtils.toString(response.getEntity()));
			EntityUtils.consume(response.getEntity());
			Gson gson = new GsonBuilder().create();
			JsonObject json = gson.fromJson(jsonoutput, JsonElement.class).getAsJsonObject();
			return json.get("id").getAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
