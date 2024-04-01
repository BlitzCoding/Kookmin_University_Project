import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SendPhysical {

	Socket sender5, sender55;
	ServerSocket sendPhysical, sendPhysical2;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String msg, msg2;
	String result = "";
	int level = 1;
	String x = "0";
	String first, second = "";

	public void run() {
		try {
			System.out.println("-------------Sender-------------\n");
			System.out.println("=============Physical Layer=============\n");
			sendPhysical = new ServerSocket(1030);
			connection = sendPhysical.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg = (String) in.readObject();

			System.out.println("Datalink Layer에서 " + msg + "전달받음, MLT-3 적용중...");

			for (int i = 0; i < msg.length(); i++) {
				if (msg.charAt(i) == '0') {
					result = result + x;
				} else {
					if (x == "0") {
						if (level == 1) {
							x = "+";
							level = -1;
							result = result + x;
						} else {
							x = "-";
							level = 1;
							result = result + x;
						}
					} else {
						x = "0";
						result = result + x;
					}
				}
			}
			Thread.sleep(2000);
			System.out.println("MLT-3 변환된 bit : " + result);
			System.out.println();
			System.out.println("Receiver으로 전송합니다...");
			Thread.sleep(2000);

			sender5 = new Socket("localhost", 1040);
			out = new ObjectOutputStream(sender5.getOutputStream());
			out.flush();
			in = new ObjectInputStream(sender5.getInputStream());

			out.writeObject(result);
			out.flush();

			System.out.println("Receiver로 전송완료");
			System.out.println();
			System.out.println("===========================================");

			//

			sendPhysical = new ServerSocket(1130);
			connection = sendPhysical.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			msg2 = (String) in.readObject();

			System.out.println("Receiver에서 받은 MLT-3 신호 : " + msg2);

			System.out.println("MLT-3를 Bit로 바꾸는 중...");

			for (int i = 0; i < msg2.length(); i++) {
				if (i < 1) {
					if (msg2.charAt(0) == '0') {
						first = "0";
					} else {
						first = "1";
					}

				} else {
					if (msg2.charAt(i) == '0') {
						if (msg2.charAt(i - 1) == '0') {
							x = "0";
							second = second + x;
						} else {
							x = "1";
							second = second + x;
						}
					} else {
						if (msg2.charAt(i - 1) == '0') {
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
			String result2 = first + second;

			System.out.println("bit stream으로 변환된 신호 : " + result2);

			System.out.println(result2 + "를 Datalink layer로 전송중");

			sender55 = new Socket("localhost", 1140);
			out = new ObjectOutputStream(sender55.getOutputStream());
			out.flush();
			in = new ObjectInputStream(sender55.getInputStream());
			Thread.sleep(2000);
			out.writeObject(result2);
			System.out.println("Datalink layer에 전송완료\n");
			System.out.println("===========================================");

		} catch (Exception e) {

		}

	}

	public static void main(String[] args) {
		SendPhysical s = new SendPhysical();
		s.run();

	}

}
