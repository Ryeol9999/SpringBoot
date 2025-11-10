package Thread;

import javax.swing.JOptionPane;

// ===========================
// 1. 시간 카운트 스레드
// ===========================
class GugudanTimer extends Thread {

    @Override
    public void run() {
        for (int i = 10; i > 0; i--) {
            if (GugudanGame.inputCheck) return; // 입력 완료되면 시간 스레드 종료

            System.out.println("⏳ 남은시간 : " + i + "초");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 시간이 다되면 시간초과 설정
        GugudanGame.timeOut = true;
        System.out.println("❌ 시간초과!");
    }
}


// ===========================
// 2. 입력 스레드
// ===========================
class GugudanInputThread extends Thread {

    @Override
    public void run() {
        // 문제 랜덤 생성
        int a = (int)(Math.random() * 9) + 1;
        int b = (int)(Math.random() * 9) + 1;

        String question = a + " × " + b + " = ?";

        String input = JOptionPane.showInputDialog("구구단 문제를 푸세요!\n\n" + question);

        if (input != null && !input.equals("")) {
            GugudanGame.inputCheck = true;  // 입력 성공 표시

            try {
                int userAnswer = Integer.parseInt(input);

                if (userAnswer == a * b) {
                    System.out.println("🎉 정답입니다!");
                } else {
                    System.out.println("❌ 오답입니다. 정답은 " + (a * b) + " 입니다.");
                }

            } catch (NumberFormatException e) {
                System.out.println("❌ 숫자를 입력해야 합니다!");
            }
        } else {
            System.out.println("입력이 없습니다.");
        }
    }
}


// ===========================
// 3. 메인 클래스
// ===========================
public class GugudanGame {

    static boolean inputCheck = false; // 입력유무 체크
    static boolean timeOut = false;   // 시간초과 체크

    public static void main(String[] args) {

        GugudanTimer timer = new GugudanTimer();
        GugudanInputThread inputThread = new GugudanInputThread();

        timer.start();
        inputThread.start();

        try {
            timer.join();
            inputThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("===== 게임 종료 =====");

        if (!inputCheck && timeOut) {
            System.out.println("⏰ 답을 입력하지 않아 시간초과로 종료됩니다.");
        }

        System.out.println("Main END");
    }
}
