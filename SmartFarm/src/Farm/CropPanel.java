package Farm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/*���� ���۹��� ȭ�鿡 ǥ���ϰ� FarmPanel�� �����ϴ� Ŭ���� CropPanel
 * JFrame Ŭ���� ���, ActionListener, Runnable �������̽� ����
 */
public class CropPanel extends JFrame implements ActionListener, Runnable {
	private JButton btnz[];					//���� ���۹� ��ư �迭 
	private boolean crop[];					//� ���۹��� Ŭ���ߴ��� ���θ� true, false�� �����ϴ� �迭(0:����, 1:����, 2: ����, 3: ������, 4: ȣ��, 5: ����)

	private Timer timer;					//���۹� �ν��Ͻ��� �ð��� ��� Timer �ν��Ͻ�
	private Thread th2;						//timer�� ������
	
	private String cropName;				//���۹��� �̸��� �����ϴ� ����
	
	public boolean flag = false;
	
	//CropPanel Class�� ������
	public CropPanel(){
		btnz = new JButton[6];						//���۹� ��ư �迭 �ν��Ͻ� ����(6����)
		crop = new boolean[6];						//���۹� Ŭ�����θ� �����ϴ� �迭 ����
		
		timer = new Timer();						//Timer�ν��Ͻ� ����
		
		setLayout(new GridLayout(3, 2));			//3x2 Grid Layout���� set (6ĭ)
		
		//3������ ���۹� �̹����� Icon �ν��Ͻ��� ������ ����
		Icon ic1 = new ImageIcon("����1.png");
		Icon ic2 = new ImageIcon("����2.png");
		Icon ic3 = new ImageIcon("����2.png");
		Icon ic4 = new ImageIcon("������2.png");
		Icon ic5 = new ImageIcon("ȣ��2.png");
		Icon ic6 = new ImageIcon("����2.png");
		
		//�� ��ư�迭 ��ҿ� ���� �̸��� �̹����� �ٿ� ��ư ����
		btnz[0] = new JButton("����",ic1);
		btnz[1] = new JButton("����",ic2);
		btnz[2] = new JButton("����",ic3);
		btnz[3] = new JButton("������",ic4);
		btnz[4] = new JButton("ȣ��",ic5);
		btnz[5] = new JButton("����",ic6);
		
		for(int i=0;i<btnz.length;i++)
		{
			add(btnz[i]);						//�� ��ư�� ȭ�鿡 �߰�
			btnz[i].addActionListener(this);	//�� ��ư�� �׼Ǹ����ʿ� �߰�
		}
	}
	
	//crop�迭�� ���� set�ϴ� �޼ҵ�
	public void setCrop(String cropName)
	{
		//cropName �Ķ���͸� ���� ��� index�� true���ؾ����� �Ǵ�
		if(cropName.equals("����"))
			crop[0] = true;
		else if(cropName.equals("����"))
			crop[1] = true;
		else if(cropName.equals("����"))
			crop[2] = true;
		else if(cropName.equals("������"))
			crop[3] = true;
		else if(cropName.equals("ȣ��"))
			crop[4] = true;
		else if(cropName.equals("����"))
			crop[5] = true;
	}
	
	//crop�迭�� ���� get�ϴ� �޼ҵ�
	public boolean getCrop(int i)
	{
		return crop[i];	//i �Ķ���͸� index�� �ؼ� �ش� index�� crop�� ����
	}
	
	//���۹��� �̸��� get�ϴ� �޼ҵ�
	public String getCropName()
	{
		return cropName;
	}
	
	//timer�ν��Ͻ��� get�ϴ� �޼ҵ�
	public Timer getTimer()
	{
		return timer;
	}
	
	//�׼Ǹ����� �����κ�
	public void actionPerformed(ActionEvent e){
		for(int i=0;i<btnz.length;i++){
			if(e.getSource() == btnz[i])
			{
				//��ư�� Ŭ������ ��, � ���۹��� �ɴ��� �޽����� ����ϰ� �ش� �۹��� �̸��� ���� crop�迭�� cropName�� set
				JOptionPane.showMessageDialog(btnz[i], String.format("%s�� �ɽ��ϴ�.", e.getActionCommand()));
				setCrop(btnz[i].getText());
				cropName = btnz[i].getText();
				
				/*�ش� ��ư�� ���� timer ������ ����.
				 *  CropPanel Ŭ������ �ν��Ͻ��� FarmPanel Ŭ�������� ����.
				 *  ���������δ� FarmPanel�� �ɾ����� �۹��� ���� timer ������
				 */
				th2 = new Thread(timer);
				th2.start();
				
				this.setVisible(false);	//�׼��� �Ϸ�Ǹ� â�� ����
				break;
			}
		}
	}
	
	//������ ���۽� ����Ǵ� �޼ҵ�
	public void run(){
		this.setTitle("�۹��� ����");
		this.setVisible(true);
		this.setSize(400,600);
	}
}
