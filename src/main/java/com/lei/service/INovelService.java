package com.lei.service;

import java.util.List;

import com.lei.spider.entity.Chapter;
import com.lei.spider.entity.ChapterDetail;
import com.lei.spider.entity.Novel;

public interface INovelService {
	
	/**根据小说或作者名字模糊查询*/
	public List<Novel> getNovelsByKeyWord(String keyWord);
	/**抓取小说到数据库中*/
	public void save();
	/**根据url获得小说的列表*/
	public List<Chapter> getChapterList(String url);
	/** 获得章节的详细内容*/
	public ChapterDetail getChapterDetail(String url);

}
