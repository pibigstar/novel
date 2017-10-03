package com.lei.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lei.dao.NovelMapper;
import com.lei.service.INovelSaveToDB;
import com.lei.spider.entity.Novel;
import com.lei.spider.impl.novel.KanShuZhongNovelSpider;
import com.lei.spider.interfaces.INovelSpider;

@Service("novelService")
public class NovelSaveToDB implements INovelSaveToDB{

	protected static Map<String, String> TASKS = new HashMap<>();
	private NovelMapper novelMapper;
	@Autowired
	public void setNovelMapper(NovelMapper novelMapper) {
		this.novelMapper = novelMapper;
	}

	static {
		//A-Z
		for (int i = 65; i <= 90; i++) {
			TASKS.put(String.valueOf((char)i), "http://www.kanshuzhong.com/map/"+String.valueOf((char)i)+"/1/");
		}
	}

	@Override
	public void save() {
		ExecutorService service = Executors.newCachedThreadPool();
		List<Future<String>> futures = new ArrayList<>();

		for (Entry<String, String> entry : TASKS.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			futures.add(service.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					INovelSpider spider = new KanShuZhongNovelSpider();
					Iterator<List<Novel>> iterator = spider.iterator(value);
					while (iterator.hasNext()) {
						List<Novel> novels = iterator.next();
						for (Novel novel : novels) {
							novel.setFirstLetter(key.charAt(0));
							novel.setAddTime(new Date());
						}
						novelMapper.batchInsert(novels);
						Thread.sleep(1000);
					}
					return value;
				}
			}));
		}
		service.shutdown();
		for (Future<String> future : futures) {
			try {
				System.err.println(future.get()+"已完成");
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
}