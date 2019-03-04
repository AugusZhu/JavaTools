package Others;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ѹ���㷨�� ʵ���ļ�ѹ�����ļ���ѹ�����Լ��ļ����ļ��еĻ��ѹ��
 * 
 * @author Xianfei Zhu
 * 
 */
public class CompactAlgorithm {

	/**
	 * ��ɵĽ���ļ�--�����ѹ���ļ�
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
	 * ѹ���ļ�
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
	 * ѹ���ļ�������ļ� �����֪�����ļ������ļ���--- ͳһ���ø÷���
	 * 
	 * @param file
	 * @param out
	 * @param basedir
	 */
	private void compress(File file, ZipOutputStream out, String basedir) {
		/* �ж���Ŀ¼�����ļ� */
		if (file.isDirectory()) {
			this.zipDirectory(file, out, basedir);
		} else {
			this.zipFile(file, out, basedir);
		}
	}

	/**
	 * ѹ�������ļ�
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
	 * ѹ���ļ���
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
			/* �ݹ� */
			compress(files[i], out, basedir + dir.getName() + "/");
		}
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

	public static boolean copyFile(String oldPath, String newPath) {
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
		return true;
	}

	// ����
	public static void main(String[] args) {

		// �ļ����Ʋ���
		copyFile("F://test2/css/΢��ͼƬ_20180104170221.jpg", "F://basedir/session/test1/a.jpg");

		File zipPath = new File("F://basedir/session");

		new CompactAlgorithm(new File("F:/backup/TEST.zip")).zipFiles(zipPath);
	}

}
