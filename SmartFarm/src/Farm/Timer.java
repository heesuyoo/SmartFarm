package Farm;

/*농작물이 수확하기까지 or 상하기까지 남은 시간을 표시하는 클래스 Timer
 * Runnable 인터페이스 구현
 */
public class Timer implements Runnable {
	private int time = 20;	//남은 시간 변수. 초기값 20
	
	//time을 get하는 메소드
	public int getTime()
	{
		return time;
	}
	
	//스레드 시작시 실행되는 메소드
	public void run(){
		//스레드가 종료될 때까지 반복
		while(true){
			try{
				Thread.sleep(1000);	//1초 동안 대기
			}
			//인터럽트 예외 발생시 예외처리 구문
			catch(InterruptedException ie){
				return;
			}
			time--;	//남은 시간 1씩 감소
		}
	}
}
