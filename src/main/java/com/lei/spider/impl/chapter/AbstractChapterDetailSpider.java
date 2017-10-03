package com.lei.spider.impl.chapter;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.lei.spider.NovelSiteEnum;
import com.lei.spider.config.Configuration;
import com.lei.spider.entity.ChapterDetail;
import com.lei.spider.interfaces.IChapterDetailSpider;
import com.lei.spider.utils.NovelSpiderUtil;
import com.lei.spider.utils.ReadXMLUtil;

public abstract class AbstractChapterDetailSpider implements IChapterDetailSpider {
	@SuppressWarnings("unused")
	@Override
	public ChapterDetail getChapterDetail(String url) {
		try {
			Configuration config = new Configuration();
			config.setTryTime(3);
			config.setTimeout(10000);
			NovelSpiderUtil spider = new NovelSpiderUtil(config);
			
			Document document = spider.getDocument(url);
			document.setBaseUri(url);
			Map<String, String> context = ReadXMLUtil.getContext(NovelSiteEnum.getEnumByUrl(url));
			ChapterDetail chapterDetail = new ChapterDetail();
			
			//拿章节标题
			String titleSelector = context.get("chapter-detail-title-selector");
			chapterDetail.setTitle(document.select(titleSelector).text());
			//拿章节内容
			String contentSelector = context.get("chapter-detail-content-selector");
			chapterDetail.setContent(document.select(contentSelector).text());
			//拿上一页地址
			String prevSelector = context.get("chapter-detail-prev-selector");
			chapterDetail.setPrev(document.select(prevSelector).first().absUrl("href"));
			//拿下一页地址
			String nextSelector = context.get("chapter-detail-next-selector");
			chapterDetail.setNext(document.select(nextSelector).first().absUrl("href"));
			
			return chapterDetail;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
