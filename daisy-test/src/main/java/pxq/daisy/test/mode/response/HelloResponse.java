/**
 * 文件名：HelloResponse<br/>
 * CopyRight (c) 2019-2030：<br/>
 * 创建人：peixiaoqing
 * 日期：2022/1/8
 * 修改人：
 * 日期：
 * 描述：
 * 版本号：2.5.4
 */
package pxq.daisy.test.mode.response;

/**
 * @author peixiaoqing
 * @date 2022/1/8
 */
public class HelloResponse {
    private String name;
    private Integer age;
    private String msg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
