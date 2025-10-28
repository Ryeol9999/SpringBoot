package AOP;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StopWatch;

/*
 간단한 사칙 연산기 만들기
    주업무 : (주관심, 코어컨선) : 사칙연산(ADD, MUL, ...) 주기능
 */

// 요구 사항 연산에 걸린 시간을 측정해보라. - 보조기능(보조관심, 공통 관심)
// 요구 사항2 : 로그 출력(보조 기능, 공통 관심)
public class Calc {
   public int Add(int x, int y) {
      //시간 처리와 로그 출력을 넣어야함
      Log log = LogFactory.getLog(this.getClass());
      StopWatch sw = new StopWatch();
      sw.start();
      log.info("[타이머 시작]");
      
      //주업무
      int result = x + y;
      
      //시간 처리 + log출력
      sw.stop();
      log.info("[타이머 종료]");
      log.info("[Time log Method ADD]");
      log.info("[Time log Method]" + sw.getTotalTimeMillis());
      
      return result;
   }
   
   public int Mul(int x, int y) {
      
      //주업무
      int result = x * y;
      
      return result;
   }
   
}
