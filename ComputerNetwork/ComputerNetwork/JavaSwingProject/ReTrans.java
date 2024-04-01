import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ReTrans {

	Socket receiver4, receiver44;
	ServerSocket receiverTrans1, receiverTrans2;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String msg;
	String rmsg;
	String result = "";

	public void run() {
		try {
			System.out.println("-------------Receiver-------------\n");
			System.out.println("=============Transport Layer=============\n");
			receiverTrans1 = new ServerSocket(1070);
			connection = receiverTrans1.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg = (String) in.readObject();

			System.out.println("Network Layer���� " + msg + " ����");
			System.out.println("Stop-and-Wait protocol ����, ACK ����");

			int packet = msg.length();

			for (int i = 0; i < packet; i++) {
				System.out.println("��Ŷ " + msg.charAt(i) + "����, ACK " + msg.charAt(i) + " ����");
				System.out.println();
				Thread.sleep(2000);
			}

			System.out.println("Stop-and-Wait �Ϸ�, ACK ���� �Ϸ�");

			System.out.println("Application Layer�� ������..");

			receiver4 = new Socket("localhost", 1080);
			out = new ObjectOutputStream(receiver4.getOutputStream());
			out.flush();
			Thread.sleep(2000);
			in = new ObjectInputStream(receiver4.getInputStream());
			out.writeObject(msg);
			out.flush();
			System.out.println("Application Layer�� ���� �Ϸ�");
			System.out.println();
			System.out.println("===========================================");

			receiverTrans2 = new ServerSocket(1090);
			connection = receiverTrans2.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());

			rmsg = (String) in.readObject();

			System.out.println("Stop-and-Wait protocol ����");

			for (int i = 0; i < packet; i++) {
				int num = rmsg.charAt(i);
				char asc = (char) num;
				System.out.println("ACK " + rmsg.charAt(i) + "�� ASCII Code " + num + "���� ��ȯ");
				String binaryString = Integer.toBinaryString(num);
				binaryString = "00" + binaryString;
				System.out.println("ASCII Code�� ��Ʈ�� ��ȯ : " + binaryString);
				result = result + binaryString;

				System.out.println("�� ��Ʈ >>" + result);
				Thread.sleep(2000);
			}
			System.out.println("��� ACK�� ASCII�ڵ�� ��ȯ�Ϸ�!");

			System.out.println("Network Layer�� ������..");

			receiver44 = new Socket("localhost", 1100);
			out = new ObjectOutputStream(receiver44.getOutputStream());
			out.flush();
			Thread.sleep(2000);
			in = new ObjectInputStream(receiver44.getInputStream());
			out.writeObject(result);
			out.flush();
			System.out.println("Network Layer�� ���� �Ϸ�");
			System.out.println();
			System.out.println("===========================================");

		} catch (Exception e) {

		}
	}

	public static void main(String[] args) {
		ReTrans s = new ReTrans();
		s.run();

	}

}
