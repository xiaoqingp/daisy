package pxq.daisy.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.springframework.core.io.ClassPathResource;

import cn.hutool.core.io.FileUtil;

public class ResourceTest {
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		ClassPathResource classPathResource = new ClassPathResource("static/bootstrap/css/bootstrap.min.css");
		InputStream inputStream = classPathResource.getInputStream();

		StringBuilder strb = new StringBuilder();
		char[] tempchars = new char[1024];
		int charread = 0;
		InputStreamReader reader = new InputStreamReader(inputStream);
        // 读入多个字符到字符数组中，charread为一次读取字符数
		int num = 0;
        while ((charread = reader.read(tempchars)) != -1) {
            if ((charread == tempchars.length)) {
                strb.append(tempchars);
                num++;
            } else {
                for (int i = 0; i < charread; i++) {
                	strb.append(tempchars[i]);
                }
            }
        }
        
        System.out.println(num);
        long end = System.currentTimeMillis();
        System.out.println("cost="+(end-start)+" ms");
        FileUtil.writeString(strb.toString(), new File("D:\\bootstrap.txt"), "UTF-8");
	}
}
