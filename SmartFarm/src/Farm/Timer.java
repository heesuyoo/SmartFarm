package Farm;

/*���۹��� ��Ȯ�ϱ���� or ���ϱ���� ���� �ð��� ǥ���ϴ� Ŭ���� Timer
 * Runnable �������̽� ����
 */
public class Timer implements Runnable {
	private int time = 20;	//���� �ð� ����. �ʱⰪ 20
	
	//time�� get�ϴ� �޼ҵ�
	public int getTime()
	{
		return time;
	}
	
	//������ ���۽� ����Ǵ� �޼ҵ�
	public void run(){
		//�����尡 ����� ������ �ݺ�
		while(true){
			try{
				Thread.sleep(1000);	//1�� ���� ���
			}
			//���ͷ�Ʈ ���� �߻��� ����ó�� ����
			catch(InterruptedException ie){
				return;
			}
			time--;	//���� �ð� 1�� ����
		}
	}
}
