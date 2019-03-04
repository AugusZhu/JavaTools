package Others;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩算法类 实现文件压缩，文件夹压缩，以及文件和文件夹的混合压缩
 * 
 * @author Xianfei Zhu
 * 
 */
public class CompactAlgorithm {

	/**
	 * 完成的结果文件--输出的压缩文件
	 */
	File targetFile;

	public CompactAlgorithm() {
	}

	public CompactAlgorithm(File target) {
		targetFile = target;
		if (targetFile.exists())
			targetFile.delete();
	}

	/**
	 * 压缩文件
	 * 
	 * @param srcfile
	 */
	public void zipFiles(File srcfile) {

		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(targetFile));

			if (srcfile.isFile()) {
				zipFile(srcfile, out, "");
			} else {
				File[] list = srcfile.listFiles();
				for (int i = 0; i < list.length; i++) {
					compress(list[i], out, "");
				}
			}

			System.out.println("Zip Successful");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 压缩文件夹里的文件 起初不知道是文件还是文件夹--- 统一调用该方法
	 * 
	 * @param file
	 * @param out
	 * @param basedir
	 */
	private void compress(File file, ZipOutputStream out, String basedir) {
		/* 判断是目录还是文件 */
		if (file.isDirectory()) {
			this.zipDirectory(file, out, basedir);
		} else {
			this.zipFile(file, out, basedir);
		}
	}

	/**
	 * 压缩单个文件
	 * 
	 * @param srcfile
	 */
	public void zipFile(File srcfile, ZipOutputStream out, String basedir) {
		if (!srcfile.exists())
			return;

		byte[] buf = new byte[1024];
		FileInputStream in = null;

		try {
			int len;
			in = new FileInputStream(srcfile);
			out.putNextEntry(new ZipEntry(basedir + srcfile.getName()));

			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.closeEntry();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 压缩文件夹
	 * 
	 * @param dir
	 * @param out
	 * @param basedir
	 */
	public void zipDirectory(File dir, ZipOutputStream out, String basedir) {
		if (!dir.exists())
			return;

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			/* 递归 */
			compress(files[i], out, basedir + dir.getName() + "/");
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

	public static boolean copyFile(String oldPath, String newPath) {
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
		return true;
	}

	// 测试
	public static void main(String[] args) {

		// 文件复制测试
		copyFile("F://test2/css/微信图片_20180104170221.jpg", "F://basedir/session/test1/a.jpg");

		File zipPath = new File("F://basedir/session");

		new CompactAlgorithm(new File("F:/backup/TEST.zip")).zipFiles(zipPath);
	}

}
