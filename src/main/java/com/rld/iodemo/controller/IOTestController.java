package com.rld.iodemo.controller;

import com.rld.iodemo.pojo.Employee;
import com.rld.iodemo.utils.IOUtil;
import com.rld.iodemo.utils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class IOTestController {

    /**
     * 将指定文本以内容以指定格式输出到系统src=>main=>resources=>output.txt中
     * @return
     */
    @RequestMapping("/output/run")
    @ResponseBody
    public R transFile(){
        IOUtil.run();
        return R.ok().put("data","文件已按指定格式输出，请检阅工程目录下指定的文件 src=>main=>resources=>output.txt");
    }

    /**
     * 跳转到水果查询页
     * @param req
     * @return
     */
    @GetMapping(value = "/index")
    public ModelAndView test(HttpServletRequest req) {
        // UserEntity userEntity = getCurrentUser(req);
        Employee employee = new Employee();
        employee.setFirstName("liu");
        employee.setLastName("fei");

        ModelAndView mv = new ModelAndView();
        mv.addObject("employee", employee);
        mv.setViewName("/fruit/show.html");
        return mv;
    }

    /**
     * 响应Ajax请求，将符和条件的水果返回
     * @param fruitName
     * @return
     */
    @GetMapping(value = "/getFruit")
    @ResponseBody
    public R test(String fruitName) {
        List<String> fruitList=new ArrayList<>();
        fruitList.add("红富士苹果");
        fruitList.add("冰糖心苹果");
        fruitList.add("山东特产苹果");
        fruitList.add("陕西苹果");
        fruitList.add("江西蜜桔");
        fruitList.add("淮南大桔");
        fruitList.add("北京大桃");
        List<String> resultList=new ArrayList<>();
        Iterator<String> iterator = fruitList.iterator();
        while (iterator.hasNext()) {
            String fruit = iterator.next();
            if (fruit.contains(fruitName)) {
                resultList.add(fruit+"-");
            }
        }
        System.out.println(resultList);
        return R.ok().put("data",resultList);
    }

}
