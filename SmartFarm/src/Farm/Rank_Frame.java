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
	private JButton buttonExit; // ������ ��ư
	ArrayList<Recordbean> arr = new ArrayList<Recordbean>();
	JLabel writeid;
	JLabel writetime;

	@SuppressWarnings("unchecked")
	Rank_Frame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 500); // ������
		setLocationRelativeTo(null); // â�� ����� ���� �Ѵ�.

		JPanel panel4 = new JPanel();
		panel4.setLayout(new GridLayout(3, 0));

		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		JLabel writeLabel = new JLabel("��ü ���");

		panel1.add(writeLabel);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(6, 0));

		DBBean db = DBBean.getInstance();
		
		arr = db.selectLog();

		for (int i = 0; i < arr.size(); i++) {
			writeid =  new JLabel("id :" + (arr.get(i).getUser_id())+ "���� : "+String.valueOf(arr.get(i).getUser_berry())
					+ "���� : "+String.valueOf(arr.get(i).getUser_melon()) +"ȣ�� : "+String.valueOf(arr.get(i).getUser_pum())
					+"���� : "+String.valueOf(arr.get(i).getUser_potato())+"���� : "+String.valueOf(arr.get(i).getUser_lettuce())
					+"������ : "+String.valueOf(arr.get(i).getUser_corn()));
			panel2.add(writeid);
		}

		JPanel panel3 = new JPanel();
		buttonExit = new JButton("������");
		panel3.setLayout(null);
		buttonExit.setBounds(310, 100, 80, 25); // ���� ��ġ,ũ�� ����
		panel3.add(buttonExit);

		panel4.add(panel1);
		panel4.add(panel2);
		panel4.add(panel3);

		this.add(panel4);
		setVisible(true);
		buttonExit.addActionListener(new ActionListener() { // ������ ��ư ������ ����
			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();
			}
		});
	}
}
