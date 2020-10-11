package Farm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Start_Frame extends JFrame {
	private JTextField idTextField;
	private JTextField pwTextField;
	
	private JTextField userid; // ���̵��Է�
	private JPasswordField userpw; // ��й�ȣ�Է�

	private JButton logInBtn;
	private JButton insertBtn;
	
	private String id = ""; // field���� �Է¹��� ���� �����ϴ� ����
	private String pw = ""; // field���� �Է¹��� ���� �����ϴ� ����

	private DBBean db; // DBBean ����

	Start_Frame(){
		setTitle("�α����� ��Ȱȭ�սô�."); // ����
		setSize(280,140); // ������
	    setLocationRelativeTo(null);   //ȭ�� ���߾� �ڵ�
	    setLayout(null);   //gui�����ڸ� �����Ѵ�.
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
	    JLabel Labelid = new JLabel("���̵�");
	    Labelid.setBounds(10,10,80,25); // �ش� ������Ʈ�� ���� ��ġ�� ũ�� ���� (x : x��ǥ, y : y��ǥ, w  : ���� ũ��, h : ���� ũ��)
	    this.add(Labelid);

	    JLabel Labelpw = new JLabel("��й�ȣ");
	    Labelpw.setBounds(10,40,80,25);
	    this.add(Labelpw);
	    
		userid = new JTextField(10); // id�� �޾ƿ��� ���� ��
		userid.setBounds(100, 10, 160, 25); // ��ġ ����
		this.add(userid);

		userpw = new JPasswordField(10); // ��й�ȣ�� �޾ƿ��� ���� ��
		userpw.setBounds(100, 40, 160, 25); // ��ġ ����
		this.add(userpw);
	    
		logInBtn = new JButton("Login"); // �α��� ��ư
		logInBtn.setBounds(10, 70, 100, 25); // ��ư ��ġ
		this.add(logInBtn);
	    
	    setVisible(true); // ȭ�鿡 �����ֱ�
	   
	    
	    // �α��� ��ư
	    logInBtn.addActionListener(new ActionListener() {   //�α��� ��ư�� ������ ����
	          @Override
	          public void actionPerformed(ActionEvent e) {

	             int num = loginCheck();   //db���� �α��� �����ϸ� 1�� ��ȯ
	             if(num == 1) {
	                JOptionPane.showMessageDialog(null, "�α����� �Ǿ����ϴ�.");
	                Logon_Frame lf = new Logon_Frame(id); // ����Ǹ� �̵�
	                dispose(); // ���ϴ� �ϳ��� Frame�� ���� ��Ű�� ���ؼ��� dispose()�޼ҵ带 �̿��ؾ��Ѵ�.
	             }
	             else if(num == 0) {
	                JOptionPane.showMessageDialog(null, "��й�ȣ�� ���� �ʽ��ϴ�.");
	             }
	             else if(num == -1) {
	                JOptionPane.showMessageDialog(null, "���̵� �������� �ʽ��ϴ�.");
	             }
	             else if(num == 2) {
	                JOptionPane.showMessageDialog(null, "��ĭ�� ä���ּ���");
	             }
	          }
	       });
	    
	    
	    // ȸ�� ���� ��ư
		insertBtn = new JButton("Join"); // ȸ������ ��ư
		insertBtn.setBounds(160, 70, 100, 25); // ȸ�����Թ�ư�� ��ġ
		this.add(insertBtn); // �߰�
		insertBtn.addActionListener(new ActionListener() { // ȸ������ ��ư�� ������ ����
			@Override
			public void actionPerformed(ActionEvent e) {
				memberInsert(); // ȸ������
			}
		});
	    
	}
		// ����
	   public int loginCheck() {
		      id = userid.getText();   //�Է��� id�� �ҷ�����
		      pw = String.valueOf(userpw.getPassword());   //�Է��� passwd�� �ҷ�����

		      if(id.equals("") || pw.equals("")) {
		         return 2;
		      }
		      db = DBBean.getInstance();
		      int num = db.select(id, pw);   //db���� �α��� �� ��������
		      return num;
		   }
	   // ����
	   public void memberInsert() {
		      id = userid.getText();   //�Է��� id�� �ҷ�����
		      pw = String.valueOf(userpw.getPassword());   //�Է��� passwd�� �ҷ�����
		      // valueOf : �Ķ���Ͱ� null�̸� ���ڿ� "null"�� ���� ��ȯ�Ѵ�.
		      if((id.equals(""))||(pw.equals(""))) {
		         JOptionPane.showMessageDialog(null, "ĭ�� �� ä���ּ���");
		         return ;
		      }

		      db = DBBean.getInstance();
		      db.adduser(id, pw);   //db�� id,pw �����ϱ�
		   }
}
