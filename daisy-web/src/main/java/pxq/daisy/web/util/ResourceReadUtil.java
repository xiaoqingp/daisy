package pxq.daisy.web.util;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.core.io.ClassPathResource;

/**
 * 资源文件读取工具
 * @author peixiaoqing
 * @date 2022-02-07
 * @since 1.0.0
 */
public class ResourceReadUtil {

	public static synchronized String read(String path) {
		ClassPathResource classPathResource = new ClassPathResource(path);
		InputStream inputStream = null;
		StringBuilder strb = new StringBuilder();

		try {
			inputStream = classPathResource.getInputStream();
			char[] tempchars = new char[1024];
			int charread = 0;
			InputStreamReader reader = new InputStreamReader(inputStream);
			while ((charread = reader.read(tempchars)) != -1) {
				if ((charread == tempchars.length)) {
					strb.append(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						strb.append(tempchars[i]);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return strb.toString();
	}
}
