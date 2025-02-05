package 이쁜이버거;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class 햄버거_Test {
    private static final ArrayList<String> 선택재료 = new ArrayList<>();
    private static final List<String> 유효재료 = List.of(
            "치즈", "패티", "토마토", "상추", "계란후라이",
            "고추", "피클", "양파", "치킨", "스마일",
            "돈까스", "슬라임", "마카롱", "똥", "완료"
    );
    private static final int TIME_LIMIT = 120;
    private static Timer timer;
    private static JLabel timerLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("햄버거 만들기♥");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 500);
            frame.setLocationRelativeTo(null);
            try {
	            ImageIcon originalIcon = new ImageIcon("hamburger/햄버거.jpg");
	            Image originalImage = originalIcon.getImage();
	            
	            // 64x64 크기로 조절 (더 잘 보이도록)
	            Image resizedImage = originalImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
	            frame.setIconImage(resizedImage);
	            
	        } catch (Exception e) {
	            System.out.println("이미지 설정 중 오류 발생: " + e.getMessage());
	        }

	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel(new BorderLayout());
            JPanel buttonPanel = new JPanel(new GridLayout(3, 5, 10, 10));

            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

            JLabel instructionLabel = new JLabel("재료를 선택하고 완료 버튼을 누르세요♥", SwingConstants.CENTER);
            instructionLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));

            timerLabel = new JLabel("Time : 2:00", SwingConstants.CENTER);
            timerLabel.setFont(new Font("Arial", Font.BOLD, 16));

            topPanel.add(instructionLabel, BorderLayout.NORTH);
            topPanel.add(timerLabel, BorderLayout.SOUTH);

            for (String 재료 : 유효재료) {
                JButton button = new JButton();

                try {
                    ImageIcon icon = new ImageIcon("image/" + 재료 + ".jpg");
                    Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(img));
                } catch (Exception e) {
                    button.setText(재료);
                }

                // 버튼 스타일링 (기본 테두리 설정)
                button.setFont(new Font("맑은 고딕", Font.BOLD, 14));
                button.setFocusPainted(false);  // 포커스 시 테두리 안 보이게 하기

                if (재료.equals("완료")) {
                    button.addActionListener(e -> {
                        timer.stop();
                        showCompletionMessage(frame);
                    });
                } else {
                    button.addActionListener(e -> {
                        선택재료.add(재료);
                        // 핑크색 배경 제거 -> 기본 상태로 남기기
                    });
                }

                buttonPanel.add(button);
            }

            panel.add(topPanel, BorderLayout.NORTH);
            panel.add(buttonPanel, BorderLayout.CENTER);
            frame.add(panel);

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    timer.stop();
                    showCompletionMessage(frame);
                }
            });

            frame.setVisible(true);

            startTimer(frame);
        });
    }

    private static void startTimer(JFrame frame) {
        timer = new Timer(1000, new ActionListener() {
            int remainingTime = TIME_LIMIT;

            @Override
            public void actionPerformed(ActionEvent e) {
                int minutes = remainingTime / 60;
                int seconds = remainingTime % 60;
                timerLabel.setText(String.format("Time : %d:%02d", minutes, seconds));

                if (remainingTime == 0) {
                    timer.stop();
                    showCompletionMessage(frame);
                }
                remainingTime--;
            }
        });
        timer.start();
    }

    private static void showCompletionMessage(JFrame frame) {
        String resultMessage = 선택재료.isEmpty() ? "💔선택된 재료가 없습니다💔" : "선택된 재료: " + String.join(", ", 선택재료);
        JOptionPane.showMessageDialog(
                frame,
                resultMessage + "\n♥햄버거 제작 완료♥\n",
                "완료",
                JOptionPane.INFORMATION_MESSAGE
        );
        frame.dispose();
        사이드_Test.사이드메뉴선택(선택재료, 시작_Test.get현재손님());
    }

    public static List<String> get선택재료() {
        return new ArrayList<>(선택재료);
    }
}
