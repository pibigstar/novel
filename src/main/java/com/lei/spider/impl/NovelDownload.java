package com.lei.spider.impl;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.lei.spider.NovelSiteEnum;
import com.lei.spider.config.Configuration;
import com.lei.spider.entity.Chapter;
import com.lei.spider.entity.ChapterDetail;
import com.lei.spider.interfaces.IChapterDetailSpider;
import com.lei.spider.interfaces.IChapterSpider;
import com.lei.spider.interfaces.INovelDownload;
import com.lei.spider.utils.ChapterDetailSpiderFactory;
import com.lei.spider.utils.ChapterSpiderFactory;
import com.lei.spider.utils.NovelSpiderUtil;

/**
 * 下载小说
 * Author: pibigstar
 * Created on: 2017年10月2日 下午10:40:15
 */
public class NovelDownload implements INovelDownload{

	/**
	 * 给我一个列表页的URL，我给你下载此小说
	 */
	@Override
	public String download(String url, Configuration config) {
		IChapterSpider spider = ChapterSpiderFactory.getChapterSpider(url);
		List<Chapter> chapters = spider.getChapter(url);
		int size = config.getSize();
		//Math.ceil()  10.5->11 10.2->11 10.8->11
		int maxThread =(int) Math.ceil(chapters.size() * 1.0 / size);
		int fromIndex = 0;
		int toIndex = 0;
		Map<String, List<Chapter>> downloadTask = new HashMap<>();
		for (int i = 1; i < maxThread + 1; i++) {
			//410   5个线程   i<6
			//i = 1 1-101       1-100
			//i = 2 101-200		101-200
			fromIndex = (i-1)*size + 1;
			toIndex = i == maxThread?chapters.size() : i * size;
			downloadTask.put(fromIndex + "-" + toIndex, chapters.subList(fromIndex-1, toIndex));
		}
		
		ExecutorService service = Executors.newFixedThreadPool(maxThread);
		Set<String> keySet = downloadTask.keySet();
		List<Future<String>> tasks = new ArrayList<>();
		String savePath = config.getPath();
		new File(savePath).mkdirs();
		for (String key : keySet) {
			tasks.add(service.submit(new DownloadCallable(downloadTask.get(key), savePath + "/" +key + ".txt",config.getTryTime())));
		}
		service.shutdown();
		
		for (Future<String> future : tasks) {
			try {
				System.out.println(future.get() + "下载完成");
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		NovelSpiderUtil.mutliFileMerge(savePath, null, true);
		return savePath+"/merge.txt";
	}
}

/**
 * 下载每个分集合里面的章节
 * Author: pibigstar
 * Created on: 2017年10月2日 下午10:39:06
 */
class DownloadCallable implements Callable<String>{
	private List<Chapter> chapters;
	private String path;
	private int tryTime;
	public DownloadCallable(List<Chapter> chapters, String path,int tryTime) {
		this.chapters = chapters;
		this.path = path;
		this.tryTime = tryTime;
	}

	@Override
	public String call() throws Exception {
		try(
			PrintWriter out = new PrintWriter(new File(path),"UTF-8");
			) {
			for (Chapter chapter : chapters) {
				IChapterDetailSpider spider = ChapterDetailSpiderFactory.getChapterDetailSpider(chapter.getUrl());
				
				for (int i = 0; i < tryTime; i++) {
					try {
						ChapterDetail detail = spider.getChapterDetail(chapter.getUrl());
						out.println(detail.getTitle());
						out.println(detail.getContent());
						break;
					} catch (RuntimeException e) {
						System.err.println("尝试["+(i+1)+"/"+tryTime+"]次下载失败了！");
					}
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return path;
	}
	
}
