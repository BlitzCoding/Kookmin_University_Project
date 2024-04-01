import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SendNetwork {

	Socket sender3, sender33;
	ServerSocket sendNetwork, sendNetwork2;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String msg, msg2;

	public void run() {
		try {
			System.out.println("-------------Sender-------------\n");
			System.out.println("=============Network Layer=============\n");
			sendNetwork = new ServerSocket(1010);
			connection = sendNetwork.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg = (String) in.readObject();

			System.out.println("Transport Layer에서 " + msg + "받음");

			System.out.println("전송중(bypass)");
			Thread.sleep(2000);

			sender3 = new Socket("localhost", 1020);
			out = new ObjectOutputStream(sender3.getOutputStream());
			out.flush();
			in = new ObjectInputStream(sender3.getInputStream());

			out.writeObject(msg);
			out.flush();

			System.out.println("전송완료(bypass)\n");
			System.out.println("Datalink Layer로 " + msg + "보냄");

			System.out.println();
			System.out.println("===========================================");

			sendNetwork2 = new ServerSocket(1150);
			connection = sendNetwork2.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg2 = (String) in.readObject();

			System.out.println("Datalink Layer에서 " + msg2 + "받음");

			System.out.println("전송중(bypass)");
			Thread.sleep(2000);

			sender33 = new Socket("localhost", 1160);
			out = new ObjectOutputStream(sender33.getOutputStream());
			out.flush();
			in = new ObjectInputStream(sender33.getInputStream());

			out.writeObject(msg2);
			out.flush();

			System.out.println("전송완료(bypass)\n");
			System.out.println("Transport Layer로 " + msg2 + "보냄");

		} catch (Exception e) {

		}
	}

	public static void main(String[] args) {
		SendNetwork s = new SendNetwork();
		s.run();

	}

}
