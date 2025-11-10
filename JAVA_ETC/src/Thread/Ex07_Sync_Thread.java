package Thread;


/*
멀티 스레드 환경(공유자원)

synchronized (동기화)

Collection (Vetor , ArrayList) 비교

Vetor 동기화 보장 ...> 멀디 스레드 환경 > 자원 보호 > 화장실 (lock) > 성능 저하....
ArrayList 동기화 보장하지 않아요 > 성능 보장 > 자원 보호 보장....하지 않아요

한강
화장실(1개): 공유자원
여러명의 사람들 (10명) : 10개의 스레드 가...... >> 동시에 화장실 접근

반대 (성능)
한강 비빕밥 축제 : 여러 사람이 동시 접근 (빨리 ....) Lock  처리 안되요

LOCK 처리 (객체단위, 함수단위)
*/

class Wroom{
	synchronized void openDoor(String name) {
		System.out.println(name +" 님 화장실 입장 ^_^");
		for(int i=0; i <100 ; i++) {
			System.out.println(name +" 사용 중 " + i);
			if (i==10) {
				System.out.println(name +" 님 끙 @@");
				
			}
		}
		System.out.println("시원하시죠 ^^");
	}
}

class User extends Thread{
	Wroom wr;
	String who;
	
	User(String name,Wroom wr){
		this.who =name;
		this.wr= wr;
	}
	@Override
    public void run() {
        wr.openDoor(who); // ⭐ Thread가 실제로 하는 작업
    }
}

public class Ex07_Sync_Thread {
	public static void main(String[] args) {
		Wroom w = new Wroom();

		User kim =new User("김씨", w);
		User lee =new User("이씨", w);
		User park =new User("박씨", w);
	
	//급 똥이

		kim.start();
		lee.start();
		park.start();
	
	
	}
	

}
