package pxq.daisy.test.web;

import org.springframework.stereotype.Controller;
import pxq.daisy.test.mode.request.StudentRequest;
import pxq.daisy.test.mode.response.HelloResponse;
import pxq.daisy.web.annotation.DeleteMapping;
import pxq.daisy.web.annotation.GetMapping;
import pxq.daisy.web.annotation.PostMapping;
import pxq.daisy.web.annotation.PutMapping;
import pxq.daisy.web.core.DaisyResponse;
import pxq.daisy.web.core.WebContext;

import java.util.List;

/**
 * 测试类
 *
 * @author peixiaoqing
 * @date 2021/12/30
 */
@Controller
public class HelloController {

    @GetMapping("/test/hello")
    public String hello() {
        return "hello";
    }

    /**
     * 测试返回html页面
     *
     * @return
     */
    @GetMapping("/test/hello1")
    public String init1(String name, int age) {
        System.out.println("我是小拳拳:" + name + " 年龄：" + age);
        DaisyResponse response = WebContext.getResponse();

        response.addAttribute("name", name);
        response.addAttribute("age", age);
        return "index";
    }

    // 测试参数类型
    @GetMapping("/test/hello2")
    public String init2(String name, int age1, Integer age2, short s1, Short s2, long l1, Long l2) {
        System.out.println("init2:" + name + " 年龄：" + age1);

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

    // form表单提交，使用方法参数接收
    @PostMapping("/test/post1")
    public String post1(String name, int age) {
        System.out.println("post1");
        System.out.println("name=" + name + " age=" + age);
        return "index";
    }

    // form表单提交，对象接收
    @PostMapping("/test/post2")
    public String post2(StudentRequest request) {
        System.out.println("post2");
        System.out.println("name=" + request.getName() + " age=" + request.getAge());
        return "index";
    }

    // json字符串提交 对象接收
    @PostMapping("/test/post3")
    public String post3(StudentRequest request) {
        System.out.println("post3");
        System.out.println("name=" + request.getName() + " age=" + request.getAge());
        return "index";
    }

    // json字符串提交, 参数接收
    @PostMapping("/test/post4")
    public String post4(StudentRequest request, String name, boolean agg) {
        System.out.println("post4");
        System.out.println("name=" + request.getName() + " age=" + request.getAge());
        return "index";
    }

    // form表单提交
    @PutMapping("/test/put1")
    public String put1(String name, int age) {
        System.out.println("put1");
        System.out.println("name=" + name + " age=" + age);
        return "index";
    }

    // form表单提交
    @PutMapping("/test/put2")
    public String put1(StudentRequest request) {
        System.out.println("put2");
        System.out.println("name=" + request.getName() + " age=" + request.getAge());
        return "index";
    }

    // json提交
    @PutMapping("/test/put3")
    public String put3(String name, int age) {
        System.out.println("put3");
        System.out.println("name=" + name + " age=" + age);
        return "index";
    }

    // json提交
    @PutMapping("/test/put4")
    public String put4(StudentRequest request) {
        System.out.println("put4");
        System.out.println("name=" + request.getName() + " age=" + request.getAge());
        return "index";
    }

    // url方式提交
    @DeleteMapping("/test/del1")
    public String del1(Long studentId) {
        System.out.println("del1");
        System.out.println("删除 studentId=" + studentId);
        return "index";
    }

    @DeleteMapping("/test/del2")
    public String del1(List<Long> studentIds) {
        System.out.println("del2");
        for (Long studentId : studentIds) {
            System.out.println("删除 studentId=" + studentId);
        }
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
