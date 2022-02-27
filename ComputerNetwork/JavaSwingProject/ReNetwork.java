import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ReNetwork {

	Socket receiver3, receiver33;
	ServerSocket receiverNetwork, receiverNetwork2;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String msg;

	public void run() {
		try {

			System.out.println("-------------Receiver-------------\n");
			System.out.println("=============Network Layer=============\n");
			receiverNetwork = new ServerSocket(1060);
			connection = receiverNetwork.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg = (String) in.readObject();

			System.out.println("Datalink Layer에서 " + msg + " 전달받음");

			System.out.println("전송중(bypass)");
			Thread.sleep(2000);
			System.out.println("전송완료(bypass)\n");
			System.out.println("Transport Layer로 " + msg + "보냄");

			receiver3 = new Socket("localhost", 1070);
			out = new ObjectOutputStream(receiver3.getOutputStream());
			out.flush();
			in = new ObjectInputStream(receiver3.getInputStream());

			out.writeObject(msg);
			out.flush();

			System.out.println();
			System.out.println("===========================================");

			receiverNetwork = new ServerSocket(1100);
			connection = receiverNetwork.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg = (String) in.readObject();

			System.out.println("Datalink Layer에서 " + msg + " 전달받음");

			System.out.println("전송중(bypass)");
			Thread.sleep(2000);
			System.out.println("전송완료(bypass)\n");
			System.out.println("Transport Layer로 " + msg + "보냄");

			receiver33 = new Socket("localhost", 1110);
			out = new ObjectOutputStream(receiver33.getOutputStream());
			out.flush();
			in = new ObjectInputStream(receiver33.getInputStream());

			out.writeObject(msg);
			out.flush();

			System.out.println();
			System.out.println("===========================================");

		} catch (Exception e) {

		}
	}

	public static void main(String[] args) {
		ReNetwork s = new ReNetwork();
		s.run();

	}

}
