package com.javapandeng.controller;

import com.javapandeng.base.BaseController;
import com.javapandeng.po.ItemCategory;
import com.javapandeng.service.ItemCategoryService;
import com.javapandeng.utils.Pager;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;

@Controller
@RequestMapping("/itemCategory")
public class ItemCategoryController extends BaseController {
    @Autowired
    private ItemCategoryService itemCategoryService;

    @RequestMapping("/findBySql")
   public String findBySql(Model model,ItemCategory itemCategory){
       String sql = "select * from item_category where  isDelete = 0 and pid is null order by id";
        Pager<ItemCategory> bySqlRerturnEntity = itemCategoryService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",bySqlRerturnEntity);
        model.addAttribute("obj",itemCategory);
        return "itemCategory/itemCategory";
   }
    @RequestMapping("/add")
    public String add(){

        return "itemCategory/add";
    }

    @RequestMapping("/exAdd")
    public String exAdd(ItemCategory itemCategory){
        itemCategory.setIsDelete(0);
        itemCategoryService.insert(itemCategory);

        return "redirect:/itemCategory/findBySql";
    }

    @RequestMapping("/update")
    public String update(int id,Model model){
        ItemCategory obj = itemCategoryService.load(id);
        model.addAttribute("obj",obj);
        return "/itemCategory/update";
    }
    @RequestMapping("/exUpdate")
    public String exUpdate(ItemCategory itemCategory){
        itemCategoryService.updateById(itemCategory);
        return "redirect:/itemCategory/findBySql";
    }


    @RequestMapping("/delete")
    public String delete(int id){
        itemCategoryService.deleteById(id);
        return  "redirect:/itemCategory/findBySql";
    }


    @RequestMapping("/findBySql2")
    public String findBySql2(ItemCategory itemCategory, Model model){
        String sql = "select * from item_category where isDelete = 0 and pid = "+itemCategory.getPid()+" order by id";
        Pager<ItemCategory> pagers = itemCategoryService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",itemCategory);
        return  "/itemCategory/itemCategory2";
    }



    @RequestMapping("/add2")
    public String add2(int pid,Model model){
        System.out.println("pid="+pid);
        model.addAttribute("pid",pid);
        return  "itemCategory/add2";
    }

    @RequestMapping("/exAdd2")
    public String exAdd2(ItemCategory itemCategory,Model model){
        itemCategory.setIsDelete(0);
        itemCategoryService.insert(itemCategory);
        return  "redirect:/itemCategory/findBySql2?pid="+itemCategory.getPid();
    }
    @RequestMapping("/delete2")
    public String delete(int id,int pid){
       itemCategoryService.deleteById(id);
        return  "redirect:/itemCategory/findBySql2?pid="+pid;
    }


    @RequestMapping("/update2")
    public String update2(int id ,Model model){
        ItemCategory load = itemCategoryService.load(id);
        model.addAttribute("obj",load);
        return "itemCategory/update2";
    }


    @RequestMapping("/exUpdate2")
    public String exUpdate2(ItemCategory itemCategory,Model model){
        itemCategoryService.updateById(itemCategory);
        return  "redirect:/itemCategory/findBySql2?pid="+itemCategory.getPid();
    }
}
