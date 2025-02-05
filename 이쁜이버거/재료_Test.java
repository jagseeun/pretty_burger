package 이쁜이버거;

import javax.swing.*;
import java.awt.*;

public class 재료_Test {
	private JFrame frame;
	private JLabel imageLabel, nameLabel;
	private int currentIndex = 0;
	private String[] imageNames = {

			"image/상추.jpg", "image/계란후라이.jpg", "image/스마일.jpg", "image/치킨.jpg", "image/토마토.jpg", "image/피클.jpg",
			"image/고추.jpg", "image/패티.jpg", "image/슬라임.jpg", "image/돈까스.jpg", "image/똥.jpg", "image/빵.jpg",
			"image/마카롱.jpg", "image/양파.jpg", "image/치즈.jpg"

	};

	public 재료_Test() {
		frame = new JFrame("햄버거 재료♥");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(400, 500);
		frame.setLayout(new BorderLayout());
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

//	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 이미지와 이름 레이블
		imageLabel = new JLabel("", JLabel.CENTER);
		nameLabel = new JLabel("", JLabel.CENTER);
		nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));

		frame.add(imageLabel, BorderLayout.CENTER);
		frame.add(nameLabel, BorderLayout.NORTH);

		// 버튼 패널
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JButton prevButton = new JButton("이전");
		JButton nextButton = new JButton("다음");
		JButton makeBurgerButton = new JButton("햄버거 만들기");

		prevButton.addActionListener(e -> changeImage(-1));
		nextButton.addActionListener(e -> changeImage(1));
		makeBurgerButton.addActionListener(e -> {
		    JOptionPane.showMessageDialog(frame, "햄버거 만들기로 이동합니다♥");
		    frame.dispose();
		    햄버거_Test.main(null); // 햄버거 만들기 창 열기
            
		});
		buttonPanel.add(prevButton);
		buttonPanel.add(makeBurgerButton);
		buttonPanel.add(nextButton);

		frame.add(buttonPanel, BorderLayout.SOUTH);

		updateImage();
		frame.setVisible(true);
	}

	private void changeImage(int direction) {
		currentIndex = (currentIndex + direction + imageNames.length) % imageNames.length;
		updateImage();
	}

	private void updateImage() {
		try {
			ImageIcon icon = new ImageIcon(imageNames[currentIndex]);
			Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
			imageLabel.setIcon(new ImageIcon(img));
			String imageName = imageNames[currentIndex].substring(imageNames[currentIndex].lastIndexOf("/") + 1);
			nameLabel.setText(imageName.replace(".jpg", ""));
		} catch (Exception e) {
			imageLabel.setIcon(null);
			nameLabel.setText("이미지를 찾을 수 없습니다.");
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(재료_Test::new);
	}
}
