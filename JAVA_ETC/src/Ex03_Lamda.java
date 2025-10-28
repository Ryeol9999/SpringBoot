/*
람다식
-자바에서 함수적 프로그래밍 지원 기법
-코드의 간결화 및 병렬처리에 강함 (Collection API 성능 효과적 개선) > Stream 

Java 는 객체지향프로그램으로서 모두 함수는 클래스/인터페이스 내부에만 존재 가능 (클래스 내부의 함수 = 메서드)


람다식 이해를 위한 기본 용어

함수(function) : 기본 동작을 정의

메서드(method) : 클래스 또는 인터페이스 내부에 정의된 함수

**함수형 인터페이스 (functional interface) : 내부에 단 1개의 추상메서드만 존재하는 인터페이스
**함수형 프로그래밍을 사용하기 위한 방법
----------------------------------------------------------------------
본래 의미의 함수적 프로그래밍에서의 함수 사용 (자주 사용하는 기능 구현)


void add() {}   ->  add() 함수 사용  -> 

객체지향에서는

class A {           -> A a = new A();   -> a.add()
    void add(){
     
    }
    
------------------------------------------------------------------------
자바는 새로운 함수 문법을 정의한 것이 아니라 이미 있는 인터페이스를 빌려서 람다식 표현    

모든 인터페이스의 구현 메서드는 람다식으로 변환 할 수 있을까 NO
**함수적 인터페이스의 메서드만 람다식 표현 가능하다

1 단계

interface a {
   void abc();
}

-->

class B implements A {
    public void abc(){
       ....
    }
}

-->

A a = new B();    a.abc();
---------------------------------------------------

2단계

익명클래스 
A a = new A(){
     void abc(){
     
     }
}

a.abc();

----------------------------------------------------

3단계
자바의 함수적 프로그래밍(람다식) 함수 사용

interface A {
    void abc(); 추상함수 구현부 없음
}

A a = () -> { } 구현부를 람다식으로 표현

-------------------------------------------------------
실습
interface A {  입력 x , 리턴 x 
    void method1();
}
interface B {  입력 0 , 리턴 x
    void method2(int a);
}
interface C {  입력 x , 리턴 0
    int method3();
}
interface D {  입력 0 , 리턴 0
    double method1(int a , double b);
}
--------------------------------------------------------
A a2 = () -> {System.out.println();};
A a3 = () -> System.out.println();

B b2 = (int a) -> {System.out.println("");};
B b3 = (a) -> {System.out.println("");};
B b4 = (a) -> System.out.println("");
B b5 =  a  -> System.out.println("");


C c2 = () -> {return 4;};
C c3 = () -> 4


D d2 = (int a, double b) -> { return a+b;};
D d3 = (a,b) -> {return a+b;};
D d4 = (a,b) -> a+b;

=====================================================

람다식 활용

1. 익명이너클래스 내 구현 메서드의 약식(람다식) 표련 : 함수적 인터페이스만 가능
2. 메서드 참조 (인스턴의 메서의 참조 , 정적 메서드 참조 , 인스턴스의 메서드 참조)
3. 생성자 참조(배열 생성자 참조, 클래스 생성자 참조)


함수형 인터페이스

interface A {
    void method2(int a);
}

익명 클래스
A a = new A(){
   public void method2(int a){
   
   }
}

람다식 활용
A a = (int a) -> {}

--------------------------------------------------
람다식 활용 : 인스턴스의 메서드 참조 타입 ->> 이미 정의된 인스턴스 메서드 참조

interface A {  void abc();};

class B { void bcd(){ System.out.pritnln("메서드");} }


A a = new A(){
   public void abc(){
        B b = new B();
        b.bcd();
   }
}


A a = () ->{
    B b = new B();
    b.bcd; 
}

인스턴스메서드 참조
클래스객체::인스턴스메서드이름

 B b = new B();
 A a = b::bcd;
 
 
 A a = (k) -> {
    System.out.println(k);
 }
 
 A a = System.out::println;
 
 
*/
@FunctionalInterface
interface MyFunction{
	void method();
}

public class Ex03_Lamda {

	public static void main(String[] args) {
		
		//사용방법
		//1. 클래스 이름이 없는 익명 클래스 구현 (전통적인 방법)
		
		MyFunction myfun = new MyFunction() {
			
			@Override
			public void method() {
				// TODO Auto-generated method stub
				System.out.println("hello world!");
			}
			
	};
		 myfun.method();
		 
		 MyFunction my = ( ) -> System.out.println("ramda");
		 my.method();
		 
		 my = () -> {
			 System.out.println("ramda2");
			 System.out.println("ramda3");
		 };
		 
		 my.method();
		 
		 //함수만 사용가능 >> 마치 javascript 처럼
	}
}