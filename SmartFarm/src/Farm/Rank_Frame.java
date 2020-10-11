package Farm;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Rank_Frame extends JFrame {
	private JButton buttonExit; // 나가기 버튼
	ArrayList<Recordbean> arr = new ArrayList<Recordbean>();
	JLabel writeid;
	JLabel writetime;

	@SuppressWarnings("unchecked")
	Rank_Frame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 500); // 사이즈
		setLocationRelativeTo(null); // 창을 가운데에 띄우게 한다.

		JPanel panel4 = new JPanel();
		panel4.setLayout(new GridLayout(3, 0));

		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		JLabel writeLabel = new JLabel("전체 기록");

		panel1.add(writeLabel);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(6, 0));

		DBBean db = DBBean.getInstance();
		
		arr = db.selectLog();

		for (int i = 0; i < arr.size(); i++) {
			writeid =  new JLabel("id :" + (arr.get(i).getUser_id())+ "딸기 : "+String.valueOf(arr.get(i).getUser_berry())
					+ "수박 : "+String.valueOf(arr.get(i).getUser_melon()) +"호박 : "+String.valueOf(arr.get(i).getUser_pum())
					+"감자 : "+String.valueOf(arr.get(i).getUser_potato())+"상추 : "+String.valueOf(arr.get(i).getUser_lettuce())
					+"옥수수 : "+String.valueOf(arr.get(i).getUser_corn()));
			panel2.add(writeid);
		}

		JPanel panel3 = new JPanel();
		buttonExit = new JButton("나가기");
		panel3.setLayout(null);
		buttonExit.setBounds(310, 100, 80, 25); // 절대 위치,크기 조정
		panel3.add(buttonExit);

		panel4.add(panel1);
		panel4.add(panel2);
		panel4.add(panel3);

		this.add(panel4);
		setVisible(true);
		buttonExit.addActionListener(new ActionListener() { // 나가기 버튼 리스너 설정
			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();
			}
		});
	}
}
