package Http;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TestHttpGet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ReadByGet().start();

	}

}

class ReadByGet extends Thread {
	@Override
	public void run() {
		try {

			URL url = new URL("https://poll.kuaidi100.com/poll/query.do");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);

			String line;
			StringBuilder builder = new StringBuilder();
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}

			br.close();
			isr.close();
			is.close();
			System.out.println(builder.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
