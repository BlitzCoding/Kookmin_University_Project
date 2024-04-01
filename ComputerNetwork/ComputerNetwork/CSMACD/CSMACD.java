import java.util.Random;
import java.util.Scanner;

public class CSMACD extends Thread {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Random r = new Random();
		final int n = 1;
		int k = 0;
		int rand;

		System.out.print("1�̻��� Maxium Propagation time���� �Է��ϼ��� : ");
		int Tp = sc.nextInt();
		if (Tp < 1) {
			System.out.println("1�̸��� �� �Է�, ���α׷� �����մϴ�");
			System.exit(0);
		}
		System.out.println();

		while (k < 15) {
			System.out.printf("***���� k = %d, Channel idle or busy ���� Ȯ��***", k);
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
			System.out.println("***Transmit and receive ������***");
			try {
				Thread.sleep(1000);
				System.out.println("���ۿϷ�");
				System.out.println();
				Thread.sleep(1000);
				System.out.println("**Collison ���� Ȯ����**");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int z = r.nextInt(2);

			try {
				if (z == 0) {
					System.out.println("Collison ����!, ���� Success");
					Thread.sleep(1000);
					break;
				} else {
					System.out.println("Collison ����!, Send a jamming signal");
					System.out.println();
					Thread.sleep(1000);
					k++;

					if (k > 15) {
						System.out.println("�õ� Ƚ�� 15�� �ʰ�, ��� ���� Abort");
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
				System.out.println("***���� R�� ���� 0 ~ " + (rk - 1) + "�Դϴ�.***");
				Thread.sleep(1000);
				int ran = r.nextInt(rk);

				System.out.println("***���õ� R�� �� : " + ran + ", Tb�� ���� " + (ran * Tp) + "�Դϴ�.***");
				System.out.println();
				int back = ran * Tp;
				if (back == 0) {
					System.out.println("0�̹Ƿ� ��� �����մϴ�");
					System.out.println();
				} else {
					System.out.println("��� �ð� : " + back);
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

