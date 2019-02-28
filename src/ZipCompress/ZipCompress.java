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
					// �ݹ�
					zip(file.listFiles(), file.getName() + File.separator, zos);
					continue;
				}
				entry = new ZipEntry(baseFolder + file.getName());
				// ����
				zos.putNextEntry(entry);
				fis = new FileInputStream(file);
				// ��ȡ
				while ((count = fis.read(buffer, 0, buffer.length)) != -1)
					// д��
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
			// ��Դ
			File inputDir = new File(filePath);
			// Ŀ��
			FileOutputStream fos = new FileOutputStream(baseFolder);
			// ����
			ZipOutputStream zos = new ZipOutputStream(fos);
			// ѹ��
			zip(inputDir.listFiles(), "", zos);
			// �ر�
			zos.close();
		} catch (Exception e) {
			System.out.println("ѹ���ļ���ʧ��");
			e.printStackTrace();
		}
		//System.out.println("compress end");
		return "success";

	}

}
