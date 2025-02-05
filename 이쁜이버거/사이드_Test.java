package 이쁜이버거;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class 사이드_Test {
    private static final int TIME_LIMIT = 30;
    private static Timer timer;
    private static JLabel timerLabel;

    public static void 사이드메뉴선택(ArrayList<String> 선택재료, String 현재손님) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("사이드 메뉴 선택");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 600);
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
            JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            JPanel topPanel = new JPanel(new BorderLayout());

            JLabel instructionLabel = new JLabel("음료를 선택하세요♥", SwingConstants.CENTER);
            timerLabel = new JLabel("남은 시간: 0:30", SwingConstants.CENTER);
            timerLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
            topPanel.add(instructionLabel, BorderLayout.NORTH);
            topPanel.add(timerLabel, BorderLayout.SOUTH);

            String[] 음료목록 = { "콜라", "사이다", "오렌지 주스", "물" };
            String[] 이미지경로 = { "Juice/콜라.jpg", "Juice/사이다.jpg", "Juice/오렌지 주스.jpg", "Juice/물.jpg" };

            final String[] 선택된음료 = { "" };

            for (int i = 0; i < 음료목록.length; i++) {
                JButton button = createButton(음료목록[i], 이미지경로[i], 선택된음료, frame, 선택재료, 현재손님);
                buttonPanel.add(button);
            }

            panel.add(topPanel, BorderLayout.NORTH);
            panel.add(buttonPanel, BorderLayout.CENTER);

            frame.setLocationRelativeTo(null);
            frame.add(panel);
            frame.setVisible(true);

            startTimer(frame, 선택재료, 현재손님, 선택된음료);
        });
    }

    private static JButton createButton(String 음료, String 이미지경로, String[] 선택된음료, JFrame frame, ArrayList<String> 선택재료,
                                       String 현재손님) {
        JButton button = new JButton();
        try {
            ImageIcon icon = new ImageIcon(이미지경로);
            Image img = icon.getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            button.setText(음료);
        }

        button.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 160));

        button.addActionListener(e -> {
            선택된음료[0] = 음료;
            timer.stop();
            showCompletionMessage(frame, 선택재료, 현재손님, 선택된음료[0]);
        });
        return button;
    }

    private static void startTimer(JFrame frame, ArrayList<String> 선택재료, String 현재손님, String[] 선택된음료) {
        timer = new Timer(1000, new ActionListener() {
            int remainingTime = TIME_LIMIT;

            @Override
            public void actionPerformed(ActionEvent e) {
                timerLabel.setText(String.format("남은 시간: 0:%02d", remainingTime));
                if (remainingTime == 0) {
                    timer.stop();
                    if (선택된음료[0].isEmpty()) {
                        선택된음료[0] = "물";
                    }
                    showCompletionMessage(frame, 선택재료, 현재손님, 선택된음료[0]);
                }
                remainingTime--;
            }
        });
        timer.start();
    }

    private static void showCompletionMessage(JFrame frame, ArrayList<String> 선택재료, String 현재손님, String 선택된음료) {
        frame.dispose(); // 사이드 메뉴 창을 닫고

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
        JOptionPane.showMessageDialog(frame, "선택한 음료: " + 선택된음료, "음료 확인", JOptionPane.INFORMATION_MESSAGE);

        JDialog loadingDialog = new JDialog(frame, "결과 계산 중...♥", true);
        loadingDialog.setSize(300, 180);
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        loadingDialog.setLocationRelativeTo(frame);
        loadingDialog.setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("♥ 결과 계산 중 ♥");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        titlePanel.add(titleLabel);

        JLabel loadingLabel = new JLabel("잠시만 기다려주세요", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));

        JPanel progressPanel = new JPanel(new BorderLayout(5, 5));

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("맑은 고딕", Font.BOLD, 12));

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(loadingLabel, BorderLayout.CENTER);
        progressPanel.add(progressBar, BorderLayout.SOUTH);
        mainPanel.add(progressPanel, BorderLayout.SOUTH);

        loadingDialog.add(mainPanel);

        Timer blinkTimer = new Timer(500, new ActionListener() {
            boolean isVisible = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isVisible) {
                    loadingLabel.setText("♥ 잠시만 기다려주세요 ♥");
                } else {
                    loadingLabel.setText("잠시만 기다려주세요");
                }
                isVisible = !isVisible;
            }
        });
        blinkTimer.start();

        Timer progressTimer = new Timer(50, new ActionListener() {
            private int progress = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                progress += 2;
                progressBar.setValue(progress);
                progressBar.setString(progress + "%");

                if (progress >= 100) {
                    ((Timer) e.getSource()).stop();
                    blinkTimer.stop();
                }
            }
        });

        Timer mainTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blinkTimer.stop();
                HashmapTest.평가(선택재료, 시작_Test.get현재손님(), 선택된음료);
                loadingDialog.dispose();
            }
        });

        mainTimer.setRepeats(false);

        progressTimer.start();
        mainTimer.start();

        loadingDialog.setVisible(true);
    }
}
