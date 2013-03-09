import java.io.*;
import java.net.*;
import java.util.*;

public class ClientMessage implements Serializable
{
	public ArrayList<String> online = null;
	public String message;
	public String nickname;
}