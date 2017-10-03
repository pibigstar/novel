package com.lei.spider.utils;

import com.lei.spider.NovelSiteEnum;
import com.lei.spider.impl.chapter.DefaultChapterSpider;
import com.lei.spider.interfaces.IChapterSpider;

public class ChapterSpiderFactory {
	private ChapterSpiderFactory() {}
	public static IChapterSpider getChapterSpider(String url) {

		IChapterSpider chapterSpider = null;
		NovelSiteEnum novelSiteEnum = NovelSiteEnum.getEnumByUrl(url);
		switch (novelSiteEnum) {
		case BXWX:
		case BIQUGE:
		case KANSHUZHONG:
			chapterSpider = new DefaultChapterSpider();break;
		}
		return chapterSpider;
	}

}
