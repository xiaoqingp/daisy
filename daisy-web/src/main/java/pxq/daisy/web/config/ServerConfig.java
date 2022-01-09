/**
 * 文件名：ServerConfig<br/>
 * CopyRight (c) 2019-2030：<br/>
 * 创建人：peixiaoqing
 * 日期：2021/12/29
 * 修改人：
 * 日期：
 * 描述：配置文件
 * 版本号：2.5.4
 */
package pxq.daisy.web.config;

import org.aeonbits.owner.Config;

/**
 * 配置文件
 *
 * @author peixiaoqing
 * @date 2021/12/29
 */
@Config.Sources("classpath:daisy.properties")
public interface ServerConfig extends Config {
    @DefaultValue("dev")
    String env();

    // 服务器端口
    @Key("server.${env}.port")
    @DefaultValue("8080")
    Integer port();

    // 服务器日志等级
    @Key("server.${env}.log.level")
    @DefaultValue("INFO")
    String level();
}
