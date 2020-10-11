package Farm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/*심을 농작물을 화면에 표시하고 FarmPanel과 연동하는 클래스 CropPanel
 * JFrame 클래스 상속, ActionListener, Runnable 인터페이스 구현
 */
public class CropPanel extends JFrame implements ActionListener, Runnable {
	private JButton btnz[];					//심을 농작물 버튼 배열 
	private boolean crop[];					//어떤 농작물을 클릭했는지 여부를 true, false로 저장하는 배열(0:딸기, 1:수박, 2: 상추, 3: 옥수수, 4: 호박, 5: 감자)

	private Timer timer;					//농작물 인스턴스의 시간을 재는 Timer 인스턴스
	private Thread th2;						//timer의 쓰레드
	
	private String cropName;				//농작물의 이름을 저장하는 변수
	
	public boolean flag = false;
	
	//CropPanel Class의 생성자
	public CropPanel(){
		btnz = new JButton[6];						//농작물 버튼 배열 인스턴스 생성(6종류)
		crop = new boolean[6];						//농작물 클릭여부를 저장하는 배열 생성
		
		timer = new Timer();						//Timer인스턴스 생성
		
		setLayout(new GridLayout(3, 2));			//3x2 Grid Layout으로 set (6칸)
		
		//3종류의 농작물 이미지를 Icon 인스턴스를 생성해 저장
		Icon ic1 = new ImageIcon("딸기1.png");
		Icon ic2 = new ImageIcon("수박2.png");
		Icon ic3 = new ImageIcon("상추2.png");
		Icon ic4 = new ImageIcon("옥수수2.png");
		Icon ic5 = new ImageIcon("호박2.png");
		Icon ic6 = new ImageIcon("감자2.png");
		
		//각 버튼배열 요소에 대해 이름과 이미지를 붙여 버튼 생성
		btnz[0] = new JButton("딸기",ic1);
		btnz[1] = new JButton("수박",ic2);
		btnz[2] = new JButton("상추",ic3);
		btnz[3] = new JButton("옥수수",ic4);
		btnz[4] = new JButton("호박",ic5);
		btnz[5] = new JButton("감자",ic6);
		
		for(int i=0;i<btnz.length;i++)
		{
			add(btnz[i]);						//각 버튼을 화면에 추가
			btnz[i].addActionListener(this);	//각 버튼을 액션리스너에 추가
		}
	}
	
	//crop배열의 값을 set하는 메소드
	public void setCrop(String cropName)
	{
		//cropName 파라미터를 통해 어느 index를 true로해야할지 판단
		if(cropName.equals("딸기"))
			crop[0] = true;
		else if(cropName.equals("수박"))
			crop[1] = true;
		else if(cropName.equals("상추"))
			crop[2] = true;
		else if(cropName.equals("옥수수"))
			crop[3] = true;
		else if(cropName.equals("호박"))
			crop[4] = true;
		else if(cropName.equals("감자"))
			crop[5] = true;
	}
	
	//crop배열의 값을 get하는 메소드
	public boolean getCrop(int i)
	{
		return crop[i];	//i 파라미터를 index로 해서 해당 index의 crop을 리턴
	}
	
	//농작물의 이름을 get하는 메소드
	public String getCropName()
	{
		return cropName;
	}
	
	//timer인스턴스를 get하는 메소드
	public Timer getTimer()
	{
		return timer;
	}
	
	//액션리스너 구현부분
	public void actionPerformed(ActionEvent e){
		for(int i=0;i<btnz.length;i++){
			if(e.getSource() == btnz[i])
			{
				//버튼을 클릭했을 때, 어떤 농작물을 심는지 메시지를 출력하고 해당 작물의 이름을 통해 crop배열과 cropName을 set
				JOptionPane.showMessageDialog(btnz[i], String.format("%s를 심습니다.", e.getActionCommand()));
				setCrop(btnz[i].getText());
				cropName = btnz[i].getText();
				
				/*해당 버튼에 대한 timer 스레드 생성.
				 *  CropPanel 클래스의 인스턴스는 FarmPanel 클래스에서 생성.
				 *  최종적으로는 FarmPanel의 심어지는 작물에 대한 timer 스레드
				 */
				th2 = new Thread(timer);
				th2.start();
				
				this.setVisible(false);	//액션이 완료되면 창을 닫음
				break;
			}
		}
	}
	
	//스레드 시작시 실행되는 메소드
	public void run(){
		this.setTitle("작물을 심자");
		this.setVisible(true);
		this.setSize(400,600);
	}
}
