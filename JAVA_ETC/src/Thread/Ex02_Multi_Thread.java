package Thread;


/*
 Thread : 프로세스에서 하나의 최소 실행단위 (흐름) >> method >>  실행되는 공간 stack
 
 결과 : stack 여러개 실행해서 >> 동시에 실행 (실행 가능 한다) >> cpu 점유할 수 있는 상태로 만든다.
 
 쓰레드 생성 방법
 1. Thread 클래스 상속 >> class Task extends Thread >> run 함수
 2. Runnable 인터페이스 구현 >> class Task implements Runnable >> run 추상함수 강제 구현: Thread 아니고요
 	Thread thread =new Thread(new Task());
 ex) class Task extends Car 를 이미 상속 extend Thread 안됨 >> 인터페이스 RUN 강제구현
 3. 
 
*/

class Task_1 extends Thread{
	
	
	//why > run() 추상함수가 아닐까
	@Override
	public void run() {  // run 함수가 Thread 의 메인함수 역할
		for(int i= 0; i < 1000; i++) {
			System.out.println("Task_1 " + i +this.getName());
		}
		System.out.println("Task_1 run() 함수 END");
	}
	
}

class Task_2 implements Runnable{
	
	@Override
	public void run() {
		for(int i= 0; i < 1000; i++) {
			System.out.println("Task_2 " + i);
		}
		System.out.println("Task_2 run() 함수 END");
	}
}

public class Ex02_Multi_Thread {

	public static void main(String[] args) {
			//main thread
		
		Task_1 th =new Task_1();
		th.start();
		//POINT
		//start();
		//1. memory call back 생성하고 그리고 Thread가 가지고있는 run() 함수 스택에 올려두고 생명끝
		
		Task_2 ta = new Task_2(); //runnable 구현한 그냥 객체
		Thread th2 =new Thread(ta);
		th2.start();
		//POINT
		//start();
		//1. memory call back 생성하고 그리고 Thread가 가지고있는 run() 함수 스택에 올려두고 생명끝
		
		Thread th3 = new Thread(new Runnable() {  // 한단계 발전 > 함수형 인터페이스 > 람다 표현식
			
			@Override
			public void run() {
				for(int i= 0; i < 1000; i++) {
					System.out.println("th3 " + i);
				}
				System.out.println("th3 run() 함수 END");
			}
		});
		th3.start();		
		for(int i= 0; i < 1000; i++) {
			System.out.println("Main " + i);
		}
		System.out.println("Main run() 함수 END");
	}		
}
	



