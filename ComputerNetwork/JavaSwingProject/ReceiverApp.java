import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiverApp {

	Socket receiver5;
	Socket receiver;
	ServerSocket receiverTrans;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String msg;

	public void run() {
		try {
			System.out.println("-------------Receiver-------------\n");
			System.out.println("=============Application Layer=============\n");
			receiverTrans = new ServerSocket(1080);
			connection = receiverTrans.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg = (String) in.readObject();

			System.out.println("Transport Layer에서 " + msg + " 받음");
			System.out.println("Sender로 부터 보낸 메시지 수신완료\n");

			receiver = new Socket("localhost", 1090);
			out = new ObjectOutputStream(receiver.getOutputStream());
			out.flush();
			in = new ObjectInputStream(receiver.getInputStream());
			System.out.println("Transport Layer에서 송신확인메시지 보내기");
			out.writeObject(msg);
			System.out.println();
			System.out.println("===========================================");

		} catch (Exception e) {

		}
	}

	public static void main(String[] args) {
		ReceiverApp s = new ReceiverApp();
		s.run();

	}

}
