package com.javapandeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.javapandeng.base.BaseController;
import com.javapandeng.po.*;
import com.javapandeng.service.ItemCategoryService;
import com.javapandeng.service.ItemService;
import com.javapandeng.service.ManageService;
import com.javapandeng.service.UserService;
import com.javapandeng.utils.Pager;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.lucene.util.CollectionUtil;
import org.aspectj.weaver.ast.ITestVisitor;
import org.springframework.asm.SpringAsmInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
    @Autowired
    ManageService manageService;
    @Autowired
    private ItemCategoryService itemCategoryService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public String login(){
        return "login/mLogin";
    }
    @RequestMapping("toLogin")
    public String toLogin(Manage manage, HttpServletRequest request){
        Manage byEntity = manageService.getByEntity(manage);
        if (byEntity==null){

            return "redirect:/login/exit";
        }
        request.getSession().setAttribute("massage",manage);
        return "login/mIndex";
    }

/**
 * 管理退出
 */
    @RequestMapping("/exit")
    public String exit(HttpServletRequest request) {
        request.getSession().setAttribute("massage",null);
        return "login/mLogin";
    }

    @RequestMapping("/uIndex")
    public String uIndex(Item item, Model model) {
        String sql ="select * from item_category where pid is null and isDelete = 0";
        List<ItemCategory> fatherlist = itemCategoryService.listBySqlReturnEntity(sql);
        List<CategoryDto> list = new ArrayList<>();
        if(!CollectionUtils.isEmpty(fatherlist)){
            for(ItemCategory ic:fatherlist){
                CategoryDto dto = new CategoryDto();
                String sql2 = "select * from item_category  where isDelete = 0 and pid = "+ic.getId();
                List<ItemCategory> childrenlist = itemCategoryService.listBySqlReturnEntity(sql2);
                dto.setFather(ic);
                dto.setChildrens(childrenlist);
                list.add(dto);
            }
            model.addAttribute("lbs",list);
        }
//        折扣商品
        String sql3 ="select * from item where zk is not null order by zk desc limit 0,10";
        List<Item> zks = itemService.listBySqlReturnEntity(sql3);
        model.addAttribute("zks",zks);
//        热销商品

        String sql4 = "select * from item where isDelete = 0 order by gmNum desc limit 0,10";
        List<Item> rxs = itemService.listBySqlReturnEntity(sql4);
        model.addAttribute("rxs",rxs);
        return "login/uIndex";
    }
    @RequestMapping("res")
    public String res(){
        return "login/res";
    }

    @RequestMapping("toRes")
    public String toRes(Model model, User user){
        userService.insert(user);
        return "login/res";
    }
    @RequestMapping("uLogin")
    public String uLogin(){
        return "login/uLogin";
    }

    @RequestMapping("utoLogin")
    public String utoLogin(User user, Model model,HttpServletRequest request){
        User byEntity = userService.getByEntity(user);
        if(!isEmpty(byEntity)){
            request.getSession().setAttribute("role",2);
            request.getSession().setAttribute("username",byEntity.getUserName());
            request.getSession().setAttribute("userId",byEntity.getId());
            return "redirect:/login/uIndex";
        }else {
            String sql = "select * from user where phone = "+user.getUserName()+ " and password = "+ user.getPassWord();
            User bySqlRerturnEntity = userService.getBySqlReturnEntity(sql);
            if(!isEmpty(bySqlRerturnEntity)){
                request.getSession().setAttribute("role",2);
                request.getSession().setAttribute("username",bySqlRerturnEntity.getUserName());
                request.getSession().setAttribute("userId",bySqlRerturnEntity.getId());
                return "redirect:/login/uIndex";
            }
        }
        return "login/uLogin";
    }
    @RequestMapping("uTui")
    public  String uTui(HttpSession session){
         session.invalidate();
        return "redirect:/login/uLogin";
    }

    @RequestMapping("/pass")
    public  String pass(HttpServletRequest request,Model model){
        Object attribute = request.getSession().getAttribute("userId");
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        Integer userId = Integer.valueOf(attribute.toString()) ;
        User load = userService.load(userId);
        model.addAttribute("obj",load);
        return "login/pass";
    }

    @RequestMapping("/upass")
    @ResponseBody
    public String upass(String password,HttpServletRequest request){
        Object attribute = request.getSession().getAttribute("userId");
        JSONObject js = new JSONObject();
        if(attribute==null){
            js.put("res",0);
            return js.toString();
        }
        Integer userId = Integer.valueOf(attribute.toString());
        User load = userService.load(userId);
        load.setPassWord(password);
        userService.updateById(load);
        js.put("res",1);
        return js.toString();
    }
}
