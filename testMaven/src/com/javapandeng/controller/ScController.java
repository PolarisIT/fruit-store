package com.javapandeng.controller;

import com.javapandeng.base.BaseController;
import com.javapandeng.po.Item;
import com.javapandeng.po.Sc;
import com.javapandeng.service.ItemService;
import com.javapandeng.service.ScService;
import com.javapandeng.utils.Pager;
import com.sun.org.apache.bcel.internal.generic.MONITORENTER;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.aspectj.weaver.ast.ITestVisitor;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sc")
public class ScController extends BaseController {

    @Autowired
    private ScService scService;
    @Autowired
    private ItemService itemService;

    @RequestMapping("/exAdd")
    public String exAdd(int itemId, Model model, HttpServletRequest request){
        System.out.println("itemId="+itemId);
        Item load = itemService.load(itemId);
        load.setScNum(load.getScNum()+1);
        itemService.updateById(load);
        Sc sc = new Sc();
        sc.setItemId(itemId);
        if(!isEmpty( request.getSession().getAttribute("userId"))){
            int userId = (int) request.getSession().getAttribute("userId");
            sc.setUserId(userId);
            scService.insert(sc);
            return "redirect:/sc/findBySql";
        }else{
            return "redirect:/login/uLogin";
        }
    }
@RequestMapping("/findBySql")
    public String findBySql(Model model,HttpServletRequest request){
        Object attribute = request.getSession().getAttribute("userId");
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        Integer userId = Integer.valueOf(attribute.toString());
        String sql = "select * from sc where user_id = "+userId +" order by id desc" ;
        Pager<Sc> bySqlRerturnEntity = scService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",bySqlRerturnEntity);
        return "sc/my";
    }
    @RequestMapping("/delete")
    public String delete(int id,Model model,HttpServletRequest request){
        Object attribute = request.getSession().getAttribute("userId");
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        scService.deleteById(id);

        return "redirect:/sc/findBySql";
    }
}
