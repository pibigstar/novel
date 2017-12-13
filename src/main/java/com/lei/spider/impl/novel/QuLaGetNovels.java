package com.lei.spider.impl.novel;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lei.spider.config.Configuration;
import com.lei.spider.entity.Novel;
import com.lei.spider.utils.NovelSpiderUtil;

public class QuLaGetNovels {
	
	public static List<Novel> getNovels(String url){
		Configuration config = new Configuration();
		config.setTryTime(3);
		List<Novel> novels = new ArrayList<>();
		NovelSpiderUtil spiderUtil = new NovelSpiderUtil(config);
		try {
			Document document = spiderUtil.getDocument(url);
			
			Elements resultNodes = document.select("#results > div.result-list > div");
			for (Element element : resultNodes) {
				Novel novel = new Novel();
				String name = element.select("div.result-game-item-detail > h3 > a").text();
				String author = element.select("div.result-game-item-detail > div > p:nth-child(1) > span:nth-child(2)").text().trim();
				String novelUrl = element.select("div.result-game-item-detail > h3 > a").attr("abs:href");
				String type = element.select(" div.result-game-item-detail > div > p:nth-child(2) > span:nth-child(2)").text().trim();
				String lastChapter = element.select("div.result-game-item-detail > div > p:nth-child(4) > a").text();
				String lastUpdateChapterUrl = element.select("div.result-game-item-detail > div > p:nth-child(4) > a").attr("abs:href");
				
				novel.setLastUpdateChapterUrl(lastUpdateChapterUrl);
				novel.setName(name);
				novel.setUrl(novelUrl);
				novel.setAuthor(author);
				novel.setType(type);
				novel.setLastUpdateChapter(lastChapter);
				novels.add(novel);
			}
			return novels;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("解析小说列表失败！");
		}
	}
}
