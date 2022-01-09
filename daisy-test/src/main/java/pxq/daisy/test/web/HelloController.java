package pxq.daisy.test.web;

import org.springframework.stereotype.Controller;
import pxq.daisy.test.mode.response.HelloResponse;
import pxq.daisy.web.annotation.GetMapping;

/**
 * @author peixiaoqing
 * @date 2021/12/30
 */
@Controller
public class HelloController {

    @GetMapping("/test/hello1")
    public String init(String name) {
        System.out.println("我是小拳拳:" + name);
        return "index";
    }

    @GetMapping("/test/hello2")
    public HelloResponse init2(String name) {
        HelloResponse response = new HelloResponse();
        response.setName(name);
        response.setAge(11);
        response.setMsg("欢迎使用");
        return response;
    }
}
