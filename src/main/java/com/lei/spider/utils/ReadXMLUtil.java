package com.lei.spider.utils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lei.spider.NovelSiteEnum;

public class ReadXMLUtil {
	
	private static final Map<NovelSiteEnum,Map<String, String>> CONTEXT_MAP = new HashMap<>();
	static {
		init();
	}
	private ReadXMLUtil() {}

	@SuppressWarnings("unchecked")
	private static void init() {
		SAXReader reader = new SAXReader();
		try {
			 InputStream  in = Thread.currentThread().getContextClassLoader().getResourceAsStream("Spider-Rule.xml");
			Document document =	reader.read(in);
			Element root = document.getRootElement();
			
			List<Element> sites = root.elements("site");
			for (Element site : sites) {
				List<Element> subs = site.elements();
				Map<String, String> submap = new HashMap<>();
				for (Element sub : subs) {
					submap.put(sub.getName(), sub.getTextTrim());
				}
				
				CONTEXT_MAP.put(NovelSiteEnum.getEnumByUrl(submap.get("url")), submap);
			}
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 拿到对应网站的解析规则
	 * @param novelSiteEnum
	 * @return
	 */
	public static Map<String, String> getContext(NovelSiteEnum novelSiteEnum){
		return CONTEXT_MAP.get(novelSiteEnum);
	}
	
}
