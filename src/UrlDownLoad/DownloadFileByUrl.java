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
	 *            ����·��
	 * @param downloadDir
	 *            ���ش��Ŀ¼
	 */
	public static void downloadFile(String urlPath, String downloadDir, String fileName) {
		File file = null;
		try {
			// ͳһ��Դ
			URL url = new URL(urlPath);
			// ������ĸ��࣬������
			URLConnection urlConnection = url.openConnection();
			// http��������
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			// �趨����ķ�����Ĭ����GET
			httpURLConnection.setRequestMethod("GET");
			// �����ַ�����
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			// �򿪵��� URL ���õ���Դ��ͨ�����ӣ������δ�������������ӣ���
			httpURLConnection.connect();

			// �ļ���С
			int fileLength = httpURLConnection.getContentLength();
			// �ļ�
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
	 *            ԭ�ļ���
	 * @param historyPath
	 *            �����ļ���
	 * @param checkDays
	 *            ������������ļ�ת�浽�����ļ���
	 */
	public static void transferFolderFile(String path, String historyPath, int checkDays) {
		try {
			File file = new File(path);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("�ļ����ǿյ�!");
					return;
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {
							System.out.println("�ļ���:" + file2.getAbsolutePath());
							transferFolderFile(file2.getAbsolutePath(), historyPath, checkDays);
						} else {
							System.out.println("�ļ�:" + file2.getAbsolutePath());
							Calendar cal = Calendar.getInstance();
							long time = file2.lastModified();
							cal.setTimeInMillis(time);
							Date currentTime = new Date();
							int days = (int) ((currentTime.getTime() - cal.getTime().getTime()) / (1000 * 3600 * 24));
							System.out.println("�ļ�:" + file2.getAbsolutePath() + "��������" + days + "��");
							if (checkDays <= days) {
								transferFile(path, historyPath, file2.getName());
							}
						}
					}
				}
			} else {
				System.out.println("�ļ�������!");
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

		// �����ļ�����
		  downloadFile(
		  "https://einvoicelink.51fapiao.cn:8181/FPFX/actions/f1710172890211f5e19a6bb7c13bea1b8a112e",
		  "F:\\test1", "test1.pdf");
		 
		// �ļ�ת�Ʋ���
		 transferFolderFile("F:\\test1", "F:\\test2", 0);

	
	}

}
