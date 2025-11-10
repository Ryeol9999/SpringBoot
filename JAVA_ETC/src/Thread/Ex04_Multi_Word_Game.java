package Thread;

import javax.swing.JOptionPane;

/*
문제 ..출력
시간 ... 시간 출력
2개의 작업 동시에 (경합) ... 실행

단일 Thread 불가능
멀티 Thread 통해서 (여러개의 stack 메모리 확보)
 */
class WordTime extends Thread{
	
	@Override
	public void run() {
		for(int i =10 ; i > 0 ; i--) {
			if(Ex04_Multi_Word_Game.inputCheck) return ; //run 함수 탈출하기 //thread 종료하기
			try {
				System.out.println("남은시간 : " + i);
				Thread.sleep(1000); //남은시간 출력하고 (규칙) 휴게실가서 1초 쉬고 다시와		} catch (Exception e) {
				// TODO: handle exception
			}catch (Exception e) {
			    e.printStackTrace();
			}
		}
	}
	

	
}

class WordInputThread extends Thread {
	
	@Override
	public void run() {
		String dan ="2";
		String inputData = JOptionPane.showInputDialog(dan + "단 값을 입력하세요");
		if(inputData != null && !inputData.equals("")) {
			Ex04_Multi_Word_Game.inputCheck =true;
		}
		System.out.println("입력값 : " + inputData);
	}
}
public class Ex04_Multi_Word_Game {
	
	static boolean inputCheck = false;
	public static void main(String[] args) {
		WordTime timer = new WordTime();
		timer.start();
		
		WordInputThread wordInputThread = new WordInputThread();
		wordInputThread.start();
		
		//상태
		// 각 위성과 지구와의 거리
		// 목성거리 + 토성거리 + 금성거리 > 총거리의합(3개의 종료후에 그 다음에 종료 )>>
		
		// main 함수가 word thread , time thread 끝난 다음에 종료
		try {
			timer.join();
			wordInputThread.join();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Main END");
		
	}
}
