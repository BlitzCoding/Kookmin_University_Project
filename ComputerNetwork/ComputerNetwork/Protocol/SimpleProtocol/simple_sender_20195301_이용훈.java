import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class simple_sender_20195301_�̿��� {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		Socket socket = null;

		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress("localhost", 1013));
			System.out.println("*****�����ڿ� ���� ����*****");

			byte[] bytes = null;
			String send = null;

			OutputStream out = socket.getOutputStream();
			System.out.print("���� ���� �Է��ϼ��� : ");
			String send1 = sc.next();
			System.out.println();

			System.out.print("��Ŷ���� ����� ��");
			Thread.sleep(1000);
			bytes = send1.getBytes("UTF-8");
			out.write(bytes);
			out.flush();
			System.out.println(">>������ ������ ����");

			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!socket.isClosed()) {
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}