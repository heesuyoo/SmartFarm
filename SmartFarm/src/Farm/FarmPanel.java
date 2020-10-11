package Farm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/*���� ȭ�鿡 ǥ���ϰ� CropPanel Ŭ������ �ν��Ͻ��� �����ϴ� Ŭ����
 * JFrame Ŭ���� ���, ActionListener, Runnable �������̽� ����
 */
public class FarmPanel extends JFrame implements ActionListener, Runnable {
	
	static private int cropResult[] = new int[6];	//��Ȯ�� ���۹��� ���� �����ϴ� �迭(0:����, 1:����, 2:����, 3: ������, 4: ȣ��, 5: ����)
	String id;
	int berry;
	int melon;
	int corn;
	int potato;
	int lettuce;
	int pum;
	private JButton exit;
	private JButton btnz[];							//���� ��ġ�ϴ� ��ư �迭
	private JLabel remainTime[];					//���۹��� �ɾ����� ��Ȯ����or���� ������ ���� �ð��� ǥ���ϴ� �� �迭
	private JLabel result;							//��Ȯ�� ���۹��� �� ������ �����ϴ� ��
	/* pan1:pan2 �迭�� �� ��Ҹ� �����ϴ� �г�
	   pan2:btnz�� remainTime�� �����ϴ� �г� �迭
	   pan3:result�� �����ϴ� �г�
	 									*/
	private Panel pan1, pan2[], pan3;
	
	private CropPanel cp[];							//�� ��ư�� Ŭ���� �� ���� ���۹����� ǥ���ϴ� CropPanel Ŭ���� �迭
	private Thread th1;								//cp�� ������
	
	private Icon ic[] = new Icon[15];				//���� �̹����� �����ϴ� Icon �迭(����ִ� ��, ���۹� ���� �̹���, ��Ȯ��� �̹���, ���� ���۹� �̹���)
	
	//FarmPanel class�� ������
	public FarmPanel(String id){
		this.id = id;
		cp = new CropPanel[16];					    //CropPanel �ν��Ͻ� �迭 ���� 
		btnz = new JButton[16];						//�� ��ư �迭 �ν��Ͻ� ����  ��ư�̸�, �ɴ� ���۹��� ���¿� ���� �̹����� ǥ�õ�. �ʱ���´� ���� ���� ����ִ� �� �̹����� ����
		remainTime = new JLabel[16];				//�����ð� �迭 �ν��Ͻ� ���� ���̸�, ���۹��� ��Ȯ���� �����ð�, ���� ������ �����ð��� ǥ�õ˴ϴ�. �ʱ���´� �۹��� �ɾ��ּ���.
		
		pan1 = new Panel(new GridLayout(4,4));		//pan1�� 4x4 Grid Layout set
		pan2 = new Panel[16];						//pan2 �г� �ν��Ͻ� 16�� ����
		pan3 = new Panel(new FlowLayout());		//pan3�� Flow Layout set
		
		//Icon �ν��Ͻ� ����
		ic[0] = new ImageIcon("��.png");			//����ִ� ��
		ic[1] = new ImageIcon("���⼺��.png");	//���� Ű�����
		ic[2] = new ImageIcon("����2.png");		//���� ��Ȯ���
		ic[3] = new ImageIcon("���ڼ���.png");	//���� Ű�����
		ic[4] = new ImageIcon("����1.png");		//���� ��Ȯ���
		ic[5] = new ImageIcon("���߼���.png");	//���� Ű�����
		ic[6] = new ImageIcon("����1.png");		//���� ��Ȯ���
		ic[7] = new ImageIcon("����������.png");	//������ Ű�����
		ic[8] = new ImageIcon("������1.png");		//������ ��Ȯ���
		ic[9] = new ImageIcon("ȣ�ڼ���.png");	//ȣ�� Ű�����
		ic[10] = new ImageIcon("ȣ��1.png");		//������ ��Ȯ���
		ic[11] = new ImageIcon("���ڼ���.png");	//���� Ű�����
		ic[12] = new ImageIcon("����1.png");		//���� ��Ȯ���
		ic[13] = new ImageIcon("����.png");		//���ħ
		ic[14] = new ImageIcon("��.png");		//���ħ
		
		for(int i=0;i<btnz.length;i++)
		{
			cp[i] = new CropPanel();						//cp�� �� �ν��Ͻ��� ����
			pan2[i] = new Panel(new BorderLayout());		//pan2�� �� ��ҵ��� Border Layout���� set
			btnz[i] = new JButton("��" + (i+1),ic[0]);		//��ư��ҵ鿡 ���̸��� �̹����� �ٿ� ��ư ���� // ���� ������ ���â�̶��.
			btnz[i].addActionListener(this);				//�� ��ư���� �׼Ǹ����ʿ� �߰�
			remainTime[i] = new JLabel("�۹��� �ɾ��ּ���.");	//���� �ð��� �� �󺧵� ����(�ʱ� ǥ�� ���ڿ� : �۹��� �ɾ��ּ���.)
			pan2[i].add(btnz[i], BorderLayout.CENTER);		//�� ��ư�� ��� �߰� ������ ���� �ȶ�
			pan2[i].add(remainTime[i], BorderLayout.SOUTH);	//�����ð� ���� �Ʒ��� �߰� ������ �ؿ� �ð��� �����ʴ´�.
			pan1.add(pan2[i]);								//Border Layout���� set�� pan2�� pan1�� �߰�
		}
		
		result = new JLabel(getCropResult());				//result �� ����
		exit = new JButton("��������");
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DBBean db = DBBean.getInstance();
				db.updateLog(id, berry, melon, pum, potato, lettuce, corn);
			}
		});
		pan3.add(exit);										//pan3�� exit �� �߰�
		pan3.add(result);									//pan3�� result �� �߰�
		
		setLayout(new BorderLayout());						//��üȭ���� Border Layout set
		add(pan1, BorderLayout.CENTER);						//pan2�� �߾ӿ� �߰�
		add(pan3, BorderLayout.SOUTH);						//pan3�� �Ʒ��ʿ� �߰�
	}
	
	//���̸�, ��Ȯ�� ���۹��� �� ������ ǥ���մϴ�.
	public String getCropResult() 
	{
		return "���� : " + cropResult[0] + "��," + " " + "����: " + cropResult[1] + "��," + " " + "����: " + cropResult[2] + "��," + " "
				+ "������: " + cropResult[3] + "��," + " " + "ȣ��: " + cropResult[4] + "��," + " " + "����: " + cropResult[5] + "��" ;
	}
	
	//�׼Ǹ����� �����κ�
	public void actionPerformed(ActionEvent e){
		for(int i=0;i<btnz.length;i++){
					// ��ư�� Ŭ������ ��
					if(e.getSource() == btnz[i])
					{
						//�ش��ư�� ���۹��� �̹� Ű��� ���� ��
						if((btnz[i].getIcon() == ic[1]) || (btnz[i].getIcon() == ic[3]) || (btnz[i].getIcon() == ic[5]) || (btnz[i].getIcon() == ic[7]) 
								|| (btnz[i].getIcon() == ic[9]))
						{
							//�������̶�� �޽����� ���(�̹� Ű��� ���۹��� �ߺ����� ���۹��� �ɴ� ���� ����)
							JOptionPane.showMessageDialog(btnz[i], String.format("%s��/�� ���۹� �������Դϴ�.", e.getActionCommand()));
							break;
						}
						
						for(int j=2;j<13;j+=2) // ��Ȯ 3�̸� ���⵵ ��Ȯ x j�� 2���� �����ϸ� ���� ��Ȯ�� �� ���� j=2 ��� �ǹ� ic[2]�� ��������̴ϱ� 13���� �ϸ� ���ڱ��� ��Ȯ ����
						{
							//�ش��ư�� ��Ȯ��� ������ ��
							if(btnz[i].getIcon() == ic[j])
							{
								//���۹��� ��Ȯ�Ѵٴ� �޽��� ���
								JOptionPane.showMessageDialog(btnz[i], String.format("%s�� %s�� ��Ȯ�մϴ�.", e.getActionCommand(), cp[i].getCropName()));
								cp[i] = new CropPanel();			//�ش� ���� cp �ν��Ͻ� �����(�ʱ�ȭ)
								cropResult[j/2-1]++;				//��Ȯ�� ���۹��� ���� ����
								result.setText(getCropResult());	//result ���� ���� set(����� �� �ݿ�)
								remainTime[i].setText("����ֽ��ϴ�.");//remainTime ���� ����������� set
								return;								
							}
						}
						//�ش� ��ư�� ���۹��� ���� ������ ��
						if(btnz[i].getIcon() == ic[13]) // ���� ic[13]�̴�.
						{
							//���� ���� ���´ٴ� �޽��� ���
							JOptionPane.showMessageDialog(btnz[i], String.format("%s��/�� ���� �������ϴ�.", e.getActionCommand()));
							cp[i] = new CropPanel();			//�ش� ���� cp �ν��Ͻ� �����(�ʱ�ȭ)
							remainTime[i].setText("�۹��� �ɾ��ּ���.");		//remainTime ���� ����������� set
							break;
						}
						//�ش� ��ư�� ����ִ� ���� ��, �� �̸��� �۹��� �ɾ��ּ����� �޽��� ���
						JOptionPane.showMessageDialog(btnz[i], String.format("%s: �۹��� �ɾ��ּ���.", e.getActionCommand()));
						th1 = new Thread(cp[i]);	//�ش� ���� cp�ν��Ͻ��� ���� ������ ����
						th1.start();				//������ ����
						break;
					}
				}
			}
	
	//������ ���۽� ����Ǵ� �޼ҵ�
	public void run(){
		this.setTitle("���۹�");
		this.setVisible(true);
		this.setSize(850,1000);
		this.setLocation(300, 0); // ������Ʈ ��ġ����
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//�����尡 ����� ������ ��� �ݺ�
		while(true)
		{
			/*�� �翡 ���� ���۹��� ���¸� ��Ÿ���� ���� for��
			 * ��� ���۹��� ó�� �ɰ� 10�� ���� ���� �� ���� �̸�
			 * 10�� �Ŀ��� ��Ȯ ��� ����, 20�� �Ŀ��� �۹��� ���Ѵ�.
			 */
			for(int i=0;i<cp.length;i++)
			{
					//�� i�� ���� ���۹��� ������ ���
					if(cp[i].getCrop(0) == true)
					{
						//cp i�� timer���� 10���̻��� ���(10~20)
						if(cp[i].getTimer().getTime() > 10)
						{
							//��ư �̹����� �������� �̹�����
							btnz[i].setIcon(ic[1]);
							//��Ȯ���� ���� �ð� ǥ��
							remainTime[i].setText("��Ȯ���� �����ð� : "+ (cp[i].getTimer().getTime()-10));
						}
						//cp i�� timer���� 0~10�� ���
						else if((0 < cp[i].getTimer().getTime()) && (cp[i].getTimer().getTime() <= 10))
						{
							//��ư �̹����� ��Ȯ��� �̹�����
							btnz[i].setIcon(ic[2]);
							//���� ������ ���� �ð� ǥ��
							remainTime[i].setText("���� ������ �����ð� : "+ cp[i].getTimer().getTime());
						}
						//cp i�� timer���� 0������ ���
						else if(cp[i].getTimer().getTime() <= 0)
						{
							//��ư �̹����� ���� ���۹� �̹�����
							btnz[i].setIcon(ic[13]);
							//remainTime �󺧿� �۹� �������� set
							remainTime[i].setText("�۹� ����.");
						}
					}
					
					//�� i�� ���� ���۹��� ������ ���
					else if(cp[i].getCrop(1) == true)
					{
						if(cp[i].getTimer().getTime() > 10)
						{
							btnz[i].setIcon(ic[3]);
							remainTime[i].setText("��Ȯ���� �����ð� : "+ (cp[i].getTimer().getTime()-10));
						}
						else if((0 < cp[i].getTimer().getTime()) && (cp[i].getTimer().getTime() <= 10))
						{
							btnz[i].setIcon(ic[4]);
							remainTime[i].setText("���� ������ �����ð� : "+ cp[i].getTimer().getTime());
						}
						else if(cp[i].getTimer().getTime() <= 0)
						{
							btnz[i].setIcon(ic[13]);
							remainTime[i].setText("�۹� ����.");
						}
					}
					//�� i�� ���� ���۹��� ������ ���
					else if(cp[i].getCrop(2) == true)
					{
						if(cp[i].getTimer().getTime() > 10)
						{
							btnz[i].setIcon(ic[5]);
							remainTime[i].setText("��Ȯ���� �����ð� : "+ (cp[i].getTimer().getTime()-10));
						}
						else if((0 < cp[i].getTimer().getTime()) && (cp[i].getTimer().getTime() <= 10))
						{
							btnz[i].setIcon(ic[6]);
							remainTime[i].setText("���� ������ �����ð� : "+ cp[i].getTimer().getTime());
						}
						else if(cp[i].getTimer().getTime() <= 0)
						{
							btnz[i].setIcon(ic[13]);
							remainTime[i].setText("�۹� ����.");
						}
					}
					//�� i�� ���� ���۹��� �������� ���
					else if(cp[i].getCrop(3) == true)
					{
						if(cp[i].getTimer().getTime() > 10)
						{
							btnz[i].setIcon(ic[7]);
							remainTime[i].setText("��Ȯ���� �����ð� : "+ (cp[i].getTimer().getTime()-10));
						}
						else if((0 < cp[i].getTimer().getTime()) && (cp[i].getTimer().getTime() <= 10))
						{
							btnz[i].setIcon(ic[8]);
							remainTime[i].setText("���� ������ �����ð� : "+ cp[i].getTimer().getTime());
						}
						else if(cp[i].getTimer().getTime() <= 0)
						{
							btnz[i].setIcon(ic[13]);
							remainTime[i].setText("�۹� ����.");
						}
					}
					//�� i�� ���� ���۹��� ȣ���� ���
					else if(cp[i].getCrop(4) == true)
					{
						if(cp[i].getTimer().getTime() > 10)
						{
							btnz[i].setIcon(ic[9]);
							remainTime[i].setText("��Ȯ���� �����ð� : "+ (cp[i].getTimer().getTime()-10));
						}
						else if((0 < cp[i].getTimer().getTime()) && (cp[i].getTimer().getTime() <= 10))
						{
							btnz[i].setIcon(ic[10]);
							remainTime[i].setText("���� ������ �����ð� : "+ cp[i].getTimer().getTime());
						}
						else if(cp[i].getTimer().getTime() <= 0)
						{
							btnz[i].setIcon(ic[13]);
							remainTime[i].setText("�۹� ����.");
						}
					}
					//�� i�� ���� ���۹��� ������ ���
					else if(cp[i].getCrop(5) == true)
					{
						if(cp[i].getTimer().getTime() > 10)
						{
							btnz[i].setIcon(ic[11]);
							remainTime[i].setText("��Ȯ���� �����ð� : "+ (cp[i].getTimer().getTime()-10));
						}
						else if((0 < cp[i].getTimer().getTime()) && (cp[i].getTimer().getTime() <= 10))
						{
							btnz[i].setIcon(ic[12]);
							remainTime[i].setText("���� ������ �����ð� : "+ cp[i].getTimer().getTime());
						}
						else if(cp[i].getTimer().getTime() <= 0)
						{
							btnz[i].setIcon(ic[13]);
							remainTime[i].setText("�۹� ����.");
						}
					}
					//�� i�� ���� ���۹��� �ƹ��͵� ���� ���
					else
						//�ش� ��ư�� ����ִ� �̹�����
							btnz[i].setIcon(ic[0]);
					
						
				}
				
				
			}
		}
	}
