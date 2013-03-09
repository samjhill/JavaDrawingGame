import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Receiver 
{
	public static void main(String[] args) throws IOException, ClassNotFoundException 
	{
		ServerSocket ss = new ServerSocket(10000);
		
		while(true) 
		{
			Socket s = ss.accept();
			
			final ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			
			new Thread() {public void run() 
			{
				while(true) 
				{
					TransferObject to;
					try 
					{
						to = (TransferObject) ois.readObject();
						System.out.println(to.x+" "+to.y);
					} 
					
					catch (IOException e) 
					{
						e.printStackTrace();
					} 
					
					catch (ClassNotFoundException e) 
					{
						e.printStackTrace();
					}				
				}
			}}.start();
		}
	}
}
