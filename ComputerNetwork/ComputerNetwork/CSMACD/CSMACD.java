import java.util.Random;
import java.util.Scanner;

public class CSMACD extends Thread {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Random r = new Random();
		final int n = 1;
		int k = 0;
		int rand;

		System.out.print("1이상의 Maxium Propagation time값을 입력하세요 : ");
		int Tp = sc.nextInt();
		if (Tp < 1) {
			System.out.println("1미만의 수 입력, 프로그램 종료합니다");
			System.exit(0);
		}
		System.out.println();

		while (k < 15) {
			System.out.printf("***현재 k = %d, Channel idle or busy 여부 확인***", k);
			System.out.println();
			while (n < 2) {
				int y = r.nextInt(2);
				try {
					if (y == 1) {
						System.out.println("Channel is busy!");
						Thread.sleep(1000);
					} else {
						System.out.println("Channel is idle");
						System.out.println();
						Thread.sleep(1000);
						break;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("***Transmit and receive 수행중***");
			try {
				Thread.sleep(1000);
				System.out.println("전송완료");
				System.out.println();
				Thread.sleep(1000);
				System.out.println("**Collison 여부 확인중**");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int z = r.nextInt(2);

			try {
				if (z == 0) {
					System.out.println("Collison 없음!, 전송 Success");
					Thread.sleep(1000);
					break;
				} else {
					System.out.println("Collison 감지!, Send a jamming signal");
					System.out.println();
					Thread.sleep(1000);
					k++;

					if (k > 15) {
						System.out.println("시도 횟수 15번 초과, 통신 실패 Abort");
						break;
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Thread.sleep(1000);
				int rk = (int) Math.pow(2, k);
				System.out.println("***현재 R의 값은 0 ~ " + (rk - 1) + "입니다.***");
				Thread.sleep(1000);
				int ran = r.nextInt(rk);

				System.out.println("***선택된 R의 값 : " + ran + ", Tb의 값은 " + (ran * Tp) + "입니다.***");
				System.out.println();
				int back = ran * Tp;
				if (back == 0) {
					System.out.println("0이므로 즉시 전송합니다");
					System.out.println();
				} else {
					System.out.println("대기 시간 : " + back);
					System.out.println();
					Thread.sleep(back);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}

	}
}

