package pxq.daisy.test.web;

import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Controller;

import cn.hutool.core.date.format.FastDateFormat;
import pxq.daisy.test.mode.request.Num2TimeRequest;
import pxq.daisy.test.mode.response.Num2TimeResponse;
import pxq.daisy.web.annotation.GetMapping;
import pxq.daisy.web.annotation.PostMapping;
import pxq.daisy.web.core.WebContext;

/**
 * 小工具
 *
 * @author peixiaoqing
 * @date 2022-02-01
 */
@Controller
public class ToolsController {
	// 10位时间戳最大值，通过9999-12-31 23:59:59计算获得
	private static final long MAX_TIMESTAMP = 253402271999L;

	/**
	 * 时间戳工具
	 *
	 * @return
	 */
	@GetMapping("/tools/timestamp")
	public String timestamp() {
		WebContext.getResponse().addAttribute("timestamp", System.currentTimeMillis());
		return "timestamp";
	}

	/**
	 * 时间戳转换为时间
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping("/tools/timestamp")
	public Num2TimeResponse timestamp(Num2TimeRequest request) {
		Num2TimeResponse response = new Num2TimeResponse();
		long timestamp = 0;
		if (request.getTimestampType() == 1 && request.getTimestampValue() < MAX_TIMESTAMP) {
			// 秒
			timestamp = request.getTimestampValue() * 1000;
		} else {
			// 毫秒
			timestamp = request.getTimestampValue();
		}
		response.setTime(FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp)));
		return response;
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
