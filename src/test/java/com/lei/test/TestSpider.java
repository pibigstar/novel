package com.lei.test;

import java.util.List;

import org.junit.Test;

import com.lei.spider.NovelSiteEnum;
import com.lei.spider.config.Configuration;
import com.lei.spider.entity.Chapter;
import com.lei.spider.entity.ChapterDetail;
import com.lei.spider.impl.NovelDownload;
import com.lei.spider.impl.chapter.DefaultChapterDetailSpider;
import com.lei.spider.impl.chapter.DefaultChapterSpider;
import com.lei.spider.interfaces.IChapterDetailSpider;
import com.lei.spider.interfaces.IChapterSpider;
import com.lei.spider.interfaces.INovelDownload;
import com.lei.spider.utils.NovelSpiderUtil;
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
		config.setPath("D:/novel2");
		String result = download.download("http://www.kanshuzhong.com/book/103251/", config);
		System.out.println("文件下载好了，保存在："+result+"里，快去查看吧！");
	}
	
	@Test
	public void testGetFiles() {
		Configuration config = new Configuration();
		config.setPath("D:/novel");
		NovelSpiderUtil spiderUtil = new NovelSpiderUtil(config);
		spiderUtil.mutliFileMerge(config.getPath(), "1.txt",false);
	}
	
	@Test
	public void testMergeToFile() {
		Configuration config = new Configuration();
		config.setPath("D:/novel2");
		NovelSpiderUtil spiderUtil = new NovelSpiderUtil(config);
		spiderUtil.mutliFileMerge(config.getPath(), null, true);
	}

}
