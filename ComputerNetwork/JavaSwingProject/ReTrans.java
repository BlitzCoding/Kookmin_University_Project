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

			System.out.println("Network Layer에서 " + msg + " 받음");
			System.out.println("Stop-and-Wait protocol 실행, ACK 생성");

			int packet = msg.length();

			for (int i = 0; i < packet; i++) {
				System.out.println("패킷 " + msg.charAt(i) + "받음, ACK " + msg.charAt(i) + " 생성");
				System.out.println();
				Thread.sleep(2000);
			}

			System.out.println("Stop-and-Wait 완료, ACK 생성 완료");

			System.out.println("Application Layer로 전송중..");

			receiver4 = new Socket("localhost", 1080);
			out = new ObjectOutputStream(receiver4.getOutputStream());
			out.flush();
			Thread.sleep(2000);
			in = new ObjectInputStream(receiver4.getInputStream());
			out.writeObject(msg);
			out.flush();
			System.out.println("Application Layer로 전송 완료");
			System.out.println();
			System.out.println("===========================================");

			receiverTrans2 = new ServerSocket(1090);
			connection = receiverTrans2.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());

			rmsg = (String) in.readObject();

			System.out.println("Stop-and-Wait protocol 실행");

			for (int i = 0; i < packet; i++) {
				int num = rmsg.charAt(i);
				char asc = (char) num;
				System.out.println("ACK " + rmsg.charAt(i) + "를 ASCII Code " + num + "으로 변환");
				String binaryString = Integer.toBinaryString(num);
				binaryString = "00" + binaryString;
				System.out.println("ASCII Code를 비트로 변환 : " + binaryString);
				result = result + binaryString;

				System.out.println("총 비트 >>" + result);
				Thread.sleep(2000);
			}
			System.out.println("모든 ACK를 ASCII코드로 변환완료!");

			System.out.println("Network Layer로 전송중..");

			receiver44 = new Socket("localhost", 1100);
			out = new ObjectOutputStream(receiver44.getOutputStream());
			out.flush();
			Thread.sleep(2000);
			in = new ObjectInputStream(receiver44.getInputStream());
			out.writeObject(result);
			out.flush();
			System.out.println("Network Layer로 전송 완료");
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
