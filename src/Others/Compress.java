package Others;

import java.io.*;
import java.util.zip.*;

public class Compress {
	// ����
	static byte[] buffer = new byte[2048];

	private static void zip(File[] files, String baseFolder, ZipOutputStream zos) throws Exception {
		// ����
		FileInputStream fis = null;
		// ��Ŀ
		ZipEntry entry = null;
		// ��Ŀ
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
	}

	public static void main(String[] args) throws Exception {
		// ��Դ
		File inputDir = new File("F://backup/back");
		// Ŀ��
		FileOutputStream fos = new FileOutputStream("F:\\109.zip");
		// ����
		ZipOutputStream zos = new ZipOutputStream(fos);
		// ѹ��
		zip(inputDir.listFiles(), "", zos);
		zos.close();
		// �ر�
	}

}
