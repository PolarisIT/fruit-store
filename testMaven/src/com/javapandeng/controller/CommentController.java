package com.javapandeng.controller;

import com.javapandeng.base.BaseController;
import com.javapandeng.po.Comment;
import com.javapandeng.service.CommentService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/exAdd")
    public String exAdd(Comment comment, HttpServletRequest request){
        Object attribute = request.getSession().getAttribute("userId");
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        comment.setUserId(Integer.valueOf(attribute.toString()));
        commentService.insert(comment);


        return "redirect:/itemOrder/my";
    }

}
