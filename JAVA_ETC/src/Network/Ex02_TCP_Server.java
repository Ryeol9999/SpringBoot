package Network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/* 
 TCP서버
 IP:192.168.2.18
 PORT:9999
 
 TCP 클라이언트
 서버 IP 와 서버 PORT
 */
public class Ex02_TCP_Server {
	
	public static void main(String[] args) throws IOException{
		ServerSocket serverSocket = new ServerSocket(9999);
		System.out.println("접속 대기중....");
		Socket socket = serverSocket.accept();
		System.out.println("연결완료");
	
		//연결
		//서버와 클라이언트(socket -socket 연결) : read, write
		//socket 객체 (input, output stream 내장)
		
		OutputStream out = socket.getOutputStream();
		DataOutputStream dos = new DataOutputStream(out);
		dos.writeUTF("문자데이터 Byte 통신 입니다");
		
		System.out.println("서버종료");
		
		dos.close();
		out.close();
		socket.close();
		serverSocket.close();
	}

}
