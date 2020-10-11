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
	
	private JTextField userid; // 아이디입력
	private JPasswordField userpw; // 비밀번호입력

	private JButton logInBtn;
	private JButton insertBtn;
	
	private String id = ""; // field에서 입력받은 값을 저장하는 변수
	private String pw = ""; // field에서 입력받은 값을 저장하는 변수

	private DBBean db; // DBBean 적용

	Start_Frame(){
		setTitle("로그인을 생활화합시다."); // 제목
		setSize(280,140); // 사이즈
	    setLocationRelativeTo(null);   //화면 정중앙 코드
	    setLayout(null);   //gui관리자를 해제한다.
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
	    JLabel Labelid = new JLabel("아이디");
	    Labelid.setBounds(10,10,80,25); // 해당 컴포넌트의 절대 위치와 크기 지정 (x : x좌표, y : y좌표, w  : 가로 크기, h : 세로 크기)
	    this.add(Labelid);

	    JLabel Labelpw = new JLabel("비밀번호");
	    Labelpw.setBounds(10,40,80,25);
	    this.add(Labelpw);
	    
		userid = new JTextField(10); // id를 받아오기 위한 것
		userid.setBounds(100, 10, 160, 25); // 위치 조절
		this.add(userid);

		userpw = new JPasswordField(10); // 비밀번호를 받아오기 위한 것
		userpw.setBounds(100, 40, 160, 25); // 위치 조절
		this.add(userpw);
	    
		logInBtn = new JButton("Login"); // 로그인 버튼
		logInBtn.setBounds(10, 70, 100, 25); // 버튼 위치
		this.add(logInBtn);
	    
	    setVisible(true); // 화면에 보여주기
	   
	    
	    // 로그인 버튼
	    logInBtn.addActionListener(new ActionListener() {   //로그인 버튼에 리스너 설정
	          @Override
	          public void actionPerformed(ActionEvent e) {

	             int num = loginCheck();   //db에서 로그인 성공하면 1을 반환
	             if(num == 1) {
	                JOptionPane.showMessageDialog(null, "로그인이 되었습니다.");
	                Logon_Frame lf = new Logon_Frame(id); // 연결되면 이동
	                dispose(); // 원하는 하나의 Frame만 종료 시키기 위해서는 dispose()메소드를 이용해야한다.
	             }
	             else if(num == 0) {
	                JOptionPane.showMessageDialog(null, "비밀번호가 맞지 않습니다.");
	             }
	             else if(num == -1) {
	                JOptionPane.showMessageDialog(null, "아이디가 존재하지 않습니다.");
	             }
	             else if(num == 2) {
	                JOptionPane.showMessageDialog(null, "빈칸을 채워주세요");
	             }
	          }
	       });
	    
	    
	    // 회원 가입 버튼
		insertBtn = new JButton("Join"); // 회원가입 버튼
		insertBtn.setBounds(160, 70, 100, 25); // 회원가입버튼의 위치
		this.add(insertBtn); // 추가
		insertBtn.addActionListener(new ActionListener() { // 회원가입 버튼에 리스터 설정
			@Override
			public void actionPerformed(ActionEvent e) {
				memberInsert(); // 회원가입
			}
		});
	    
	}
		// 접속
	   public int loginCheck() {
		      id = userid.getText();   //입력한 id값 불러오기
		      pw = String.valueOf(userpw.getPassword());   //입력한 passwd값 불러오기

		      if(id.equals("") || pw.equals("")) {
		         return 2;
		      }
		      db = DBBean.getInstance();
		      int num = db.select(id, pw);   //db에서 로그인 값 가져오기
		      return num;
		   }
	   // 접속
	   public void memberInsert() {
		      id = userid.getText();   //입력한 id값 불러오기
		      pw = String.valueOf(userpw.getPassword());   //입력한 passwd값 불러오기
		      // valueOf : 파라미터가 null이면 문자열 "null"을 만들어서 반환한다.
		      if((id.equals(""))||(pw.equals(""))) {
		         JOptionPane.showMessageDialog(null, "칸을 다 채워주세요");
		         return ;
		      }

		      db = DBBean.getInstance();
		      db.adduser(id, pw);   //db에 id,pw 저장하기
		   }
}
