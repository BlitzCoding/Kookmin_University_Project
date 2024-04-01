import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RePhysical {

	Socket receiver1, receiver11;
	ServerSocket receiverPhysical, receiverPhysical2;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String msg, msg2;
	String first, second = "";
	String x = "0";
	int level = 1;

	public void run() {
		try {
			System.out.println("-------------Receiver-------------\n");
			System.out.println("=============Physical Layer=============\n");
			receiverPhysical = new ServerSocket(1040);
			connection = receiverPhysical.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg = (String) in.readObject();

			System.out.println("Sender에서 받은 MLT-3 신호 : " + msg);

			System.out.println("MLT-3를 Bit로 바꾸는 중...");

			for (int i = 0; i < msg.length(); i++) {
				if (i < 1) {
					if (msg.charAt(0) == '0') {
						first = "0";
					} else {
						first = "1";
					}

				} else {
					if (msg.charAt(i) == '0') {
						if (msg.charAt(i - 1) == '0') {
							x = "0";
							second = second + x;
						} else {
							x = "1";
							second = second + x;
						}
					} else {
						if (msg.charAt(i - 1) == '0') {
							x = "1";
							second = second + x;
						} else {
							x = "0";
							second = second + x;
						}
					}
				}
			}
			Thread.sleep(2000);
			String result = first + second;

			System.out.println("bit stream으로 변환된 신호 : " + result);

			System.out.println(result + "를 Datalink layer로 전송중");

			receiver1 = new Socket("localhost", 1050);
			out = new ObjectOutputStream(receiver1.getOutputStream());
			out.flush();
			in = new ObjectInputStream(receiver1.getInputStream());
			Thread.sleep(2000);
			out.writeObject(result);
			System.out.println("Datalink layer에 전송완료\n");
			System.out.println("===========================================");

			receiverPhysical2 = new ServerSocket(1120);
			connection = receiverPhysical2.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg2 = (String) in.readObject();

			//

			System.out.println("Datalink Layer에서 " + msg2 + "전달받음, MLT-3 적용중...");

			x = "0";
			String result2 = "";

			for (int i = 0; i < msg2.length(); i++) {
				if (msg2.charAt(i) == '0') {
					result2 = result2 + x;
				} else {
					if (x == "0") {
						if (level == 1) {
							x = "+";
							level = -1;
							result2 = result2 + x;
						} else {
							x = "-";
							level = 1;
							result2 = result2 + x;
						}
					} else {
						x = "0";
						result2 = result2 + x;
					}
				}
			}
			Thread.sleep(2000);
			System.out.println("MLT-3 변환된 bit : " + result2);
			System.out.println();
			System.out.println("Sender으로 전송합니다...");
			Thread.sleep(2000);

			receiver11 = new Socket("localhost", 1130);
			out = new ObjectOutputStream(receiver11.getOutputStream());
			out.flush();
			in = new ObjectInputStream(receiver11.getInputStream());

			out.writeObject(result2);
			out.flush();

			System.out.println("Sender로 전송완료");
			System.out.println();
			System.out.println("===========================================");

		} catch (Exception e) {

		}
	}

	public static void main(String[] args) {
		RePhysical s = new RePhysical();
		s.run();

	}

}
