package Farm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class Main extends JFrame {
	public Main(String id){
		FarmPanel fp = new FarmPanel(id);	//FarmPanel�ν��Ͻ� ����
		Thread th1 = new Thread(fp);	//FarmPanel�ν��Ͻ��� ���� ������ ����
		th1.start();		//������ ����
		
	}
}