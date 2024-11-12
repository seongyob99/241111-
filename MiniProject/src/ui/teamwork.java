package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class teamwork extends JFrame {

    private JPanel mainPanel;
    private JPanel boardViewPanel;
    private JPanel boardWritePanel;

    // 게시글을 저장할 Map (게시글 제목을 key로 사용하고, 게시글 내용은 Board 객체로 저장)
    private Map<String, Board> boardContents;

    public teamwork() {
        setTitle("게시판 프로그램");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boardContents = new HashMap<>();

        setLayout(new BorderLayout());

        // 메인 패널 생성 및 버튼 추가
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        JButton viewBoardButton = new JButton("게시판 조회");
        JButton writeBoardButton = new JButton("게시판 글쓰기");

        mainPanel.add(viewBoardButton);
        mainPanel.add(writeBoardButton);

        add(mainPanel, BorderLayout.NORTH);

        // 게시판 조회 화면 패널
        boardViewPanel = new JPanel();
        boardViewPanel.setLayout(new BorderLayout());
        JTextArea boardTextArea = new JTextArea();
        boardTextArea.setEditable(false);
        boardViewPanel.add(new JLabel("게시판 조회 화면"), BorderLayout.NORTH);
        boardViewPanel.add(boardTextArea, BorderLayout.CENTER);

        // 게시판 글쓰기 화면 패널
        boardWritePanel = new JPanel();
        boardWritePanel.setLayout(new FlowLayout());
        boardWritePanel.add(new JLabel("제목: "));
        JTextField titleField = new JTextField(15);
        boardWritePanel.add(titleField);
        boardWritePanel.add(new JLabel("내용: "));
        JTextField contentField = new JTextField(15);
        boardWritePanel.add(contentField);

        JButton saveButton = new JButton("게시글 작성");
        boardWritePanel.add(saveButton);

        // 게시판 조회 버튼 동작
        viewBoardButton.addActionListener(e -> showBoardView(boardTextArea));

        // 게시판 글쓰기 버튼 동작
        writeBoardButton.addActionListener(e -> showBoardWrite());

        // 게시글 작성 버튼 동작 (서버에 저장)
        saveButton.addActionListener(e -> {
            String title = titleField.getText();
            String content = contentField.getText();
            if (!title.isEmpty() && !content.isEmpty()) {
                boardContents.put(title, new Board(title, content));
                titleField.setText("");
                contentField.setText("");
                System.out.println("게시글이 저장되었습니다.");
                // 게시글 저장 후 바로 게시판 조회 화면을 갱신하여 게시글을 표시
                showBoardView(boardTextArea);
            } else {
                System.out.println("제목과 내용을 입력하세요.");
            }
        });

        setVisible(true);
    }

    // 게시판 조회 화면을 메인 프레임에 표시하는 메서드
    private void showBoardView(JTextArea boardTextArea) {
        boardTextArea.setText(""); // 기존 텍스트 초기화
        // 저장된 게시글을 하나씩 출력
        for (Board board : boardContents.values()) {
            boardTextArea.append(board.toString() + "\n\n");
        }
        // 화면을 갱신
        getContentPane().removeAll();
        add(mainPanel, BorderLayout.NORTH);
        add(boardViewPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // 게시판 글쓰기 화면을 메인 프레임에 표시하는 메서드
    private void showBoardWrite() {
        getContentPane().removeAll();
        add(mainPanel, BorderLayout.NORTH);
        add(boardWritePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new teamwork();
    }
}

class Board {
    private String title;
    private String content;

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "제목: " + title + "\n내용: " + content;
    }
}
