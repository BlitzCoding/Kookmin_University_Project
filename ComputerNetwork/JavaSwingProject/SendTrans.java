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

			System.out.println("Application Layer으로 부터 " + msg + "도착함");

			// Stop and Wait

			System.out.println("Stop-and-Wait protocol 실행");

			int packet = msg.length();

			for (int i = 0; i < packet; i++) {
				int rn = rand.nextInt(3);
				System.out.println("패킷 " + msg.charAt(i) + " 전송중...");
				Thread.sleep(2000);
				if (rn == 1) {
					System.out.println("패킷 " + msg.charAt(i) + " 전송 실패 Time out, Restart");
					Thread.sleep(2000);
					System.out.println("패킷 " + msg.charAt(i) + " 전송 성공, ACK 대기중\n");
					Thread.sleep(2000);
				} else {
					System.out.println("패킷 " + msg.charAt(i) + " 전송 성공, ACK 대기중\n");
					Thread.sleep(2000);
				}
			}

			System.out.println("모든 패킷 전송 완료, Network Layer로 전송중");
			Thread.sleep(2000);
			sender2 = new Socket("localhost", 1010);
			out = new ObjectOutputStream(sender2.getOutputStream());
			out.flush();
			in = new ObjectInputStream(sender2.getInputStream());
			out.writeObject(msg);
			System.out.println("Network Layer에 전송 완료\n");
			System.out.println("===========================================\n");

			sendtrans2 = new ServerSocket(1160);
			connection = sendtrans2.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg2 = (String) in.readObject();

			System.out.println(msg2 + "을 전송받음, Stop and Wait protocol 실행");
			packet = msg2.length();

			for (int i = 0; i < packet / 8; i++) {
				String bit = msg2.substring(0, 8);
				msg2 = msg2.substring(8);
				System.out.println("현재 비트값 은 : " + msg2);
				int binaryToDecimal = Integer.parseInt(bit, 2);
				char ascii = (char) binaryToDecimal;
				System.out.println(binaryToDecimal + "아스키 코드 받음, ACK" + ascii + "확인");
				Thread.sleep(1000);
			}

			System.out.println(msg2 + "의 ACK 수신확인완료");

			System.out.println("Application Layer로 전송중");
			Thread.sleep(2000);
			sender22 = new Socket("localhost", 1170);
			out = new ObjectOutputStream(sender22.getOutputStream());
			out.flush();
			in = new ObjectInputStream(sender22.getInputStream());
			out.writeObject(msg2);
			System.out.println("Application Layer에 전송 완료\n");

		} catch (Exception e) {

		}
	}

	public static void main(String[] args) {

		SendTrans s = new SendTrans();
		s.run();

	}

}
