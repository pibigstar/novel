package com.lei.spider.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 小说实体
 * Author: pibigstar
 * Created on: 2017年10月3日 下午12:12:53
 */
public class Novel implements Serializable {
	private static final long serialVersionUID = 4834523404092493662L;
	/** 小说ID */
	private int id;
	/** 小说名字 */
	private String name;
	/** 作者 */
	private String author;
	/** 链接*/
	private String url;
	/** 类型 修真，玄幻*/
	private String type;
	/** 最后一章 */
	private String lastUpdateChapter;
	/** 最后一章的连接*/
	private String lastUpdateChapterUrl;
	/** 最后的更新时间*/
	private Date lastUpdateTime;
	/** 状态 1 连载 2完结 */
	private int status;
	/** 首字母 ABCDE... */
	private char firstLetter;
	/** 平台id */
	private int platformId;
	/** 小说存储到数据库的时间 */
	private Date addTime;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLastUpdateChapter() {
		return lastUpdateChapter;
	}
	public void setLastUpdateChapter(String lastUpdateChapter) {
		this.lastUpdateChapter = lastUpdateChapter;
	}
	public String getLastUpdateChapterUrl() {
		return lastUpdateChapterUrl;
	}
	public void setLastUpdateChapterUrl(String lastUpdateChapterUrl) {
		this.lastUpdateChapterUrl = lastUpdateChapterUrl;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public char getFirstLetter() {
		return firstLetter;
	}
	public void setFirstLetter(char firstLetter) {
		this.firstLetter = firstLetter;
	}
	public int getPlatformId() {
		return platformId;
	}
	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	@Override
	public String toString() {
		return "Novel [name=" + name + ", author=" + author + ", url=" + url + ", type=" + type + ", lastUpdateChapter="
				+ lastUpdateChapter + ", lastUpdateChapterUrl=" + lastUpdateChapterUrl + ", lastUpdateTime="
				+ lastUpdateTime + ", status=" + status + ", firstLetter=" + firstLetter + ", platformId=" + platformId
				+ ", addTime=" + addTime + "]";
	}
}
