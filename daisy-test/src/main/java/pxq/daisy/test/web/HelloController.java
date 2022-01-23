package pxq.daisy.test.web;

import org.springframework.stereotype.Controller;
import pxq.daisy.test.mode.response.HelloResponse;
import pxq.daisy.web.annotation.GetMapping;

import java.util.List;

/**
 * 测试类
 *
 * @author peixiaoqing
 * @date 2021/12/30
 */
@Controller
public class HelloController {

    /**
     * 测试返回html页面
     *
     * @param name
     * @return
     */
    @GetMapping("/test/hello1")
    public String init1(String name, int age) {
        System.out.println("我是小拳拳:" + name + " 年龄：" + age);
        return "index";
    }

    // 测试参数类型
    @GetMapping("/test/hello2")
    public String init2(String name, int age1, Integer age2, short s1, Short s2, long l1, Long l2) {
        System.out.println("我是小拳拳:" + name + " 年龄：" + age1);

        return "index";
    }

    // 测试数组类型
    @GetMapping("/test/hello3")
    public String init3(List<String> arr1, List<Integer> arr2) {
        if (null != arr1) {
            System.out.println("打印数组arr1");
            arr1.stream().forEach(System.out::println);
        }
        if (null != arr2) {
            System.out.println("打印数组arr2");
            arr2.stream().forEach(System.out::println);
        }
        return "index";
    }

    @GetMapping("/test/hello4")
    public String int4() {
        return "index";
    }

    /**
     * 测试返回json字符串
     *
     * @param name
     * @return
     */
    @GetMapping("/test/hello10")
    public HelloResponse init3(String name) {
        HelloResponse response = new HelloResponse();
        response.setName(name);
        response.setAge(11);
        response.setMsg("欢迎使用");
        return response;
    }
}
