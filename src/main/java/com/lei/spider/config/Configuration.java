package com.lei.spider.config;

public class Configuration {
	
	/**
	 * 下载后保存的路径
	 */
	private String path;

	/**
	 * 每个线程最多下载多少章节
	 * @return
	 */
	private int size = 100;
	
	/**
	 * 超时时间
	 */
	private int timeout = 10000;
	/**
	 * 尝试次数
	 */
	private int tryTime = 3;

	
	public Configuration() {
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public int getTryTime() {
		return tryTime;
	}
	public void setTryTime(int tryTime) {
		this.tryTime = tryTime;
	}
	
	
}
