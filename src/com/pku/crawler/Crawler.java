package com.pku.crawler;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class Crawler {
	private String url;
	public Crawler(String url) {
		this.url = url;
	}

	public String getResponse(){
		String responseBody = null;
		CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpGet httpGet=new HttpGet(url);
		ResponseHandler<String> responseHandler=new ResponseHandler<String>() {

			@Override
			public String handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				int statusCode=response.getStatusLine().getStatusCode();
				if(statusCode==200){
					
					String res=EntityUtils.toString(response.getEntity());
					return res;
				}else{
					throw new ClientProtocolException("statusCode:"+statusCode);
				}
			}
			
		};
		try {
			responseBody=httpClient.execute(httpGet,responseHandler);
			//System.out.println(responseBody);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseBody;

	}

}
