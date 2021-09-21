package com.javapandeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.javapandeng.base.BaseController;
import com.javapandeng.po.Car;
import com.javapandeng.po.Item;
import com.javapandeng.po.Sc;
import com.javapandeng.service.CarService;
import com.javapandeng.service.ItemService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController extends BaseController {
    @Autowired
    private CarService carService;
    @Autowired
    private ItemService itemService;

    @RequestMapping("/exAdd")
    @ResponseBody
    public String exAdd(Car car,HttpServletRequest request){
        JSONObject js = new JSONObject();
        Object attribute = request.getSession().getAttribute("userId");
        if(attribute==null){
            js.put("res",0);
            return js.toJSONString();
        }
        //保存到购物车
        Integer userId = Integer.valueOf(attribute.toString());
        car.setUserId(userId);
        Item item = itemService.load(car.getItemId());
        String price = item.getPrice();
        Double valueOf = Double.valueOf(price);
        car.setPrice(valueOf);
        if(item.getZk()!=null){
            valueOf = valueOf*item.getZk()/10;
            BigDecimal bg = new BigDecimal(valueOf).setScale(2, RoundingMode.UP);
            car.setPrice(bg.doubleValue());
            valueOf = bg.doubleValue();
        }
        Integer num = car.getNum();
        Double t = valueOf*num;

        BigDecimal bg = new BigDecimal(t).setScale(2, RoundingMode.UP);
        double doubleValue = bg.doubleValue();
        car.setTotal(doubleValue+"");
        carService.insert(car);
        js.put("res",1);
        return js.toJSONString();
    }


    @RequestMapping("findBySql")
    public String findBySql(Model model,HttpServletRequest request){
        Object attribute = request.getSession().getAttribute("userId");
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        String sql = "select * from car where user_id = "+Integer.valueOf(attribute.toString());
        List<Car> cars = carService.listBySqlReturnEntity(sql);
        model.addAttribute("list",cars);

        return "car/car";
    }
}
