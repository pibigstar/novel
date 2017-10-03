package com.lei.spider.impl.novel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lei.spider.NovelSiteEnum;
import com.lei.spider.entity.Novel;
import com.lei.spider.utils.NovelSpiderUtil;

public class KanShuZhongNovelSpider extends AbstractNovelSpider{
	
	/**
	 * 抓取小说实体
	 */
	@Override
	public List<Novel> getNovel(String url) {
		
		List<Novel> novels = new ArrayList<>();
		Elements trs = super.getTrs(url);
		
		for (int i = 1, size = trs.size(); i < size-1; i++) {
			
			Elements tds = trs.get(i).getElementsByTag("td");
			
			Novel novel = new Novel();

			novel.setType(tds.get(0).text());
			novel.setName(tds.get(1).text());
			novel.setUrl(tds.get(1).getElementsByTag("a").first().absUrl("href"));
			novel.setLastUpdateChapter(tds.get(2).text());
			novel.setLastUpdateChapterUrl(tds.get(2).getElementsByTag("a").first().absUrl("href"));
			novel.setAuthor(tds.get(3).text());
			novel.setLastUpdateTime(NovelSpiderUtil.getDate(tds.get(4).text(),"yyyy-MM-dd"));
			novel.setStatus(NovelSpiderUtil.getStatus(tds.get(5).text()));
			novel.setPlatformId(NovelSiteEnum.getEnumByUrl(url).getId());
			
			novels.add(novel);
		}
		return novels;
	}

}
