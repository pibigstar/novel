package com.lei.spider.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.lei.spider.config.Configuration;
/**
 * 小说爬虫工具类
 * Author: pibigstar
 * Created on: 2017年10月2日 下午8:45:28
 */
public class NovelSpiderUtil {
	private Configuration config;

	public NovelSpiderUtil(Configuration config) {
		this.config = config;
	}

	/**
	 * 给我一个URL，我给你此网页源码
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	public Document getDocument(String url) throws Exception {
		Document document = null;
		int tryTime = config.getTryTime();
		for (int i = 0; i <tryTime; i++) {
			try {
				document = Jsoup.connect(url).timeout(config.getTimeout()).get();
				return document;
			} catch (RuntimeException e) {
				System.err.println("第" + (i+1) +"次尝试下载失败");
			}
		}
		return null;
	}

	/**
	 * 把多个文本文件合并为一个文件
	 * @param path  保存路径
	 * @param mergeToFile 你要保存路径文件名比如D:/完美世界.txt 如果 为null 
	 * 那么就为path/merge.txt
	 * @param isDelete 是否删除小文件
	 */
	public static void mutliFileMerge(String path , String mergeToFile, Boolean isDelete) {

		mergeToFile = mergeToFile == null ? path + "/merge.txt" : mergeToFile;


		//拿到path路径下所有以后缀为.txt 的文件
		File [] files = new File(path).listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt")&&name.contains("-");
			}
		});

		//对files进行排序
		Arrays.sort(files,new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				int o1Index = Integer.parseInt(o1.getName().split("-")[0]);
				int o2Index = Integer.parseInt(o2.getName().split("-")[0]);
				return o1Index - o2Index;
			}
		});

		//将分散的文件写到一个文件里
		try(
				PrintWriter out = new PrintWriter(new File(mergeToFile),"UTF-8");
				) {
			for (File file : files) {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));

				String line = null;
				while ((line = br.readLine())!=null) {
					out.println(line);
				}
				br.close();
				if (isDelete) {
					file.delete();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("文件合并失败！");
		}
	}

	/**
	 * 返回小说的状态
	 * @param text
	 * @return
	 */
	public static int getStatus(String status) {
		if (status.contains("连载")) {
			return 1;
		}else if (status.contains("完结")||status.contains("完成")) {
			return 2;
		}else {
			return 1;
		}
	}
	
	public static Date getDate(String date,String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
}
