package Others;

import java.io.*;
import java.util.zip.*;

public class Compress {
	// 缓冲
	static byte[] buffer = new byte[2048];

	private static void zip(File[] files, String baseFolder, ZipOutputStream zos) throws Exception {
		// 输入
		FileInputStream fis = null;
		// 条目
		ZipEntry entry = null;
		// 数目
		int count = 0;
		for (File file : files) {
			if (file.isDirectory()) {
				// 递归
				zip(file.listFiles(), file.getName() + File.separator, zos);
				continue;
			}
			entry = new ZipEntry(baseFolder + file.getName());
			// 加入
			zos.putNextEntry(entry);
			fis = new FileInputStream(file);
			// 读取
			while ((count = fis.read(buffer, 0, buffer.length)) != -1)
				// 写入
				zos.write(buffer, 0, count);
		}
	}

	public static void main(String[] args) throws Exception {
		// 来源
		File inputDir = new File("F://backup/back");
		// 目标
		FileOutputStream fos = new FileOutputStream("F:\\109.zip");
		// 过滤
		ZipOutputStream zos = new ZipOutputStream(fos);
		// 压缩
		zip(inputDir.listFiles(), "", zos);
		zos.close();
		// 关闭
	}

}
