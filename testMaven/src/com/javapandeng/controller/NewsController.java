package com.javapandeng.controller;

import com.javapandeng.base.BaseController;
import com.javapandeng.po.News;
import com.javapandeng.service.NewsService;
import com.javapandeng.utils.Pager;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.ws.RequestWrapper;
import java.util.Date;

@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {
    @Autowired
    private NewsService newsService;

    @RequestMapping("findBySql")
    public String findBySql(News news, Model model){
        String sql = "select * from news where 1 = 1 ";
        if(!isEmpty(news.getName())){
            sql+=" and name like '%"+news.getName()+"%' ";
        }
        sql+=" order by id desc";
        Pager<News> bySqlRerturnEntity = newsService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",bySqlRerturnEntity);
        model.addAttribute("obj",news);
        return "news/news";
    }
    @RequestMapping("/add")
    public String add( ){
        return "news/add";
    }
    @RequestMapping("/exAdd")
    public String exAdd(News news){

        news.setAddTime(new Date());
        newsService.insert(news);
        return "redirect:/news/findBySql";
    }

    @RequestMapping("/update")
    public String update(News news,Model model){
        News load = newsService.load(news);
        model.addAttribute("obj",load);
        return "news/update";
    }


    @RequestMapping("/exUpdate")
    public String exUpdate(News news,Model model){
        newsService.updateById(news);
        return "redirect:/news/findBySql";
    }
    @RequestMapping("/delete")
    public String delete(int id){
        newsService.deleteById(id);
        return "redirect:/news/findBySql";
    }


    @RequestMapping("/list")
    public String list(Model model){
        Pager<News> byEntity = newsService.findByEntity(new News());
        model.addAttribute("pagers",byEntity);
        return "news/list";
    }


    @RequestMapping("/view")
    public String view(Model model,int id){
        News load = newsService.load(id);
        model.addAttribute("obj",load);
        return "news/view";
    }
}
