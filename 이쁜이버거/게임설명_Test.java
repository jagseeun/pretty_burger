package 이쁜이버거;

import javax.swing.*;
import java.awt.*;

public class 게임설명_Test {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("이쁜이 버거 만들기♥");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(400, 200);
			frame.setLocationRelativeTo(null);
			  try {
		            ImageIcon originalIcon = new ImageIcon("hamburger/햄버거.jpg");
		            Image originalImage = originalIcon.getImage();
		          
		            Image resizedImage = originalImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
		            frame.setIconImage(resizedImage);
		            
		        } catch (Exception e) {
		            System.out.println("이미지 설정 중 오류 발생: " + e.getMessage());
		        }

		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// 메인 패널 생성
			JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
			frame.add(mainPanel);

			// 제목 라벨
			JLabel titleLabel = new JLabel("이쁜이 버거 만들기♥", SwingConstants.CENTER);
			titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
			mainPanel.add(titleLabel, BorderLayout.NORTH);

			// 버튼 패널
			JPanel buttonPanel = new JPanel(new FlowLayout());
			JButton startButton = new JButton("게임 시작");
			JButton infoButton = new JButton("게임 설명");

			buttonPanel.add(startButton);
			buttonPanel.add(infoButton);
			mainPanel.add(buttonPanel, BorderLayout.CENTER);

			// 버튼 동작 설정
			startButton.addActionListener(e -> {
				JOptionPane.showMessageDialog(frame, "즐거운 게임 되세요♥", "게임 시작", JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
				시작_Test.main(null); // 게임 시작 로직 호출
			});

			infoButton.addActionListener(e -> showGameInstructions(frame));

			frame.setVisible(true);

		});

	}

	private static void showGameInstructions(JFrame parentFrame) {
		// 게임 설명 다이얼로그
		JDialog infoDialog = new JDialog(parentFrame, "♥게임 설명♥", true);
		infoDialog.setSize(400, 300);
		infoDialog.setLocationRelativeTo(parentFrame);

		JTextArea instructions = new JTextArea("손님의 특징에 맞춰 햄버거를 제작하는 게임입니다♥\n\n" + "게임 진행 방식:\n"
				+ "1. 재료는 총 14가지가 준비되어 있습니다\n" + "2. 순서는 자유롭게 선택할 수 있습니다\n" + "3. 제한 시간은 2분입니다\n"
				+ "4. 완성 후에는 수정이 불가능합니다\n" + "5. 음료수를 선택하면 손님에게 제공됩니다\n" + "6. 손님의 선호도에 따라 평가가 달라집니다\n\n"
				+ "힌트: 선호 재료를 많이 넣을수록 점수가 높아집니다!");
		instructions.setEditable(false);
		infoDialog.add(new JScrollPane(instructions), BorderLayout.CENTER);

		// 닫기 버튼
		JButton closeButton = new JButton("닫기");
		closeButton.addActionListener(e -> infoDialog.dispose());
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(closeButton);
		infoDialog.add(buttonPanel, BorderLayout.SOUTH);

		infoDialog.setVisible(true);
	}

}
