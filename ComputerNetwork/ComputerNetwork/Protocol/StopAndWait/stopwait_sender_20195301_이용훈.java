import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class stopwait_sender_20195301_�̿��� {
	Socket sender;
	ObjectOutputStream out = null;
	ObjectInputStream in = null;
	String packet, ack, msg;
	int sequence = 0;
	int i, n = 0;
	String recseq = null;

	stopwait_sender_20195301_�̿���() {
	}

	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			sender = new Socket("localhost", 1013);
			sequence = 0;
			out = new ObjectOutputStream(sender.getOutputStream());
			out.flush();
			in = new ObjectInputStream(sender.getInputStream());
			System.out.println("*****�����ڿ� �����*****");
			System.out.print("���� ���� �Է��Ͻÿ� : ");
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
					System.out.println("��Ŷ ���� : " + msg);
					Thread.sleep(3000);
					System.out.println("Time-out �����");
					System.out.println();
					System.out.println("��Ŷ ����(resent)" + msg);
					try {
						sender.setSoTimeout(1000);
						ack = (String) in.readObject();
					} catch (Exception e) {
						System.out.println();
					}
					System.out.println("ACK���");
					if (ack != null)
						recseq = String.valueOf(sequence);
					else
						recseq = "1";
					if (ack.equals(recseq)) {
						i++;
						System.out.println("receiver : " + "��Ŷ ���� ����");
					} else {
						System.out.println("Time-out ������");
						System.out.println();
						sequence = (sequence == 0) ? 1 : 0;
					}
				} catch (Exception e) {
				}
			} while (i < n + 1);
			System.out.println("***��� ��Ŷ ���� �Ϸ�***");
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
		stopwait_sender_20195301_�̿��� s = new stopwait_sender_20195301_�̿���();
		s.run();
	}
}