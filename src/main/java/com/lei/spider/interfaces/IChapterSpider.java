package com.lei.spider.interfaces;

import java.util.List;

import com.lei.spider.entity.Chapter;


public interface IChapterSpider {

	/**
	 * 给我一个url，我给你完整的章节列表
	 * @param url
	 * @return
	 */
	public List<Chapter> getChapter(String url);
}
