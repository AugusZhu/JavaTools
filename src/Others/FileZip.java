package Others;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * @author Xianfei Zhu
 */
public class FileZip {
	static final int BUFFER = 8192;

	private File zipFile;

	public FileZip(String pathName) {
		zipFile = new File(pathName);
	}

	public void compress(String... pathName) {
		ZipOutputStream out = null;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
			out = new ZipOutputStream(cos);
			String basedir = "";
			for (int i = 0; i < pathName.length; i++) {
				compress(new File(pathName[i]), out, basedir);
			}
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void compress(String srcPathName) {
		File file = new File(srcPathName);
		if (!file.exists())
			throw new RuntimeException(srcPathName + "不存在！");
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
			ZipOutputStream out = new ZipOutputStream(cos);
			String basedir = "";
			compress(file, out, basedir);
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void compress(File file, ZipOutputStream out, String basedir) {
		/* 判断是目录还是文件 */
		if (file.isDirectory()) {
			System.out.println("压缩：" + basedir + file.getName());
			this.compressDirectory(file, out, basedir);
		} else {
			System.out.println("压缩：" + basedir + file.getName());
			this.compressFile(file, out, basedir);
		}
	}

	/** 压缩一个目录 */
	private void compressDirectory(File dir, ZipOutputStream out, String basedir) {
		if (!dir.exists())
			return;

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			/* 递归 */
			compress(files[i], out, basedir + dir.getName() + "/");
		}
	}

	/** 压缩一个文件 */
	private void compressFile(File file, ZipOutputStream out, String basedir) {
		if (!file.exists()) {
			return;
		}
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			ZipEntry entry = new ZipEntry(basedir + file.getName());
			out.putNextEntry(entry);
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = bis.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			bis.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
			
			//获取文件名之前的绝对路径
			String subPath = newPath.substring(0, newPath.lastIndexOf("/"));
			//System.out.println(subPath);
			(new File(subPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹

			File oldfile = new File(oldPath);

			if (oldfile.exists()) { // 文件存在时
				FileInputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					//System.out.println(bytesum);
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

		// 压缩文件目录以及压缩文件名
		 FileZip zc = new FileZip("F:/TEST.zip");
		 zc.compress("F://basedir/session/");
	}
}
