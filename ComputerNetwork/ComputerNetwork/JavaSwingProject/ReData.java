import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class ReData {

	Socket receiver2, receiver22;
	ServerSocket receiverData, receiverData2;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String msg;
	int count = 0;
	String result = "";
	Scanner sc = new Scanner(System.in);
	final int n = 1;
	String stuffing = "";
	int k = 0;
	Random rand = new Random();

	public void run() {
		try {
			System.out.println("-------------Receiver-------------\n");
			System.out.println("=============Datalink Layer=============\n");
			receiverData = new ServerSocket(1050);
			connection = receiverData.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg = (String) in.readObject();

			System.out.println("Physical Layer���� " + msg + " ���޹���");

			System.out.println("Bit-unstuffing ��...");

			for (int i = 0; i < msg.length(); i++) {
				if (msg.charAt(i) == '1') {
					count++;
					result = result + msg.charAt(i);
				} else {
					count = 0;
					result = result + msg.charAt(i);
				}
				if (count == 5) {
					if ((i + 2) != result.length()) {
						result = result + msg.charAt(i + 2);
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

			receiver2 = new Socket("localhost", 1060);
			out = new ObjectOutputStream(receiver2.getOutputStream());
			out.flush();
			in = new ObjectInputStream(receiver2.getInputStream());
			Thread.sleep(2000);
			out.writeObject(result);
			System.out.println("Network layer�� ���ۿϷ�\n");
			System.out.println("===========================================\n");

			receiverData2 = new ServerSocket(1110);
			connection = receiverData2.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg = (String) in.readObject();

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

			receiver22 = new Socket("localhost", 1120);
			out = new ObjectOutputStream(receiver22.getOutputStream());
			out.flush();
			in = new ObjectInputStream(receiver22.getInputStream());

			System.out.println("CSMA/CD �Ϸ�, Physical Layer�� �޽��� ��������...");
			Thread.sleep(2000);
			out.writeObject(stuffing);
			System.out.println("Physical Layer�� " + stuffing + " ���� �Ϸ�");

			System.out.println();
			System.out.println("===========================================");

		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		ReData s = new ReData();
		s.run();

	}

}
