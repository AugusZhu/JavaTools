package ZipCompress;

import java.io.*;
import java.util.zip.*;

public class ZipCompress {

	private static void zip(File[] files, String baseFolder, ZipOutputStream zos) {
		try {

			//System.out.println("start");

			// buffer
			byte[] buffer = new byte[2048];
			// input
			FileInputStream fis = null;
			// count
			ZipEntry entry = null;
			// num
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

		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("end");
	}

	public static String compress(String filePath, String baseFolder) {
		try {
			//System.out.println("compress start");
			// 来源
			File inputDir = new File(filePath);
			// 目标
			FileOutputStream fos = new FileOutputStream(baseFolder);
			// 过滤
			ZipOutputStream zos = new ZipOutputStream(fos);
			// 压缩
			zip(inputDir.listFiles(), "", zos);
			// 关闭
			zos.close();
		} catch (Exception e) {
			System.out.println("压缩文件夹失败");
			e.printStackTrace();
		}
		//System.out.println("compress end");
		return "success";

	}

}
