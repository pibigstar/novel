package com.lei.spider;

/**
 * 已支持的小说网站枚举
 * Author: pibigstar
 * Created on: 2017年10月1日 下午8:24:30
 */
public enum NovelSiteEnum {
	BXWX(1,"bixia.org"),
	KANSHUZHONG(2,"kanshuzhong.com"),
	BIQUGE(3,"qu.la");
	private int id;
	private String url;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	private NovelSiteEnum(int id, String url) {
		this.id = id;
		this.url = url;
	}
	
	public static NovelSiteEnum getEnumById(int id) {
		switch (id) {
		case 1:return BXWX;
		case 2:return KANSHUZHONG;
		case 3:return BIQUGE;
		default:throw new RuntimeException("id=" + id + "是不被支持的小说网站");
		}
	}
	
	public static NovelSiteEnum getEnumByUrl(String url) {
		for (NovelSiteEnum novelSiteEnum : values()) {
			if (url.contains(novelSiteEnum.url)) {
				return novelSiteEnum;
			}
		}
		throw new RuntimeException("url=" + url + "是不被支持的url");
	}
}
