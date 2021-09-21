package com.javapandeng.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.javapandeng.base.BaseController;
import com.javapandeng.po.OrderDetail;
import com.javapandeng.service.OrderDetailService;
import com.javapandeng.utils.Pager;
import org.codehaus.jackson.map.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orderDetail")
public class OrderDetailController extends BaseController {
    @Autowired
    private OrderDetailService orderDetailService;

    @RequestMapping("/ulist")
    public String ulist(OrderDetail orderDetail, Model model){
        String sql ="select * from order_detail where order_id = "+orderDetail.getOrderId();
        Pager<OrderDetail> bySqlRerturnEntity = orderDetailService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",bySqlRerturnEntity);
        model.addAttribute("obj",orderDetail);
        return "orderDetail/ulist";
    }



}
