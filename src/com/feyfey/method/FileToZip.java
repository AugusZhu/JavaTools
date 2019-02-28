package com.feyfey.method;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * @author Xianfei Zhu
 * @version 1.0 将不同合同号向下的附件复制到对应的文件夹饼打包压缩下载
 */

public class FileToZip {

	private FileToZip() {
	}

	/**
	 * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
	 * 
	 * @param sourceFilePath
	 *            :待压缩的文件路径
	 * @param zipFilePath
	 *            :压缩后存放路径
	 * @param fileName
	 *            :压缩后文件的名称
	 * @return
	 */
	public static boolean fileToZip(String sourceFilePath, String zipFilePath, String fileName) {
		boolean flag = false;
		File sourceFile = new File(sourceFilePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;

		if (sourceFile.exists() == false) {
			System.out.println("待压缩的文件目录：" + sourceFilePath + "不存在.");
		} else {
			try {
				File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
				if (zipFile.exists()) {
					System.out.println(zipFilePath + "目录下存在名字为:" + fileName + ".zip" + "打包文件.");
				} else {
					File[] sourceFiles = sourceFile.listFiles();
					if (null == sourceFiles || sourceFiles.length < 1) {
						System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
					} else {
						fos = new FileOutputStream(zipFile);
						zos = new ZipOutputStream(new BufferedOutputStream(fos));
						byte[] bufs = new byte[1024 * 10];
						for (int i = 0; i < sourceFiles.length; i++) {
							// 创建ZIP实体，并添加进压缩包
							ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
							zos.putNextEntry(zipEntry);
							// 读取待压缩的文件并写进压缩包里
							fis = new FileInputStream(sourceFiles[i]);
							bis = new BufferedInputStream(fis, 1024 * 10);
							int read = 0;
							while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
								zos.write(bufs, 0, read);
							}
						}
						flag = true;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				// 关闭流
				try {
					if (null != bis)
						bis.close();
					if (null != zos)
						zos.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
		return flag;
	}

	/***
	 * 复制单个文件*
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt*
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt*
	 * @return boolean
	 */

	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;

			// 获取文件名之前的绝对路径
			String subPath = newPath.substring(0, newPath.lastIndexOf("/"));
			// System.out.println(subPath);
			(new File(subPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹

			File oldfile = new File(oldPath);

			if (oldfile.exists()) { // 文件存在时
				FileInputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					// System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("copy single file error!");
			e.printStackTrace();

		}

	}

	public static void main(String[] args) {
		// 文件复制测试
		copyFile("F://test2/css/微信图片_20180104170221.jpg", "F://basedir/session/test1/css/a.jpg");

//		String sourceFilePath = "D:\\TestFile";
//		String zipFilePath = "D:\\tmp";
//		String fileName = "12700153file";
		
		
		boolean flag = FileToZip.fileToZip("F://basedir/session/", "F://backup/", "123");
		if (flag) {
			System.out.println("文件打包成功!");
		} else {
			System.out.println("文件打包失败!");
		}
	}
}
