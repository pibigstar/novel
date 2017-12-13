package com.lei.controller;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lei.model.JSONModel;
import com.lei.service.INovelService;
import com.lei.spider.entity.Novel;

@Controller
public class NovelController {
	@Resource
	private INovelService novelService;
	
	
	/**
	 * 小说搜索{数据库}
	 * @param keyWord
	 * @return
	 */
	@RequestMapping(value = "/searchDB.do" , method = RequestMethod.POST)
	@ResponseBody
	public JSONModel getNovelByKeyWord(String keyWord) {
		JSONModel j = new JSONModel();
		j.setSuccess(true);
		j.setObject(novelService.getNovelsByKeyWord(keyWord));
		return j;
	}
	
	/**
	 * 小说搜索
	 * @param keyWord
	 * @return
	 */
	@RequestMapping(value = "/search.do" , method = RequestMethod.POST)
	@ResponseBody
	public JSONModel getNovel(String keyWord,String plat) {
		JSONModel j = new JSONModel();
		j.setSuccess(true);
		List<Novel> novels = novelService.getNovels(keyWord,plat);
		
		j.setObject(novels);
		return j;
	}
	
	
	/**
	 * 跳转到章节列表页
	 * @param url
	 * @return
	 */
	@RequestMapping(value = "/chapterList.do", method = RequestMethod.GET)
	public ModelAndView getChapterList(String url) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("chapterList");
		mv.getModel().put("chapters", novelService.getChapterList(url));
		mv.getModel().put("baseUrl", url);
		return mv;
	}
	
	/**
	 * 获得章节的详细内容
	 * @param url
	 * @param baseUrl
	 * @return
	 */
	@RequestMapping(value = "/chapterDetail.do",method = RequestMethod.GET)
	public ModelAndView getChapterDetail(String url,String baseUrl) {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("chapterDetail");
		mv.getModel().put("chapterDetail",novelService.getChapterDetail(url));
		mv.getModel().put("baseUrl", baseUrl);
		return mv;
	}
	
}
