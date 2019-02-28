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
 * @version 1.0 ����ͬ��ͬ�����µĸ������Ƶ���Ӧ���ļ��б����ѹ������
 */

public class FileToZip {

	private FileToZip() {
	}

	/**
	 * �������sourceFilePathĿ¼�µ�Դ�ļ��������fileName���Ƶ�zip�ļ�������ŵ�zipFilePath·����
	 * 
	 * @param sourceFilePath
	 *            :��ѹ�����ļ�·��
	 * @param zipFilePath
	 *            :ѹ������·��
	 * @param fileName
	 *            :ѹ�����ļ�������
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
			System.out.println("��ѹ�����ļ�Ŀ¼��" + sourceFilePath + "������.");
		} else {
			try {
				File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
				if (zipFile.exists()) {
					System.out.println(zipFilePath + "Ŀ¼�´�������Ϊ:" + fileName + ".zip" + "����ļ�.");
				} else {
					File[] sourceFiles = sourceFile.listFiles();
					if (null == sourceFiles || sourceFiles.length < 1) {
						System.out.println("��ѹ�����ļ�Ŀ¼��" + sourceFilePath + "���治�����ļ�������ѹ��.");
					} else {
						fos = new FileOutputStream(zipFile);
						zos = new ZipOutputStream(new BufferedOutputStream(fos));
						byte[] bufs = new byte[1024 * 10];
						for (int i = 0; i < sourceFiles.length; i++) {
							// ����ZIPʵ�壬����ӽ�ѹ����
							ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
							zos.putNextEntry(zipEntry);
							// ��ȡ��ѹ�����ļ���д��ѹ������
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
				// �ر���
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
	 * ���Ƶ����ļ�*
	 * 
	 * @param oldPath
	 *            String ԭ�ļ�·�� �磺c:/fqf.txt*
	 * @param newPath
	 *            String ���ƺ�·�� �磺f:/fqf.txt*
	 * @return boolean
	 */

	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;

			// ��ȡ�ļ���֮ǰ�ľ���·��
			String subPath = newPath.substring(0, newPath.lastIndexOf("/"));
			// System.out.println(subPath);
			(new File(subPath)).mkdirs(); // ����ļ��в����� �������ļ���

			File oldfile = new File(oldPath);

			if (oldfile.exists()) { // �ļ�����ʱ
				FileInputStream inStream = new FileInputStream(oldPath); // ����ԭ�ļ�
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // �ֽ��� �ļ���С
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
		// �ļ����Ʋ���
		copyFile("F://test2/css/΢��ͼƬ_20180104170221.jpg", "F://basedir/session/test1/css/a.jpg");

//		String sourceFilePath = "D:\\TestFile";
//		String zipFilePath = "D:\\tmp";
//		String fileName = "12700153file";
		
		
		boolean flag = FileToZip.fileToZip("F://basedir/session/", "F://backup/", "123");
		if (flag) {
			System.out.println("�ļ�����ɹ�!");
		} else {
			System.out.println("�ļ����ʧ��!");
		}
	}
}
