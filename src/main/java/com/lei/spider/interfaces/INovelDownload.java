package com.lei.spider.interfaces;

import com.lei.spider.config.Configuration;

public interface INovelDownload {
	
	/**
	 * 给我一个小说列表页的url，我给你去下载小说
	 * @param url
	 * @param config
	 * @return
	 */
	public String download(String url,Configuration config);

}
