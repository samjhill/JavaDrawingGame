import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Sender {
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		Socket s = new Socket("localhost",10000);
		ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
		
		
		
		
		int x = 0;
		
		while(true) {
			TransferObject to = new TransferObject();
			to.y = true;
			to.x = x;
			oos.writeObject(to);
			oos.flush();
			x+=1;
			Thread.sleep(1000);
		}
	}
}
