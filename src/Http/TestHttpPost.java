package Http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TestHttpPost {

	public static void main(String[] args) {
		new ReadByGet().start();
	}

}

class ReadByPost extends Thread {
	@Override
	public void run() {
		try {
			URL url = new URL("https://poll.kuaidi100.com/poll/query.do");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.addRequestProperty("encoding", "UTF-8");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");

			OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			BufferedWriter bW = new BufferedWriter(osw);

			bW.write("customer=34");
			bW.flush();

			InputStream is = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);

			String line;
			StringBuilder builder = new StringBuilder();
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}

			bW.close();
			osw.close();
			os.close();
			br.close();
			isr.close();
			is.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
