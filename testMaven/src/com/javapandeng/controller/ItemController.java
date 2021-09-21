package com.javapandeng.controller;

import com.javapandeng.base.BaseController;
import com.javapandeng.po.Item;
import com.javapandeng.po.ItemCategory;
import com.javapandeng.service.ItemCategoryService;
import com.javapandeng.service.ItemService;
import com.javapandeng.utils.Pager;
import com.javapandeng.utils.SystemContext;
import com.javapandeng.utils.UUIDUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Condition;

@Controller
@RequestMapping("/item")
public class ItemController extends BaseController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemCategoryService itemCategoryService;

    @RequestMapping("findBySql")
    public String findBySql(Model model, Item item){
        String sql = "select * from item where 1 = 1 and IsDelete = 0 ";
        if(!isEmpty(item.getName())){
            sql+="and name like '%" +item.getName()+"%' ";
        }
        sql+=" order by id";
        Pager<Item> bySqlRerturnEntity = itemService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",bySqlRerturnEntity);
        model.addAttribute("obj",item);
        return "item/item";
    }
    @RequestMapping("add")
    public String add(Model model){
        String sql = "select * from item_category where isDelete = 0 and pid is not null order by id";
        List<ItemCategory> bySqlRerturnEntity = itemCategoryService.listBySqlReturnEntity(sql);
        model.addAttribute("types",bySqlRerturnEntity);
        return "item/add";
    }
//商品添加
    @RequestMapping("/exAdd")
    public String exAdd(Item item, Model model, @RequestParam("file") CommonsMultipartFile[] files, HttpServletRequest request) throws IOException {
        if(files.length>0){
            for(int s=0;s<files.length;s++){
                String n = UUIDUtils.create();
                String path = SystemContext.getRealPath()+"\\resource\\ueditor\\upload\\"+n+files[s].getOriginalFilename();
                File newfile = new File(path);
                files[s].transferTo(newfile);
                if(s==0){
                    item.setUrl1(request.getContextPath()+"\\resource\\ueditor\\upload\\"+n+files[s].getOriginalFilename());
                }
                if(s==1){
                    item.setUrl2(request.getContextPath()+"\\resource\\ueditor\\upload\\"+n+files[s].getOriginalFilename());
                }
                if(s==2){
                    item.setUrl3(request.getContextPath()+"\\resource\\ueditor\\upload\\"+n+files[s].getOriginalFilename());
                }
                if(s==3){
                    item.setUrl4(request.getContextPath()+"\\resource\\ueditor\\upload\\"+n+files[s].getOriginalFilename());
                }
                if(s==4){
                    item.setUrl5(request.getContextPath()+"\\resource\\ueditor\\upload\\"+n+files[s].getOriginalFilename());
                }
            }
            item.setGmNum(0);
            item.setIsDelete(0);
            item.setScNum(0);
            ItemCategory byId = itemCategoryService.getById(item.getCategoryIdTwo());
            item.setCategoryIdOne(byId.getPid());
            itemService.insert(item);
        }
       return "redirect:/item/findBySql";
    }
    @RequestMapping("/update")
    public String update(int id,Model model){
        Item load = itemService.load(id);
        model.addAttribute("obj",load);
        String sql = "select * from item_category where isDelete = 0 and pid is not null order by id";
        List<ItemCategory> bySqlRerturnEntity = itemCategoryService.listBySqlReturnEntity(sql);
        model.addAttribute("types",bySqlRerturnEntity);
        return "item/update";
    }

    @RequestMapping("/exUpdate")
    public String exUpdate(Item item,Model model){
        itemService.updateById(item);
        return "redirect:/item/findBySql";
    }


    @RequestMapping("/delete")
    public String delete(int id,Model model){
        String sql = "update item set isDelete = 1 where id = "+id;
        itemService.updateBysql(sql);
        return "redirect:/item/findBySql";
    }


    @RequestMapping("shopList")
    public String shopList(Item item , Model model, String condition){
        String sql = "select * from item where isDelete = 0";
        if(!isEmpty(item.getCategoryIdTwo())){
            sql+= " and category_id_two = "+item.getCategoryIdTwo();

        }
        if(!isEmpty(condition)){
            sql+= " and name like '%"+condition+"%'";
        }

        if(!isEmpty(item.getPrice())){
//            price在数据库中是varchar类型，+0就是int型
            sql+= " order by (price+0) desc";
        }
        if(!isEmpty(item.getGmNum())){
            sql+= " order by gmNum desc";
        }
        if(isEmpty(item.getGmNum())&&isEmpty(item.getPrice())){
            sql+= " order by id desc";
        }
        Pager<Item> bySqlRerturnEntity = itemService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",bySqlRerturnEntity);
        model.addAttribute("condition",condition);
        model.addAttribute("obj",item);
        return "item/shoplist";
    }

    @RequestMapping("/view")
    public String view(int id,Model model){
        Item load = itemService.load(id);
        model.addAttribute("obj",load);
        return "item/view";
    }



}
