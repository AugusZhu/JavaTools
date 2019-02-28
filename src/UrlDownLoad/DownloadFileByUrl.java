package UrlDownLoad;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Xianfei Zhu
 */

public class DownloadFileByUrl {
	/**
	 * @param urlPath
	 *            下载路径
	 * @param downloadDir
	 *            下载存放目录
	 */
	public static void downloadFile(String urlPath, String downloadDir, String fileName) {
		File file = null;
		try {
			// 统一资源
			URL url = new URL(urlPath);
			// 连接类的父类，抽象类
			URLConnection urlConnection = url.openConnection();
			// http的连接类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			// 设定请求的方法，默认是GET
			httpURLConnection.setRequestMethod("GET");
			// 设置字符编码
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
			httpURLConnection.connect();

			// 文件大小
			int fileLength = httpURLConnection.getContentLength();
			// 文件
			// System.out.println("file length---->" + fileLength);

			URLConnection con = url.openConnection();
			BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

			String path = downloadDir + File.separatorChar + fileName;
			file = new File(path);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			OutputStream out = new FileOutputStream(file);

			int size = 0;
			int len = 0;
			byte[] buf = new byte[1024];
			while ((size = bin.read(buf)) != -1) {
				len += size;
				out.write(buf, 0, size);
			}
			bin.close();
			out.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param path
	 *            原文件夹
	 * @param historyPath
	 *            备份文件夹
	 * @param checkDays
	 *            超过多少天把文件转存到备份文件夹
	 */
	public static void transferFolderFile(String path, String historyPath, int checkDays) {
		try {
			File file = new File(path);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("文件夹是空的!");
					return;
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {
							System.out.println("文件夹:" + file2.getAbsolutePath());
							transferFolderFile(file2.getAbsolutePath(), historyPath, checkDays);
						} else {
							System.out.println("文件:" + file2.getAbsolutePath());
							Calendar cal = Calendar.getInstance();
							long time = file2.lastModified();
							cal.setTimeInMillis(time);
							Date currentTime = new Date();
							int days = (int) ((currentTime.getTime() - cal.getTime().getTime()) / (1000 * 3600 * 24));
							System.out.println("文件:" + file2.getAbsolutePath() + "距离现在" + days + "天");
							if (checkDays <= days) {
								transferFile(path, historyPath, file2.getName());
							}
						}
					}
				}
			} else {
				System.out.println("文件不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void transferFile(String path, String historyPath, String fileName) {
		try {
			File afile = new File(path + "\\" + fileName);
			if (afile.renameTo(new File(historyPath + "\\" + fileName))) {
				System.out.println("File is moved successful!");
			} else {
				System.out.println("File is failed to move!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		// 下载文件测试
		  downloadFile(
		  "https://einvoicelink.51fapiao.cn:8181/FPFX/actions/f1710172890211f5e19a6bb7c13bea1b8a112e",
		  "F:\\test1", "test1.pdf");
		 
		// 文件转移测试
		 transferFolderFile("F:\\test1", "F:\\test2", 0);

	
	}

}
