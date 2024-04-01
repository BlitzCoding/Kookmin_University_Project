import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class gbn_receiver_20195301_�̿��� {

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("*****���� ��ٸ�����******");
		ServerSocket ss = new ServerSocket(1013);
		Socket receiver = new Socket();
		receiver = ss.accept();
		BufferedInputStream in = new BufferedInputStream(receiver.getInputStream());
		DataOutputStream out = new DataOutputStream(receiver.getOutputStream());
		System.out.println("*****���� ������*****");
		System.out.println();

		int p = in.read();

		boolean f[] = new boolean[p];

		int pc = in.read();

		if (pc == 0) {
			for (int i = 0; i < p; ++i) {
				System.out.println("���� ������ ���� : " + i);
				out.write(i);
				out.flush();
				System.out.println("ó����");

				Thread.sleep(1000);

				int a = in.read();
				System.out.println("���� ó�� �Ϸ� : " + a);
			}
			out.flush();
		} else {
			for (int i = 0; i < p; ++i) {
				if (i == 3) {
					System.out.println("��Ŷ ��ȣ ����(Lost) : " + i);
				} else {
					System.out.println("��Ŷ ��ȣ ���� : " + i);
					out.write(i);
					out.flush();

					Thread.sleep(1000);

					int a = in.read();

					if (a != 255) {
						System.out.println("��Ŷ " + i + "�� ���� ACK�� " + i + "�� �޾ҽ��ϴ�.");
						System.out.println();
						f[i] = true;
					}
				}
			}

			for (int a = 0; a < p; a++) {
				if (f[a] == false) {
					System.out.println("��Ŷ ������ : " + a);
					out.write(a);
					out.flush();

					Thread.sleep(1000);

					int b = in.read();
					System.out.println("��Ŷ " + a + "����, ACK " + a + "�� ���½��ϴ�.");
					System.out.println();
					f[a] = true;
				}
			}
			out.flush();
		}
		in.close();
		out.close();
		System.out.println(">>���ſϷ�");
	}
}