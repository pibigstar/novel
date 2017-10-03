package com.lei.spider.interfaces;

import java.util.Iterator;
import java.util.List;

import com.lei.spider.entity.Novel;

public interface INovelSpider {
	
	/**
	 * 给我一个URL，我给你一堆小说实体
	 * @param url
	 * @return
	 */
	public List<Novel> getNovel(String url);
	
	
	public Iterator<List<Novel>> iterator(String firstPage);
}
