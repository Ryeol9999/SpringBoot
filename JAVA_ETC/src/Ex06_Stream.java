import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;




public class Ex06_Stream {
	public static void main(String[] args) {
		List<String> list = Arrays.asList("a","b","c");
		
		//List부터 스트림을 생성
		Stream<String> listStream = list.stream();
		
		//배열로 부터 스트림을 생성
		Stream<String> stream = Stream.of("a", "b", "c");
		Stream<String> stream2 = Stream.of(new String[] {"a", "b", "c"});
		Stream<String> stream3 = Arrays.stream(new String[] {"a", "b", "c"});
		
		
		stream.forEach(System.out::println);
		
		/*
		  [ 원시 Stream 생성 ]
		  위와 같이 객체를 위한 Stream 외에도 int와 long 그리고 double과 같은 원시 자료형들을 사용하기 위한 
		  특수한 종류의 Stream(IntStream, LongStream, DoubleStream) 들도 사용할 수 있으며, 
		  Intstream같은 경우 range()함수를 사용하여 기존의 for문을 대체할 수 있다.
		*/
		
		IntStream stream4 = IntStream.range(4, 10);
		stream4.forEach(System.out::println);
	}
}
