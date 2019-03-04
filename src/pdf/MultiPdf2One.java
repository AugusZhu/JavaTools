package pdf;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.util.PDFMergerUtility;

public class MultiPdf2One {

	/**
	 * 
	 * @param folder
	 * @return
	 * @throws IOException
	 */
	private static String[] getFiles(String folder) throws IOException {
		File _folder = new File(folder);
		String[] filesInFolder;

		if (_folder.isDirectory()) {
			filesInFolder = _folder.list();
			return filesInFolder;
		} else {
			throw new IOException("Path is not a directory");
		}
	}

	public static void main(String[] args) throws Exception {
		PDFMergerUtility mergePdf = new PDFMergerUtility();
		// String folder = System.getProperty("user.dir")+"\\pdfs";
		String folder = "C:\\Users\\Xianfei Zhu\\Desktop\\test";
		System.out.println(folder);
		String destinationFileName = "ONE.pdf";
		String[] filesInFolder = getFiles(folder);
		for (int i = 0; i < filesInFolder.length; i++)
			mergePdf.addSource(folder + File.separator + filesInFolder[i]);
		mergePdf.setDestinationFileName(destinationFileName);
		mergePdf.mergeDocuments();

	}
}
