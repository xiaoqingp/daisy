package pxq.daisy.test.web;

import org.springframework.stereotype.Controller;
import pxq.daisy.web.annotation.GetMapping;

/**
 * 小工具
 *
 * @author peixiaoqing
 * @date 2022-02-01
 */
@Controller
public class ToolsController {
    /**
     * 时间戳工具
     *
     * @return
     */
    @GetMapping("/tools/timestamp")
    public String timestamp() {
        return "timestamp";
    }

    /**
     * base64图片工具
     *
     * @return
     */
    @GetMapping("/tools/base64Image")
    public String base64Image() {
        return "base64Image";
    }

    /**
     * base64编码、反编码工具
     *
     * @return
     */
    @GetMapping("/tools/base64EncodeDecode")
    public String base64EncodeDecode() {
        return "base64EncodeDecode";
    }

    /**
     * url编码、反编码工具
     *
     * @return
     */
    @GetMapping("/tools/urlEncodeDecode")
    public String urlEncodeDecode() {
        return "urlEncodeDecode";
    }

    /**
     * md5工具
     *
     * @return
     */
    @GetMapping("/tools/md5")
    public String md5() {
        return "md5";
    }

    /**
     * json字符串工具
     *
     * @return
     */
    @GetMapping("/tools/jsonFormat")
    public String jsonFormat() {
        return "jsonFormat";
    }

}
