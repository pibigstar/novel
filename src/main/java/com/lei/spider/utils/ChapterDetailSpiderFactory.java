package com.lei.spider.utils;

import com.lei.spider.NovelSiteEnum;
import com.lei.spider.impl.DefaultChapterDetailSpider;
import com.lei.spider.interfaces.IChapterDetailSpider;

public class ChapterDetailSpiderFactory {
	private ChapterDetailSpiderFactory() {}

	public static IChapterDetailSpider getChapterDetailSpider(String url) {
		NovelSiteEnum novelSiteEnum = NovelSiteEnum.getEnumByUrl(url);
		IChapterDetailSpider chapterDetailSpider = null;

		switch (novelSiteEnum) {
		case BXWX:
		case BIQUGE:
		case KANSHUZHONG:
			chapterDetailSpider = new DefaultChapterDetailSpider();	break;
		}
		
		return chapterDetailSpider;
	}

}
