package com.javapandeng.controller;

import com.javapandeng.base.BaseController;
import com.javapandeng.po.User;
import com.javapandeng.service.UserService;
import com.javapandeng.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Controller
@RequestMapping("/user")

public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @RequestMapping("/findBySql")
    public String findBySql(Model model,User user){
        String sql = "select * from user where 1 = 1";
        if(!isEmpty(user.getUserName())){
            sql+=" and userName like '%" +user.getUserName()+"%'";
        }
        sql+= " order by id";
        Pager<User> bySqlRerturnEntity = userService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",bySqlRerturnEntity);

        return "user/user";
    }


    @RequestMapping("/view")
    public String view(Model model, HttpServletRequest request){
        Object attribute = request.getSession().getAttribute("userId");
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        Integer userId = Integer.valueOf(attribute.toString());
        User load = userService.load(userId);
        model.addAttribute("obj",load);
        return "user/view";
    }

    @RequestMapping("/exUpdate")
    public String exUpdate(User user,Model model,HttpServletRequest request){
        Object attribute = request.getSession().getAttribute("userId");
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        user.setId(Integer.valueOf(attribute.toString()));
        userService.updateById(user);
        return "redirect:/user/view";
    }
}
