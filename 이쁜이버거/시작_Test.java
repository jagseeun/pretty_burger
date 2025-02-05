package 이쁜이버거;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.List;

public class 시작_Test extends JFrame {
    private static String 현재손님 = "";
    private static final HashMap<String, String> 손님정보 = new HashMap<>();
    private JLabel 손님이미지Label;
    private JLabel 손님이름Label;
    private JTextPane 손님정보Label;
    private static final String IMAGE_DIRECTORY = "손님";

    public 시작_Test() {
        setTitle("이쁜이버거");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        try {
            List<Image> icons = new ArrayList<>();
            ImageIcon originalIcon = new ImageIcon("hamburger/햄버거.jpg");
            Image originalImage = originalIcon.getImage();
            icons.add(originalImage.getScaledInstance(16, 16, Image.SCALE_SMOOTH));
            icons.add(originalImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            icons.add(originalImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH));
            icons.add(originalImage.getScaledInstance(128, 128, Image.SCALE_SMOOTH));

            setIconImages(icons);  // 아이콘 설정
        } catch (Exception e) {
            System.out.println("이미지 설정 중 오류 발생: " + e.getMessage());
        }

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // 손님 정보 패널
        JPanel 손님Panel = new JPanel();
        손님Panel.setLayout(new BoxLayout(손님Panel, BoxLayout.Y_AXIS));
        손님Panel.setBackground(Color.WHITE);
        손님Panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 손님 이미지
        손님이미지Label = new JLabel("", SwingConstants.CENTER);
        손님이미지Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        손님Panel.add(손님이미지Label);

        // 손님 이름
        손님이름Label = new JLabel("", SwingConstants.CENTER);
        손님이름Label.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        손님이름Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        손님Panel.add(손님이름Label);

        // 손님 정보
        손님정보Label = new JTextPane();
        손님정보Label.setEditable(false);
        손님정보Label.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        손님정보Label.setBackground(Color.WHITE);

        // 텍스트 가운데 정렬
        StyledDocument doc = 손님정보Label.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        손님Panel.add(손님정보Label);
        mainPanel.add(손님Panel, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton 재료보기Button = new JButton("재료 보기");
        JButton 햄버거만들기Button = new JButton("햄버거 만들기");

        재료보기Button.addActionListener(e -> {
            재료_Test.main(null); // 재료 보기 창 열기
            this.dispose();  // 현재 창 닫기
        });
        
        햄버거만들기Button.addActionListener(e -> {
            햄버거_Test.main(null); // 햄버거 만들기 창 열기
            this.dispose();  // "햄버거 만들기" 클릭 시 현재 창 닫기
        });

        buttonPanel.add(재료보기Button);
        buttonPanel.add(햄버거만들기Button);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void 손님선택() {
        initializeGuestData();

        try {
            ArrayList<Path> imageFiles = new ArrayList<>();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(IMAGE_DIRECTORY),
                    "*.{jpg,JPG,jpeg,JPEG}")) {
                for (Path path : stream) {
                    imageFiles.add(path);
                }
            }

            if (imageFiles.isEmpty())
                throw new IOException("손님 이미지 파일이 없습니다.");

            Path selectedImage = imageFiles.get(new Random().nextInt(imageFiles.size()));
            현재손님 = selectedImage.getFileName().toString().replaceFirst("[.][^.]+$", "");

            ImageIcon icon = new ImageIcon(selectedImage.toString());
            Image img = icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH); // 이미지 크기 2배
            손님이미지Label.setIcon(new ImageIcon(img));

            손님이름Label.setText(현재손님);
            손님정보Label.setText(손님정보.getOrDefault(현재손님, "정보가 없습니다."));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "오류 발생: " + e.getMessage(), "에러", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeGuestData() {
        손님정보.clear();
        손님정보.put("박다니엘", "우아한 품격이 돋보이며 세련된 취향을 가진 인물. 그러나 의외로 야채를 선호하지 않는 독특한 면모가 있다.");
        손님정보.put("김치즈", "밝고 활기찬 에너지가 넘치는 캐릭터. 치즈에 대한 사랑은 남다르며 늘 웃음을 선사하는 유쾌한 존재.");
        손님정보.put("오잉크", "둥글둥글한 체형과 은은한 분홍빛 피부를 가진 사랑스러운 캐릭터. 부드러운 첫인상 뒤에 숨겨진 따뜻한 마음이 특징.");
        손님정보.put("똥", "미지의 존재. 익살스러운 기운이 감돌며 어디선가 \"뿡뿡\" 소리가 들려오는 듯하다.");
        손님정보.put("비어게인", "이름에서 풍기는 암시처럼 육류 소비를 멀리하는 삶의 철학을 지닌 듯하다.");
        손님정보.put("시그마보이", "압도적인 존재감을 가진 캐릭터. 어디에서나 \"SIGMA BOY\"라는 명칭이 울려 퍼질 만큼 강렬하고 개성 넘치는 카리스마를 지녔다.");
        손님정보.put("스폰지밥", "끝없는 활기와 장난기로 가득 찬 캐릭터. 특유의 웃음소리가 주변을 들썩이게 만드는 유쾌함의 상징.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                List<Image> icons = new ArrayList<>();
                ImageIcon originalIcon = new ImageIcon("hamburger/햄버거.jpg");
                Image originalImage = originalIcon.getImage();

                // 여러 크기의 아이콘 추가
                icons.add(originalImage.getScaledInstance(16, 16, Image.SCALE_SMOOTH));
                icons.add(originalImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
                icons.add(originalImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH));
                icons.add(originalImage.getScaledInstance(128, 128, Image.SCALE_SMOOTH));

                // mainMenuFrame에 아이콘 설정
                JFrame mainMenuFrame = new JFrame("손님 등장♥");
                mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainMenuFrame.setSize(300, 150);
                mainMenuFrame.setLocationRelativeTo(null);
                mainMenuFrame.setIconImages(icons);  // 아이콘 설정

                JPanel mainPanel = new JPanel();
                JLabel sonnim = new JLabel("♥ 손님 오셨습니다 ♥");
                JButton startButton = new JButton("손님 확인하기");

                startButton.addActionListener(e -> {
                    시작_Test frame = new 시작_Test();
                    frame.setVisible(true);
                    frame.손님선택();
                    mainMenuFrame.dispose();  // "손님 확인하기" 클릭 시 창 닫기
                });

                mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

                sonnim.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
                sonnim.setAlignmentX(Component.CENTER_ALIGNMENT);

                startButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
                startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

                mainPanel.setPreferredSize(new Dimension(mainMenuFrame.getWidth(), mainMenuFrame.getHeight()));

                mainPanel.add(Box.createVerticalGlue());
                mainPanel.add(sonnim);
                mainPanel.add(Box.createVerticalStrut(20));  // 라벨과 버튼 사이에 간격 추가
                mainPanel.add(startButton);
                mainPanel.add(Box.createVerticalGlue());
                mainMenuFrame.add(mainPanel);
                mainMenuFrame.setVisible(true);
            } catch (Exception e) {
                System.out.println("이미지 설정 중 오류 발생: " + e.getMessage());
            }
        });
    }

    public static String get현재손님() {
        return 현재손님;
    }

    public static void set현재손님(String 현재손님) {
        시작_Test.현재손님 = 현재손님;
    }
}
