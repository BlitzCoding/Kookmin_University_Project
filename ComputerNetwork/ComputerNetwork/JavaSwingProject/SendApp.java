import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SendApp {
	Socket sender1;
	ServerSocket sendApp;
	Socket connection = null;
	ObjectOutputStream out = null;
	ObjectInputStream in = null;
	String msg, msg2;

	public void run() {
		try {

			System.out.println("-------------Sender-------------\n");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			sender1 = new Socket("localhost", 1000);
			out = new ObjectOutputStream(sender1.getOutputStream());
			out.flush();
			in = new ObjectInputStream(sender1.getInputStream());
			System.out.println("=============Application Layer=============\n");
			System.out.print("���� ��Ʈ�� �Է��Ͻÿ� : ");
			msg = br.readLine();
			System.out.println();

			System.out.println("Message�� �߻��߽��ϴ� : " + msg);

			msg = msg.replace(" ", "");

			System.out.println("Message�� Transport Layer�� ������...");
			Thread.sleep(2000);
			out.writeObject(msg);
			out.flush();
			System.out.println("Transport Layer�� " + msg + "�� ���ۿϷ�\n");

			System.out.println("===========================================\n");

			sendApp = new ServerSocket(1170);
			connection = sendApp.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg2 = (String) in.readObject();

			System.out.println("Transport Layer���� " + msg2 + " ����");
			System.out.println("�۽Ÿ޽��� : " + msg + "�� ���� ����Ȯ�� �Ϸ�!");

			in.close();
			out.close();

		} catch (Exception e) {

		}
	}

	public static void main(String[] args) {
		SendApp s = new SendApp();
		s.run();
	}

}
