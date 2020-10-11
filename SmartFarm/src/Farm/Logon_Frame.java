package Farm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Logon_Frame extends JFrame {
	private JButton startbutton; // ���� ��ư
	private JButton writebutton; // ��ŷ Ȯ�� ��ư

	Logon_Frame(String id) {
		setSize(280, 120);
		setLocationRelativeTo(null); // ȭ�� ���߾� �ڵ�
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null); // gui�����ڸ� �����Ѵ�.

		JLabel idLabel = new JLabel(id + "��"); // id�۾� label
		idLabel.setBounds(110, 10, 80, 25); // �ش� ������Ʈ�� ���� ��ġ�� ũ�� ���� (x : x��ǥ, y : y��ǥ, w : ���� ũ��, h : ���� ũ��)
		this.add(idLabel);

		startbutton = new JButton("���� ����"); // ���ӽ���
		startbutton.setBounds(10, 50, 100, 25);
		this.add(startbutton);
		setVisible(true);
		startbutton.addActionListener(new ActionListener() {// ���ӽ��� ��ư�� ������ �ۼ�

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main m = new Main(id);
//				dispose();
			}
		});
		writebutton = new JButton("��ŷ Ȯ��"); // ��ŷ Ȯ��
		writebutton.setBounds(160, 50, 100, 25);// ���� ��ġ, ��ġ
		this.add(writebutton);
		
		writebutton.addActionListener(new ActionListener() { // ��ŷ Ȯ�� ��ư�� ������ ����
			@Override
			public void actionPerformed(ActionEvent e) {
				Rank_Frame rk = new Rank_Frame();
			}
		});
	}
}
