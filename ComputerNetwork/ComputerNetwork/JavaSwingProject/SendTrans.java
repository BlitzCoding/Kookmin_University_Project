import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SendTrans {

	Socket sender2, sender22;
	ServerSocket sendtrans, sendtrans2;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String msg, msg2;
	Random rand = new Random();

	public void run() {
		try {
			System.out.println("-------------Sender-------------\n");
			System.out.println("=============Transport Layer=============\n");
			sendtrans = new ServerSocket(1000);
			connection = sendtrans.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg = (String) in.readObject();

			System.out.println("Application Layer���� ���� " + msg + "������");

			// Stop and Wait

			System.out.println("Stop-and-Wait protocol ����");

			int packet = msg.length();

			for (int i = 0; i < packet; i++) {
				int rn = rand.nextInt(3);
				System.out.println("��Ŷ " + msg.charAt(i) + " ������...");
				Thread.sleep(2000);
				if (rn == 1) {
					System.out.println("��Ŷ " + msg.charAt(i) + " ���� ���� Time out, Restart");
					Thread.sleep(2000);
					System.out.println("��Ŷ " + msg.charAt(i) + " ���� ����, ACK �����\n");
					Thread.sleep(2000);
				} else {
					System.out.println("��Ŷ " + msg.charAt(i) + " ���� ����, ACK �����\n");
					Thread.sleep(2000);
				}
			}

			System.out.println("��� ��Ŷ ���� �Ϸ�, Network Layer�� ������");
			Thread.sleep(2000);
			sender2 = new Socket("localhost", 1010);
			out = new ObjectOutputStream(sender2.getOutputStream());
			out.flush();
			in = new ObjectInputStream(sender2.getInputStream());
			out.writeObject(msg);
			System.out.println("Network Layer�� ���� �Ϸ�\n");
			System.out.println("===========================================\n");

			sendtrans2 = new ServerSocket(1160);
			connection = sendtrans2.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg2 = (String) in.readObject();

			System.out.println(msg2 + "�� ���۹���, Stop and Wait protocol ����");
			packet = msg2.length();

			for (int i = 0; i < packet / 8; i++) {
				String bit = msg2.substring(0, 8);
				msg2 = msg2.substring(8);
				System.out.println("���� ��Ʈ�� �� : " + msg2);
				int binaryToDecimal = Integer.parseInt(bit, 2);
				char ascii = (char) binaryToDecimal;
				System.out.println(binaryToDecimal + "�ƽ�Ű �ڵ� ����, ACK" + ascii + "Ȯ��");
				Thread.sleep(1000);
			}

			System.out.println(msg2 + "�� ACK ����Ȯ�οϷ�");

			System.out.println("Application Layer�� ������");
			Thread.sleep(2000);
			sender22 = new Socket("localhost", 1170);
			out = new ObjectOutputStream(sender22.getOutputStream());
			out.flush();
			in = new ObjectInputStream(sender22.getInputStream());
			out.writeObject(msg2);
			System.out.println("Application Layer�� ���� �Ϸ�\n");

		} catch (Exception e) {

		}
	}

	public static void main(String[] args) {

		SendTrans s = new SendTrans();
		s.run();

	}

}
