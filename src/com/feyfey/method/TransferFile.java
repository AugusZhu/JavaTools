package com.feyfey.method;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class TransferFile {

	/**
	 * @param newPath
	 *            �����ļ���
	 * @param oldPath
	 *            ԭ�ļ���
	 */
	public static void transferFolderFile(String newPath, String oldPath) {
		try {
			// File file = new File(newPath);
			(new File(oldPath)).mkdirs(); // ����ļ��в����� �������ļ���
			File file = new File(newPath);

			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("�ļ����ǿյ�!");
					return;
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {
							System.out.println("�ļ���:" + file2.getAbsolutePath());
							transferFolderFile(file2.getAbsolutePath(), oldPath);
						} else {
							System.out.println("�ļ�:" + file2.getAbsolutePath());
							transferFile(newPath, oldPath, file2.getName());
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

	public static void transferFile(String newPath, String oldPath, String fileName) {
		try {
			File afile = new File(newPath + "\\" + fileName);
			if (afile.renameTo(new File(oldPath + "\\" + fileName))) {
				System.out.println("File is moved successful!");
			} else {
				System.out.println("File is failed to move!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// �ļ�ת�Ʋ���
		transferFolderFile("F:\\test1", "F:\\test2");
	}

}
