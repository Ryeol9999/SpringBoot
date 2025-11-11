package Network;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Ex02_TCP_Client {
	public static void main(String[] args) throws UnknownHostException , IOException{
		
		Socket socket = new Socket("192.168.2.57", 9999);
		System.out.println("서버와 연결 되었습니다");
		
		//서버가 보낸 메세지 받기
		InputStream in = socket.getInputStream();
		DataInputStream dis = new DataInputStream(in);
		
		String servermsg= dis.readUTF();
		System.out.println("서버에서 보낸 메세지 : " + servermsg);
		
		dis.close();
		in.close();
		socket.close();
	}

}
