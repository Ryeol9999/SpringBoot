package Event;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//태생 : cs 프로그램 (메모장, 그림판, 이클립스 같은 툴)
//1.awt (OS 가지고 있는 자원)
//2. swing (순수한 자바로 컴포넌트)

class MyFrame extends Frame{
	public MyFrame(String title) {
		super(title);
	}
	
}

class BtnClickHandler implements ActionListener{
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("나 클릭.....");
	}
}
public class Ex14_awt_Frame {
	public static void main(String[] args) {
		MyFrame my = new MyFrame("login");
		my.setSize(350, 300);
		my.setVisible(true);
		my.setLayout(new FlowLayout());

		Button btn = new Button("one button");
		Button btn2 = new Button("two button");
		Button btn3 = new Button("three button");
		BtnClickHandler handler =new BtnClickHandler();
		btn.addActionListener(handler);
		
		//익명객체
		btn2.addActionListener(new ActionListener() { // 익명타입 원칙ㅇ
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("인터페이스 익명 객체 구현");
			}
		});
		my.add(btn);
		my.add(btn2);
		my.add(btn3);
		
	}
	}
