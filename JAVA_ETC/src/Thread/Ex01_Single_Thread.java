package Thread;

/*
프로그램 >> 실행 >> 프로세스 >> 최소 하나의 Thread(최소 하나의 stack 메모리)
>>JVM > OS > 목적 설정> stack 메모리 > main 함수가 최초 올라가서 실행

지금까지 > 싱글 프로세스 > 싱글 쓰레드 >> main 함수 싫행 >> 하나의 stack

멀티쓰레드 >> memory >> stack 2개 또는 그이상 만드는 행위
 */

public class Ex01_Single_Thread {
	public static void main(String[] arg) {
		System.out.println("나 메인 일꾼이야");
		
		worker();
		worker2();
	}
	static void worker() {
		System.out.println("나 1번 일꾼이야");
	}
	
	static void worker2() {
		System.out.println("나 2번 일꾼이야");
	}
}
