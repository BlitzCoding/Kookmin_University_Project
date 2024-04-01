import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class SendData {

	Socket sender4, sender44;
	ServerSocket sendData, sendData2;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String msg, msg2;
	Random rand = new Random();
	Scanner sc = new Scanner(System.in);
	int count = 0;
	final int n = 1;
	String stuffing = "";
	int k = 0;
	String result = "";

	public void run() {
		try {
			System.out.println("-------------Sender-------------\n");
			System.out.println("=============Datalink Layer=============\n");
			sendData = new ServerSocket(1020);
			connection = sendData.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg = (String) in.readObject();

			//
			System.out.println("Network Layer���� " + msg + "���޹���, Bit-Stuffing ������...");

			for (int i = 0; i < msg.length(); i++) {
				if (msg.charAt(i) == '1') {
					count++;
					stuffing = stuffing + msg.charAt(i);
				} else {
					stuffing = stuffing + msg.charAt(i);
					count = 0;
				}

				if (count == 5) {
					stuffing = stuffing + '0';
					count = 0;
				}
			}
			Thread.sleep(2000);

			System.out.println("Stuffed bit : " + stuffing + " �� ��ȯ �Ϸ�\n");
			System.out.println("�ƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢ�CSMA/CD ����ƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢ�");

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
					int y = rand.nextInt(2);
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

						e.printStackTrace();
					}
				}
				System.out.println("***Transmit and receive ������***");
				try {
					System.out.println("���ۿϷ�");
					System.out.println();
					Thread.sleep(1000);

					System.out.println("**Collison ���� Ȯ����**");
					Thread.sleep(1000);
				} catch (InterruptedException e) {

					e.printStackTrace();

				}

				try {
					int z = rand.nextInt(2);
					if (z == 0) {
						System.out.println("Collison ����!, ���� Success\n");
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

					e.printStackTrace();
				}

				try {
					Thread.sleep(1000);
					int rk = (int) Math.pow(2, k);
					System.out.println("***���� R�� ���� 0 ~ " + (rk - 1) + "�Դϴ�.***");
					Thread.sleep(1000);
					int ran = rand.nextInt(rk);

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

					e.printStackTrace();

				}

			}
			sender4 = new Socket("localhost", 1030);
			out = new ObjectOutputStream(sender4.getOutputStream());
			out.flush();
			in = new ObjectInputStream(sender4.getInputStream());

			System.out.println("CSMA/CD �Ϸ�, Physical Layer�� �޽��� ��������...");
			Thread.sleep(2000);
			out.writeObject(stuffing);
			System.out.println("Physical Layer�� " + stuffing + " ���� �Ϸ�");

			System.out.println();
			System.out.println("===========================================");

			//

			sendData2 = new ServerSocket(1140);
			connection = sendData2.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg2 = (String) in.readObject();

			System.out.println("Physical Layer���� " + msg2 + " ���޹���");

			System.out.println("Bit-unstuffing ��...");

			for (int i = 0; i < msg2.length(); i++) {
				if (msg2.charAt(i) == '1') {
					count++;
					result = result + msg2.charAt(i);
				} else {
					count = 0;
					result = result + msg2.charAt(i);
				}
				if (count == 5) {
					if ((i + 2) != result.length()) {
						result = result + msg2.charAt(i + 2);
					} else {
						result = result + '1';
					}
					i = i + 2;
					count = 1;
				}

			}
			Thread.sleep(2000);
			System.out.println("Unstuffed bit �Ϸ� : " + result);

			System.out.println("Network layer���� ������...");

			sender44 = new Socket("localhost", 1150);
			out = new ObjectOutputStream(sender44.getOutputStream());
			out.flush();
			in = new ObjectInputStream(sender44.getInputStream());
			Thread.sleep(2000);
			out.writeObject(result);
			System.out.println("Network layer�� ���ۿϷ�\n");
			System.out.println("===========================================\n");

		} catch (Exception e) {

		}

	}

	public static void main(String[] args) {
		SendData s = new SendData();
		s.run();

	}

}
