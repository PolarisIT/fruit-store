package com.javapandeng.controller;

import com.javapandeng.base.BaseController;
import com.javapandeng.po.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController  {
    @RequestMapping("/ajax")
    @ResponseBody
    public List<Test> ajax(){
        List<Test> testList = new ArrayList<Test>();
        Test t1 = new Test("lisi",23);
        Test t2 = new Test("zhangsna",73);
        Test t3 = new Test("wangerma",23);
        Test t4 = new Test("adf",25);
        testList.add(t1);
        testList.add(t2);
        testList.add(t3);
        testList.add(t4);

        return testList;
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(String username){
        System.out.println("username="+username);
        if("admin".equals(username)){
            return "ok";
        }else {
            return "fair" ;
        }

    }


}
