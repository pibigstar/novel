package com.lei.spider.utils;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.lei.spider.config.Configuration;
/**
 * 给我一个URL，我把这个URL的网页的document对象给你
 * Author: pibigstar
 * Created on: 2017年10月2日 下午8:45:28
 */
public class NovelSpiderUtil {
	private Configuration config;

	public NovelSpiderUtil(Configuration config) {
		this.config = config;
	}
	
	@SuppressWarnings("unused")
	public Document getDocument(String url) {
		Document document = null;
		int tryTime = config.getTryTime();
		for (int i = 0; i <tryTime; i++) {
			try {
				document = Jsoup.connect(url).timeout(config.getTimeout()).get();
				return document;
			} catch (IOException e) {
				throw new RuntimeException("第" + i +"次尝试下载失败");
			}
		}
		return null;
	}
}
