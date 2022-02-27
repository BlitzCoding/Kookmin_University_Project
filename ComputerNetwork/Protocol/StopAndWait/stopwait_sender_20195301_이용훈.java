import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class stopwait_sender_20195301_이용훈 {
	Socket sender;
	ObjectOutputStream out = null;
	ObjectInputStream in = null;
	String packet, ack, msg;
	int sequence = 0;
	int i, n = 0;
	String recseq = null;

	stopwait_sender_20195301_이용훈() {
	}

	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			sender = new Socket("localhost", 1013);
			sequence = 0;
			out = new ObjectOutputStream(sender.getOutputStream());
			out.flush();
			in = new ObjectInputStream(sender.getInputStream());
			System.out.println("*****수신자와 연결됨*****");
			System.out.print("보낼 값을 입력하시오 : ");
			packet = br.readLine();
			System.out.println();
			n = packet.length();

			do {
				try {

					if (i < n) {
						msg = String.valueOf(sequence);
						msg = msg + " " + packet.substring(i, i + 1);
					} else if (i == n) {
						msg = "end";
						out.writeObject(msg);
						break;
					}
					out.writeObject(msg);
					sequence = (sequence == 0) ? 1 : 0;
					out.flush();
					System.out.println("패킷 전송 : " + msg);
					Thread.sleep(3000);
					System.out.println("Time-out 재시작");
					System.out.println();
					System.out.println("패킷 전송(resent)" + msg);
					try {
						sender.setSoTimeout(1000);
						ack = (String) in.readObject();
					} catch (Exception e) {
						System.out.println();
					}
					System.out.println("ACK대기");
					if (ack != null)
						recseq = String.valueOf(sequence);
					else
						recseq = "1";
					if (ack.equals(recseq)) {
						i++;
						System.out.println("receiver : " + "패킷 전송 선공");
					} else {
						System.out.println("Time-out 재전송");
						System.out.println();
						sequence = (sequence == 0) ? 1 : 0;
					}
				} catch (Exception e) {
				}
			} while (i < n + 1);
			System.out.println("***모든 패킷 전송 완료***");
		} catch (Exception e) {
		} finally {
			try {
				in.close();
				out.close();
				sender.close();
			} catch (Exception e) {
			}
		}
	}

	public static void main(String args[]) {
		stopwait_sender_20195301_이용훈 s = new stopwait_sender_20195301_이용훈();
		s.run();
	}
}