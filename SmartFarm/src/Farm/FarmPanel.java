package Farm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/*밭을 화면에 표시하고 CropPanel 클래스의 인스턴스를 생성하는 클래스
 * JFrame 클래스 상속, ActionListener, Runnable 인터페이스 구현
 */
public class FarmPanel extends JFrame implements ActionListener, Runnable {
	
	static private int cropResult[] = new int[6];	//수확한 농작물의 수를 저장하는 배열(0:딸기, 1:수박, 2:상추, 3: 옥수수, 4: 호박, 5: 감자)
	String id;
	int berry;
	int melon;
	int corn;
	int potato;
	int lettuce;
	int pum;
	private JButton exit;
	private JButton btnz[];							//밭을 배치하는 버튼 배열
	private JLabel remainTime[];					//농작물이 심어지고 수확까지or상할 때까지 남은 시간을 표시하는 라벨 배열
	private JLabel result;							//수확한 농작물의 총 갯수를 저장하는 라벨
	/* pan1:pan2 배열의 각 요소를 저장하는 패널
	   pan2:btnz와 remainTime을 저장하는 패널 배열
	   pan3:result를 저장하는 패널
	 									*/
	private Panel pan1, pan2[], pan3;
	
	private CropPanel cp[];							//밭 버튼을 클릭할 때 심을 농작물들을 표시하는 CropPanel 클래스 배열
	private Thread th1;								//cp의 스레드
	
	private Icon ic[] = new Icon[15];				//밭의 이미지를 저장하는 Icon 배열(비어있는 밭, 농작물 심은 이미지, 수확대기 이미지, 상한 농작물 이미지)
	
	//FarmPanel class의 생성자
	public FarmPanel(String id){
		this.id = id;
		cp = new CropPanel[16];					    //CropPanel 인스턴스 배열 생성 
		btnz = new JButton[16];						//밭 버튼 배열 인스턴스 생성  버튼이며, 심는 농작물의 상태에 따라 이미지가 표시됨. 초기상태는 위와 같은 비어있는 밭 이미지로 설정
		remainTime = new JLabel[16];				//남은시간 배열 인스턴스 생성 라벨이며, 농작물의 수확까지 남은시간, 상할 때까지 남은시간이 표시됩니다. 초기상태는 작물을 심어주세요.
		
		pan1 = new Panel(new GridLayout(4,4));		//pan1을 4x4 Grid Layout set
		pan2 = new Panel[16];						//pan2 패널 인스턴스 16개 생성
		pan3 = new Panel(new FlowLayout());		//pan3를 Flow Layout set
		
		//Icon 인스턴스 생성
		ic[0] = new ImageIcon("밭.png");			//비어있는 밭
		ic[1] = new ImageIcon("딸기성장.png");	//딸기 키우는중
		ic[2] = new ImageIcon("딸기2.png");		//딸기 수확대기
		ic[3] = new ImageIcon("수박성장.png");	//수박 키우는중
		ic[4] = new ImageIcon("수박1.png");		//수박 수확대기
		ic[5] = new ImageIcon("상추성장.png");	//상추 키우는중
		ic[6] = new ImageIcon("상추1.png");		//상추 수확대기
		ic[7] = new ImageIcon("옥수수성장.png");	//옥수수 키우는중
		ic[8] = new ImageIcon("옥수수1.png");		//옥수수 수확대기
		ic[9] = new ImageIcon("호박성장.png");	//호박 키우는중
		ic[10] = new ImageIcon("호박1.png");		//옥수수 수확대기
		ic[11] = new ImageIcon("감자성장.png");	//감자 키우는중
		ic[12] = new ImageIcon("감자1.png");		//감자 수확대기
		ic[13] = new ImageIcon("돼지.png");		//밭망침
		ic[14] = new ImageIcon("땅.png");		//밭망침
		
		for(int i=0;i<btnz.length;i++)
		{
			cp[i] = new CropPanel();						//cp의 각 인스턴스를 생성
			pan2[i] = new Panel(new BorderLayout());		//pan2의 각 요소들을 Border Layout으로 set
			btnz[i] = new JButton("밭" + (i+1),ic[0]);		//버튼요소들에 밭이름과 이미지를 붙여 버튼 생성 // 밭을 누르면 경고창이뜬다.
			btnz[i].addActionListener(this);				//각 버튼들을 액션리스너에 추가
			remainTime[i] = new JLabel("작물을 심어주세요.");	//남은 시간의 각 라벨들 생성(초기 표시 문자열 : 작물을 심어주세요.)
			pan2[i].add(btnz[i], BorderLayout.CENTER);		//밭 버튼을 가운데 추가 없으면 사진 안뜸
			pan2[i].add(remainTime[i], BorderLayout.SOUTH);	//남은시간 라벨을 아래에 추가 없으면 밑에 시간이 뜨지않는다.
			pan1.add(pan2[i]);								//Border Layout으로 set된 pan2를 pan1에 추가
		}
		
		result = new JLabel(getCropResult());				//result 라벨 생성
		exit = new JButton("게임종료");
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DBBean db = DBBean.getInstance();
				db.updateLog(id, berry, melon, pum, potato, lettuce, corn);
			}
		});
		pan3.add(exit);										//pan3에 exit 라벨 추가
		pan3.add(result);									//pan3에 result 라벨 추가
		
		setLayout(new BorderLayout());						//전체화면을 Border Layout set
		add(pan1, BorderLayout.CENTER);						//pan2을 중앙에 추가
		add(pan3, BorderLayout.SOUTH);						//pan3을 아래쪽에 추가
	}
	
	//라벨이며, 수확한 농작물의 총 개수를 표시합니다.
	public String getCropResult() 
	{
		return "딸기 : " + cropResult[0] + "개," + " " + "수박: " + cropResult[1] + "개," + " " + "상추: " + cropResult[2] + "개," + " "
				+ "옥수수: " + cropResult[3] + "개," + " " + "호박: " + cropResult[4] + "개," + " " + "감자: " + cropResult[5] + "개" ;
	}
	
	//액션리스너 구현부분
	public void actionPerformed(ActionEvent e){
		for(int i=0;i<btnz.length;i++){
					// 버튼을 클릭했을 때
					if(e.getSource() == btnz[i])
					{
						//해당버튼이 농작물을 이미 키우는 중일 때
						if((btnz[i].getIcon() == ic[1]) || (btnz[i].getIcon() == ic[3]) || (btnz[i].getIcon() == ic[5]) || (btnz[i].getIcon() == ic[7]) 
								|| (btnz[i].getIcon() == ic[9]))
						{
							//생산중이라는 메시지를 출력(이미 키우는 농작물에 중복으로 농작물을 심는 것을 방지)
							JOptionPane.showMessageDialog(btnz[i], String.format("%s은/는 농작물 생산중입니다.", e.getActionCommand()));
							break;
						}
						
						for(int j=2;j<13;j+=2) // 수확 3이면 딸기도 수확 x j가 2부터 시작하면 딸기 수확할 수 있음 j=2 라는 의미 ic[2]가 딸기사진이니까 13까지 하면 감자까지 수확 가능
						{
							//해당버튼이 수확대기 상태일 때
							if(btnz[i].getIcon() == ic[j])
							{
								//농작물을 수확한다는 메시지 출력
								JOptionPane.showMessageDialog(btnz[i], String.format("%s의 %s을 수확합니다.", e.getActionCommand(), cp[i].getCropName()));
								cp[i] = new CropPanel();			//해당 밭의 cp 인스턴스 재생성(초기화)
								cropResult[j/2-1]++;				//수확한 농작물의 갯수 증가
								result.setText(getCropResult());	//result 라벨을 새로 set(변경된 값 반영)
								remainTime[i].setText("비어있습니다.");//remainTime 라벨을 비어있음으로 set
								return;								
							}
						}
						//해당 버튼의 농작물이 상한 상태일 때
						if(btnz[i].getIcon() == ic[13]) // 사진 ic[13]이다.
						{
							//밭을 갈아 엎는다는 메시지 출력
							JOptionPane.showMessageDialog(btnz[i], String.format("%s을/를 갈아 엎었습니다.", e.getActionCommand()));
							cp[i] = new CropPanel();			//해당 밭의 cp 인스턴스 재생성(초기화)
							remainTime[i].setText("작물을 심어주세요.");		//remainTime 라벨을 비어있음으로 set
							break;
						}
						//해당 버튼이 비어있는 밭일 때, 밭 이름과 작물을 심어주세요라는 메시지 출력
						JOptionPane.showMessageDialog(btnz[i], String.format("%s: 작물을 심어주세요.", e.getActionCommand()));
						th1 = new Thread(cp[i]);	//해당 밭의 cp인스턴스에 대해 스레드 생성
						th1.start();				//스레드 시작
						break;
					}
				}
			}
	
	//스레드 시작시 실행되는 메소드
	public void run(){
		this.setTitle("농작물");
		this.setVisible(true);
		this.setSize(850,1000);
		this.setLocation(300, 0); // 컴포넌트 위치지정
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//스레드가 종료될 때까지 계속 반복
		while(true)
		{
			/*각 밭에 대한 농작물의 상태를 나타내기 위한 for문
			 * 모든 농작물을 처음 심고 10초 동안 생산 중 상태 이며
			 * 10초 후에는 수확 대기 상태, 20초 후에는 작물이 상한다.
			 */
			for(int i=0;i<cp.length;i++)
			{
					//밭 i의 심은 농작물이 딸기인 경우
					if(cp[i].getCrop(0) == true)
					{
						//cp i의 timer값이 10초이상인 경우(10~20)
						if(cp[i].getTimer().getTime() > 10)
						{
							//버튼 이미지를 생산중인 이미지로
							btnz[i].setIcon(ic[1]);
							//수확까지 남은 시간 표시
							remainTime[i].setText("수확까지 남은시간 : "+ (cp[i].getTimer().getTime()-10));
						}
						//cp i의 timer값이 0~10인 경우
						else if((0 < cp[i].getTimer().getTime()) && (cp[i].getTimer().getTime() <= 10))
						{
							//버튼 이미지를 수확대기 이미지로
							btnz[i].setIcon(ic[2]);
							//상할 때까지 남은 시간 표시
							remainTime[i].setText("상할 때까지 남은시간 : "+ cp[i].getTimer().getTime());
						}
						//cp i의 timer값이 0이하인 경우
						else if(cp[i].getTimer().getTime() <= 0)
						{
							//버튼 이미지를 상한 농작물 이미지로
							btnz[i].setIcon(ic[13]);
							//remainTime 라벨에 작물 상함으로 set
							remainTime[i].setText("작물 상함.");
						}
					}
					
					//밭 i의 심은 농작물이 수박인 경우
					else if(cp[i].getCrop(1) == true)
					{
						if(cp[i].getTimer().getTime() > 10)
						{
							btnz[i].setIcon(ic[3]);
							remainTime[i].setText("수확까지 남은시간 : "+ (cp[i].getTimer().getTime()-10));
						}
						else if((0 < cp[i].getTimer().getTime()) && (cp[i].getTimer().getTime() <= 10))
						{
							btnz[i].setIcon(ic[4]);
							remainTime[i].setText("상할 때까지 남은시간 : "+ cp[i].getTimer().getTime());
						}
						else if(cp[i].getTimer().getTime() <= 0)
						{
							btnz[i].setIcon(ic[13]);
							remainTime[i].setText("작물 상함.");
						}
					}
					//밭 i의 심은 농작물이 상추인 경우
					else if(cp[i].getCrop(2) == true)
					{
						if(cp[i].getTimer().getTime() > 10)
						{
							btnz[i].setIcon(ic[5]);
							remainTime[i].setText("수확까지 남은시간 : "+ (cp[i].getTimer().getTime()-10));
						}
						else if((0 < cp[i].getTimer().getTime()) && (cp[i].getTimer().getTime() <= 10))
						{
							btnz[i].setIcon(ic[6]);
							remainTime[i].setText("상할 때까지 남은시간 : "+ cp[i].getTimer().getTime());
						}
						else if(cp[i].getTimer().getTime() <= 0)
						{
							btnz[i].setIcon(ic[13]);
							remainTime[i].setText("작물 상함.");
						}
					}
					//밭 i의 심은 농작물이 옥수수인 경우
					else if(cp[i].getCrop(3) == true)
					{
						if(cp[i].getTimer().getTime() > 10)
						{
							btnz[i].setIcon(ic[7]);
							remainTime[i].setText("수확까지 남은시간 : "+ (cp[i].getTimer().getTime()-10));
						}
						else if((0 < cp[i].getTimer().getTime()) && (cp[i].getTimer().getTime() <= 10))
						{
							btnz[i].setIcon(ic[8]);
							remainTime[i].setText("상할 때까지 남은시간 : "+ cp[i].getTimer().getTime());
						}
						else if(cp[i].getTimer().getTime() <= 0)
						{
							btnz[i].setIcon(ic[13]);
							remainTime[i].setText("작물 상함.");
						}
					}
					//밭 i의 심은 농작물이 호박인 경우
					else if(cp[i].getCrop(4) == true)
					{
						if(cp[i].getTimer().getTime() > 10)
						{
							btnz[i].setIcon(ic[9]);
							remainTime[i].setText("수확까지 남은시간 : "+ (cp[i].getTimer().getTime()-10));
						}
						else if((0 < cp[i].getTimer().getTime()) && (cp[i].getTimer().getTime() <= 10))
						{
							btnz[i].setIcon(ic[10]);
							remainTime[i].setText("상할 때까지 남은시간 : "+ cp[i].getTimer().getTime());
						}
						else if(cp[i].getTimer().getTime() <= 0)
						{
							btnz[i].setIcon(ic[13]);
							remainTime[i].setText("작물 상함.");
						}
					}
					//밭 i의 심은 농작물이 감자인 경우
					else if(cp[i].getCrop(5) == true)
					{
						if(cp[i].getTimer().getTime() > 10)
						{
							btnz[i].setIcon(ic[11]);
							remainTime[i].setText("수확까지 남은시간 : "+ (cp[i].getTimer().getTime()-10));
						}
						else if((0 < cp[i].getTimer().getTime()) && (cp[i].getTimer().getTime() <= 10))
						{
							btnz[i].setIcon(ic[12]);
							remainTime[i].setText("상할 때까지 남은시간 : "+ cp[i].getTimer().getTime());
						}
						else if(cp[i].getTimer().getTime() <= 0)
						{
							btnz[i].setIcon(ic[13]);
							remainTime[i].setText("작물 상함.");
						}
					}
					//밭 i의 심은 농작물이 아무것도 없는 경우
					else
						//해당 버튼을 비어있는 이미지로
							btnz[i].setIcon(ic[0]);
					
						
				}
				
				
			}
		}
	}
