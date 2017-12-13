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
import com.lei.service.INovelService;
import com.lei.spider.entity.Chapter;
import com.lei.spider.entity.ChapterDetail;
import com.lei.spider.entity.Novel;
import com.lei.spider.impl.novel.KanShuZhongNovelSpider;
import com.lei.spider.impl.novel.QuLaGetNovels;
import com.lei.spider.interfaces.IChapterDetailSpider;
import com.lei.spider.interfaces.IChapterSpider;
import com.lei.spider.interfaces.INovelSpider;
import com.lei.spider.utils.ChapterDetailSpiderFactory;
import com.lei.spider.utils.ChapterSpiderFactory;

@Service("novelService")
public class NovelServiceImpl implements INovelService {

	
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
	
	/**
	 * 模糊查询
	 */
	@Override
	public List<Novel> getNovelsByKeyWord(String keyWord) {
		keyWord = "%" +keyWord +"%";
		return novelMapper.getNovelsByKeyWord(keyWord);
	}

	@Override
	public List<Chapter> getChapterList(String url) {
		IChapterSpider spider = ChapterSpiderFactory.getChapterSpider(url);
		List<Chapter> chapters = spider.getChapter(url);
		return chapters;
	}

	@Override
	public ChapterDetail getChapterDetail(String url) {
		IChapterDetailSpider detailSpider = ChapterDetailSpiderFactory.getChapterDetailSpider(url);
		ChapterDetail detail = detailSpider.getChapterDetail(url);
		detail.setContent(detail.getContent().replaceAll("     ", "     <br><br>"));
		return detail;
	}

	/**
	 * 解析qu.la 上的搜索页
	 */
	@Override
	public List<Novel> getNovels(String keyWord,String plat) {
		String baseUrl = "";
		if ("1".equals(plat)) {
			baseUrl = "http://zhannei.baidu.com/cse/search?s=3677118700255927857&q=";
		}else if ("2".equals(plat)) {
			baseUrl = "http://zhannei.baidu.com/cse/search?s=920895234054625192&q=";
		}
		String url = baseUrl+keyWord;
		return QuLaGetNovels.getNovels(url);
	}

}
