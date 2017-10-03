package com.lei.spider.impl.chapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lei.spider.NovelSiteEnum;
import com.lei.spider.config.Configuration;
import com.lei.spider.entity.Chapter;
import com.lei.spider.interfaces.IChapterSpider;
import com.lei.spider.utils.NovelSpiderUtil;
import com.lei.spider.utils.ReadXMLUtil;

public abstract class AbstractChapterSpider implements IChapterSpider {
	@Override
	public List<Chapter> getChapter(String url) {
		
		try {
			
			Configuration config = new Configuration();
			config.setTryTime(3);
			config.setTimeout(10000);
			NovelSpiderUtil spider = new NovelSpiderUtil(config);
			
			Document document = spider.getDocument(url);
			document.setBaseUri(url);
			Elements as = document.select(ReadXMLUtil.getContext(NovelSiteEnum.getEnumByUrl(url)).get("chapter-list-selector"));
			List<Chapter> chapters = new ArrayList<>();
			for (Element a : as) {
				Chapter chapter = new Chapter();
				chapter.setTitle(a.text());
				chapter.setUrl(a.absUrl("href"));
				chapters.add(chapter);
			}
			return chapters;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
