package Thread;

import javax.swing.JOptionPane;

class TimeThread extends Thread {

    @Override
    public void run() {
        for (int i = 10; i > 0; i--) {
            if (GugudanSpeedGame.timeOver) return;
            System.out.println("⏳ 남은시간 : " + i + "초");

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 시간 종료
        GugudanSpeedGame.timeOver = true;
        System.out.println("⛔ 10초 종료!");
    }
}

class InputThread extends Thread {

    @Override
    public void run() {

        while (!GugudanSpeedGame.timeOver) {

            int a = (int)(Math.random() * 9) + 1;
            int b = (int)(Math.random() * 9) + 1;
            int answer = a * b;

            String input = JOptionPane.showInputDialog("문제: " + a + " × " + b + " = ?");

            // ⛔ 시간이 입력 도중 끝났을 경우 바로 종료
            if (GugudanSpeedGame.timeOver) return;

            // 취소 또는 입력 없음 → 틀린 것으로 처리하고 계속
            if (input == null || input.trim().equals("")) {
                GugudanSpeedGame.total++;
                GugudanSpeedGame.wrong++;
                continue;
            }

            try {
                int user = Integer.parseInt(input);
                GugudanSpeedGame.total++;

                if (user == answer) {
                    GugudanSpeedGame.correct++;
                    System.out.println("✔ 정답!");
                } else {
                    GugudanSpeedGame.wrong++;
                    System.out.println("❌ 오답! 정답: " + answer);
                }

            } catch (NumberFormatException e) {
                GugudanSpeedGame.total++;
                GugudanSpeedGame.wrong++;
            }
        }
    }
}

public class GugudanSpeedGame {

    static boolean timeOver = false;

    static int total = 0;
    static int correct = 0;
    static int wrong = 0;

    public static void main(String[] args) {

        TimeThread timer = new TimeThread();
        InputThread inputs = new InputThread();

        timer.start();
        inputs.start();

        try {
            timer.join();
            inputs.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n===== 📊 결과 =====");
        System.out.println("총 문제 수 : " + total);
        System.out.println("맞은 개수 : " + correct);
        System.out.println("틀린 개수 : " + wrong);
        System.out.println("🟦 정확도 : " + (total == 0 ? 0 : (correct * 100 / total)) + "%");
        System.out.println("===================");
        System.out.println("게임 종료!");
    }
}
