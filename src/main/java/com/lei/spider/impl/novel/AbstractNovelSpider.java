package com.lei.spider.impl.novel;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lei.spider.NovelSiteEnum;
import com.lei.spider.config.Configuration;
import com.lei.spider.entity.Novel;
import com.lei.spider.interfaces.INovelSpider;
import com.lei.spider.utils.NovelSpiderUtil;
import com.lei.spider.utils.ReadXMLUtil;

public abstract class AbstractNovelSpider implements INovelSpider {

	protected Element nextPageElement;
	protected String nextPage;
	/**
	 * 给我一个URL，我就给你小说列表的tr的Elements
	 * @param url
	 * @return
	 */
	protected Elements getTrs(String url) {
		
		Configuration config = new Configuration();
		NovelSpiderUtil spiderUtil = new NovelSpiderUtil(config);
		try {
			Document document = spiderUtil.getDocument(url);
			document.setBaseUri(url);
			Map<String, String> context = ReadXMLUtil.getContext(NovelSiteEnum.getEnumByUrl(url));
			String novelSelector = context.get("novel-selector");
			
			if (novelSelector==null) {
				throw new RuntimeException("该网站目前不支持抓取其小说列表");
			}
			
			String nextPageSelector = context.get("novel-next-page-selector");
			nextPageElement = document.select(nextPageSelector).first();
			if (nextPageElement!=null) {
				nextPage = nextPageElement.absUrl("href");
			}else {
				nextPage = "";
			}
			Elements trs = document.select(novelSelector);
			return trs;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public Iterator<List<Novel>> iterator(String firstPage) {
		nextPage = firstPage;
		return new NovelIterator();
	}
	
	private class NovelIterator implements Iterator<List<Novel>>{

		@Override
		public boolean hasNext() {
			return !nextPage.isEmpty();
		}

		@Override
		public List<Novel> next() {
			return getNovel(nextPage);
		}
	} 
}
