package application.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 关闭流的方法
 * @author 赵政
 *
 */
public class CloseUtil {
	public static void closeAll(Closeable... io) {
		for(Closeable temp: io) {
			if(null != temp) {
				try {
					temp.close();
				} catch (IOException e) {
					
				}
			}
		}
	}
}
