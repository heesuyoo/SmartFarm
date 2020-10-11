package Farm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Logon_Frame extends JFrame {
	private JButton startbutton; // 시작 버튼
	private JButton writebutton; // 랭킹 확인 버튼

	Logon_Frame(String id) {
		setSize(280, 120);
		setLocationRelativeTo(null); // 화면 정중앙 코드
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null); // gui관리자를 해제한다.

		JLabel idLabel = new JLabel(id + "님"); // id글씨 label
		idLabel.setBounds(110, 10, 80, 25); // 해당 컴포넌트의 절대 위치와 크기 지정 (x : x좌표, y : y좌표, w : 가로 크기, h : 세로 크기)
		this.add(idLabel);

		startbutton = new JButton("게임 시작"); // 게임시작
		startbutton.setBounds(10, 50, 100, 25);
		this.add(startbutton);
		setVisible(true);
		startbutton.addActionListener(new ActionListener() {// 게임시작 버튼에 리스너 작성

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main m = new Main(id);
//				dispose();
			}
		});
		writebutton = new JButton("랭킹 확인"); // 랭킹 확인
		writebutton.setBounds(160, 50, 100, 25);// 절대 위치, 위치
		this.add(writebutton);
		
		writebutton.addActionListener(new ActionListener() { // 랭킹 확인 버튼에 리스너 설정
			@Override
			public void actionPerformed(ActionEvent e) {
				Rank_Frame rk = new Rank_Frame();
			}
		});
	}
}
