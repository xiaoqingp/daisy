
# 题外话
      06年高考随大流填报了比较热门的专业：计算机科学与技术。奈何偏科严重上不了重点大学，一本也是吊车尾。感谢祖国大学扩招让我选择了一个不错的二本院校。上了大学最初的梦想是写一个游戏外挂，可惜技术有限。毕业有前前后后搬了10年的代码，期间用过Struts1、jsp、Spring MCV、Spring boot，到现在的Spring Cloud微服务，从事的工作一直和web相关。最初的梦想大概是一辈子也实现不了。
	  daisy是我女儿的英文名，为了纪念她的到来，也为总结自己生活和工作。这个项目的名字就以她命名。
---
# 为什么使用netty
	java开发如果要使用tcp技术，第一个想到的框架是netty。因为它性能好、代码优美、自定义私有协议简单。很多耳熟能详的框架和服务提供商都底层都使用netty框架，比如：dubbo、极光推送。使用Spring boot后web容器还是用tomcat感觉有点不伦不类，所以找了neety官网看了下netty也提供了http协议的支持，所以果断选择netty。

# daisy-web
	daisy-web是使用netty作为web容器的MVC框架，可以把http请求url后和body中的参数转换为Controller Method中的入参。

1. 支持的封装参数类型有：java.lang.Short、java.lang.Integer、java.lang.Float、java.lang.Double、java.lang.Long、java.lang.String
2. 支持的基本参数类型有：short、int、float、double、long、string，如果请参数中没有默认会给一个0.
3. 支持的数组类型有：java.util.List
4. 支持：get、put、post、delete四种请求方式

## 使用

``` java
<dependency>
		<groupId>pxq.daisy</groupId>
		<artifactId>daisy-web</artifactId>
		<version>1.0.0</version>
</dependency>
```
启动类，模仿Sping boot。启动时：

1. 第一步使用AnnotationConfigApplicationContext扫描上下文，把业务组件加载到spring 上下文中。
2. 第二步从上行文中找到Controller注解标注的Controller类，并把它们使用GetMapping、PutMapping、PostMapping、DeleteMapping的method使用JDK Proxy生成一个基于SimpleController接口的代理类。这个代理类会在netty的业务handler中被调用

``` javascript
package pxq.daisy.test;

import pxq.daisy.web.HttpServer;

/**
 * @author peixiaoqing
 * @date 2021/12/30
 */
public class Test {
    public static void main(String[] args) {
        HttpServer.run(Test.class, args);
    }
}
```

