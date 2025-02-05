package 이쁜이버거;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashmapTest {
    private static final HashMap<String, ArrayList<String>> 손님재료 = new HashMap<>();
    private static int 최종점수 = 0;
    private static int 선호재료포함수 = 0;
    private static int 비선호재료수 = 0;
    private static double 선호도비율 = 0.0;

    static {
        손님재료.put("박다니엘", new ArrayList<>(List.of("치킨", "패티", "계란후라이", "돈까스", "스마일")));
        손님재료.put("김치즈", new ArrayList<>(List.of("치즈")));
        손님재료.put("오잉크", new ArrayList<>(List.of("상추", "치즈", "치킨", "고추", "피클", "토마토", "패티", "계란후라이", "돈까스", "마카롱", "양파", "스마일")));
        손님재료.put("똥", new ArrayList<>(List.of("똥")));
        손님재료.put("비어게인", new ArrayList<>(List.of("양파", "상추", "피클", "토마토")));
        손님재료.put("시그마보이", new ArrayList<>(List.of("슬라임", "마카롱", "똥", "스마일")));
        손님재료.put("스폰지밥", new ArrayList<>(List.of("치즈", "상추", "스마일", "피클")));
    }

    private static void showResultImages(String scorePath, String drinkName) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("주문 결과");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout(10, 10));
            try {
                ImageIcon originalIcon = new ImageIcon("hamburger/햄버거.jpg");
                Image originalImage = originalIcon.getImage();
                
                Image resizedImage = originalImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                frame.setIconImage(resizedImage);
            } catch (Exception e) {
                System.out.println("이미지 설정 중 오류 발생: " + e.getMessage());
            }

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            try {
                JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

                ImageIcon scoreIcon = new ImageIcon(scorePath);
                Image scoreImage = scoreIcon.getImage();
                Image scaledScoreImage = scoreImage.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                JLabel scoreLabel = new JLabel(new ImageIcon(scaledScoreImage));

                String drinkPath = "Juice/" + drinkName + ".jpg";
                ImageIcon drinkIcon = new ImageIcon(drinkPath);
                Image drinkImage = drinkIcon.getImage();
                Image scaledDrinkImage = drinkImage.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
                JLabel drinkLabel = new JLabel(new ImageIcon(scaledDrinkImage));

                imagePanel.add(scoreLabel);
                imagePanel.add(drinkLabel);

                JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JLabel resultLabel = new JLabel(getScoreMessage(최종점수));
                resultLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
                resultLabel.setForeground(Color.BLACK);
                textPanel.add(resultLabel);

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

                JButton scoreButton = new JButton("점수 확인");
                JButton deliverButton = new JButton("손님께 가져다 드리기");
                JButton restartButton = new JButton("다시 플레이하기");

                scoreButton.addActionListener(e -> {
                    JFrame scoreFrame = new JFrame("점수 확인");
                    scoreFrame.setSize(400, 300);

                    JPanel contentPanel = new JPanel();
                    contentPanel.setLayout(new BorderLayout());
                    if (최종점수 >= 100) {
                        contentPanel.setBackground(Color.GREEN);
                    } else if (최종점수 >= 70) {
                        contentPanel.setBackground(Color.BLUE);
                    } else if (최종점수 >= 50) {
                        contentPanel.setBackground(Color.ORANGE);
                    } else {
                        contentPanel.setBackground(Color.RED);
                    }

                    String 선호도퍼센트 = String.format("%.1f", 선호도비율 * 100);
                    JTextArea messageArea = new JTextArea();
                    messageArea.setEditable(false);
                    messageArea.setBackground(contentPanel.getBackground());
                    messageArea.setText(String.format(
                            "최종 점수: %d점\n" +
                            "선호하는 재료 수: %d개\n" +
                            "선호하지 않는 재료 수: %d개\n" +
                            "선호 재료 비율: %s%%",
                            최종점수, 선호재료포함수, 비선호재료수, 선호도퍼센트
                    ));

                    contentPanel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
                    scoreFrame.add(contentPanel);
                    scoreFrame.setLocationRelativeTo(null);
                    scoreFrame.setVisible(true);
                });

                deliverButton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(frame,
                            "손님께 음식을 전달했습니다!",
                            "전달 완료",
                            JOptionPane.INFORMATION_MESSAGE);
//                    frame.dispose();
                });

                restartButton.addActionListener(e -> {
                    frame.dispose();
                    try {
                        Class.forName("이쁜이버거.게임설명_Test").getMethod("main", String[].class)
                            .invoke(null, (Object) new String[0]);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame,
                            "게임을 다시 시작하는 중 오류가 발생했습니다.",
                            "오류",
                            JOptionPane.ERROR_MESSAGE);
                    }
                });

                buttonPanel.add(scoreButton);
                buttonPanel.add(deliverButton);
                buttonPanel.add(restartButton);

                frame.add(imagePanel, BorderLayout.CENTER);
                frame.add(textPanel, BorderLayout.SOUTH);
                frame.add(buttonPanel, BorderLayout.SOUTH);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                System.out.println("이미지를 불러오는 데 실패했습니다.");
                e.printStackTrace();
            }
        });
    }

    private static String getScoreMessage(int score) {
        String 선호도퍼센트 = String.format("%.1f", 선호도비율 * 100);

        StringBuilder message = new StringBuilder();
        message.append(String.format("선호하는 재료 수: %d개\n", 선호재료포함수));
        message.append(String.format("선호하지 않는 재료 수: %d개\n", 비선호재료수));
        message.append(String.format("선호 재료 비율: %s%%\n\n", 선호도퍼센트));
        if (score >= 100) {
            return "와 정말 잘 만드셨네요♥ 이건 손님이 무조건 좋아하실 겁니다♥♥ 제가 장담해요!♥♥";
        } else if (score >= 70) {
            return "이 정도면 잘했는데요? 처음 하시는 것 맞죠? 좋아요!!♥";
        } else if (score >= 50) {
            return "아, 아쉽게도 손님이 선호하지 않는 재료가 들어가서 그런지 점수가 낮네요..";
        } else if (score >= 20) {
            return "으악!! 이거 햄버거 맞죠? 먹기 조금 힘들 것 같은데요..!!💔";
        } else {
            return "💔 손님의 취향과 전혀 맞지 않는 햄버거입니다.💔";
        }
    }

    public static void 평가(ArrayList<String> 선택재료, String 현재손님, String 선택된음료) {
        if (!손님재료.containsKey(현재손님)) {
            System.out.println("손님 정보가 없습니다.");
            return;
        }

        ArrayList<String> 선호재료 = 손님재료.get(현재손님);
        int 기본점수 = 50;
        int 최대추가점수 = 50;

        선호재료포함수 = 0;
        비선호재료수 = 0;

        for (String 재료 : 선택재료) {
            if (선호재료.contains(재료)) {
                선호재료포함수++;
            } else {
                비선호재료수++;
            }
        }

        // 수정된 선호도 비율 계산
        // 전체 선택된 재료 중 선호하는 재료의 비율로 계산
        선호도비율 = 선택재료.isEmpty() ? 0 : (double) 선호재료포함수 / 선택재료.size();
        
        // 점수 계산 로직
        최종점수 = Math.min(100, (int) (기본점수 + 선호도비율 * 최대추가점수));

        String scorePath = "Score/20점.jpg";
        if (최종점수 >= 100) {
            scorePath = "Score/100점.jpg";
        } else if (최종점수 >= 70) {
            scorePath = "Score/70점.jpg";
        } else if (최종점수 >= 50) {
            scorePath = "Score/50점.jpg";
        }

        showResultImages(scorePath, 선택된음료);
    }
}