package pxq.daisy.web.core;

import static io.netty.buffer.Unpooled.copiedBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import pxq.daisy.web.util.ResourceReadUtil;

/**
 * 静态资源处理器
 * 
 * @author peixiaoqing
 * @date 2022-02-05
 *
 */
public class StaticResourceController implements SimpleController {

	@Override
	public HttpResponse doService(FullHttpRequest request) {
		String uri = request.uri();
		int len = uri.length();

		String content = ResourceReadUtil.read(uri.substring(1,len));

		ByteBuf buf = copiedBuffer(content, CharsetUtil.UTF_8);
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);

		String contentType = "text/css";
		if (uri.endsWith(".js")) {
			contentType = "application/x-javascript";
		}
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
		response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());

		return response;
	}

}
