package com.lei.test;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.junit.Test;

public class HttpclientTest {

	@Test
	public void testPost() {
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			
			HttpPost httpPost = new HttpPost();
			byte b [] = new byte[1024];
			HttpEntity entity = new ByteArrayEntity(b);
			httpPost.setEntity(entity);
			
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			
			
		}catch (Exception e) {
			
		}finally {
			////不改了，，那个httpclient得手动关闭
		}

	}
	
	@Test
	public void testJsoupPost() {
		Connection connection = Jsoup.connect("");
		connection.method(Method.POST);
	}
}