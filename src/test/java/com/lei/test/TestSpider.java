package com.lei.test;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.lei.spider.NovelSiteEnum;
import com.lei.spider.config.Configuration;
import com.lei.spider.entity.Chapter;
import com.lei.spider.entity.ChapterDetail;
import com.lei.spider.entity.Novel;
import com.lei.spider.impl.NovelDownload;
import com.lei.spider.impl.chapter.DefaultChapterDetailSpider;
import com.lei.spider.impl.chapter.DefaultChapterSpider;
import com.lei.spider.impl.novel.KanShuZhongNovelSpider;
import com.lei.spider.impl.novel.QuLaGetNovels;
import com.lei.spider.interfaces.IChapterDetailSpider;
import com.lei.spider.interfaces.IChapterSpider;
import com.lei.spider.interfaces.INovelDownload;
import com.lei.spider.interfaces.INovelSpider;
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
		config.setPath("D:/novel");
		String result = download.download("http://www.kanshuzhong.com/book/109563/", config);
		System.out.println("文件下载好了，保存在："+result+"里，快去查看吧！");
	}
	
	@Test
	public void testGetFiles() {
		Configuration config = new Configuration();
		config.setPath("D:/novel");
		NovelSpiderUtil.mutliFileMerge(config.getPath(), "1.txt",false);
	}
	
	@Test
	public void testMergeToFile() {
		Configuration config = new Configuration();
		config.setPath("D:/novel2");
		NovelSpiderUtil.mutliFileMerge(config.getPath(), null, true);
	}
	
	
	@Test
	public void testGetNovels() {
		INovelSpider spider = new KanShuZhongNovelSpider();
		Iterator<List<Novel>> iterator = spider.iterator("http://www.kanshuzhong.com/map/A/1/");
		int i = 1;
		while (iterator.hasNext()) {
			System.err.println(i++);
			List<Novel> novels = iterator.next();
			for (Novel novel : novels) {
				System.out.println(novel);
			}
		}
	}
	
	@Test
	public void testGet() {
		QuLaGetNovels.getNovels("http://zhannei.baidu.com/cse/search?s=3677118700255927857&q=%E9%80%8D%E9%81%A5%E5%B0%8F%E4%B9%A6%E7%94%9F");
	}

}
