package com.lei.test;

import java.util.List;

import org.junit.Test;

import com.lei.spider.NovelSiteEnum;
import com.lei.spider.config.Configuration;
import com.lei.spider.entity.Chapter;
import com.lei.spider.entity.ChapterDetail;
import com.lei.spider.impl.DefaultChapterDetailSpider;
import com.lei.spider.impl.DefaultChapterSpider;
import com.lei.spider.impl.NovelDownload;
import com.lei.spider.interfaces.IChapterDetailSpider;
import com.lei.spider.interfaces.IChapterSpider;
import com.lei.spider.interfaces.INovelDownload;
import com.lei.spider.utils.ReadXMLUtil;

public class TestSpider {
	
	@Test
	public void testChapterSpider() {
		IChapterSpider spider = new DefaultChapterSpider();
		List<Chapter> characters  = spider.getChapter("http://www.qu.la/book/16431/");
		for (Chapter chapter : characters) {
			System.out.println(chapter);
		}
	}
	
	@Test
	public void testGetContext() {
		System.out.println(ReadXMLUtil.getContext(NovelSiteEnum.getEnumByUrl("http://www.qu.la/book/16431/")));
	}
	
	@Test
	public void testGetChapterDetail() {
		IChapterDetailSpider detailSpider = new DefaultChapterDetailSpider();
		ChapterDetail chapterDetail = detailSpider.getChapterDetail("http://www.bixia.org/27_27047/1806509.html");
		System.out.println(chapterDetail);
	}
	
	@Test
	public void testDownload() {
		INovelDownload download = new NovelDownload();
		
		Configuration config = new Configuration();
		config.setPath("D:/novel");
		download.download("http://www.bixia.org/27_27047/", config);
	}

}
