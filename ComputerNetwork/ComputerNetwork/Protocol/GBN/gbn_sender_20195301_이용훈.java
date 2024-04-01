import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class gbn_sender_20195301_�̿��� {

	public static void main(String[] args) throws IOException {
		Socket sender = new Socket("localhost", 1013);

		BufferedInputStream in = new BufferedInputStream(sender.getInputStream());
		DataOutputStream out = new DataOutputStream(sender.getOutputStream());
		Scanner sc = new Scanner(System.in);
		Random rand = new Random();

		System.out.println("*****���� ������*****");
		System.out.print("��û�� ��Ŷ�� ���� �Է��ϼ��� : ");
		int n = sc.nextInt();
		System.out.println();

		out.write(n);
		out.flush();

		int choice = rand.nextInt(5);
		out.write(choice);

		int check = 0;
		int i = 0;

		System.out.println("================");
		if (choice == 0) { // �Ϻ��ϰ� ���ŵǴ� ���̽�
			for (int j = 0; j < n; ++j) {
				i = in.read();
				System.out.println("�۽��� ��Ŷ : " + i);
				out.write(i);
				out.flush();
			}
			out.flush();
		} else { // ���� ������ ���� ���̽�
			for (int j = 0; j < n; ++j) {
				i = in.read();
				if (i == check) {
					System.out.println("�۽��� ��Ŷ : " + i);
					out.write(1);
					++check;
				} else {
					System.out.println("������ ��Ŷ(Discard) : " + (i - 1));
					j--;
					System.out.println("��Ŷ ������");
					out.write(-1);
				}
				out.flush();
			}
		}

		in.close();
		out.close();
		System.out.println("*****�۽ſϷ�*****");
	}
}
