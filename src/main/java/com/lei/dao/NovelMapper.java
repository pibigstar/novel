package com.lei.dao;

import java.util.List;

import com.lei.spider.entity.Novel;

public interface NovelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Novel record);

    int insertSelective(Novel record);

    Novel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Novel record);

    int updateByPrimaryKey(Novel record);
    
    int batchInsert(List<Novel> novels);
}