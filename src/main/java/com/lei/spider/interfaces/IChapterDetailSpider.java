package com.lei.spider.interfaces;

import com.lei.spider.entity.ChapterDetail;

public interface IChapterDetailSpider {
	
	/**
	 * 给我一个URL，我给你一个章节实体
	 * @param url
	 * @return
	 */
	public ChapterDetail getChapterDetail(String url);

}
