import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class stopwait_receiver_20195301_�̿��� {
	ServerSocket reciever;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String packet, ack, data = "";
	int i = 0, sequence = 0;

	stopwait_receiver_20195301_�̿���() {
	}

	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			reciever = new ServerSocket(1013);
			System.out.println("*****���� ��ٸ��� ��*****");
			connection = reciever.accept();
			sequence = 0;
			System.out.println("*****���� ������*****");
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			out.writeObject("connected.");
			do {
				try {
					packet = (String) in.readObject();
					if (Integer.valueOf(packet.substring(0, 1)) == sequence) {
						data += packet.substring(2);
						sequence = (sequence == 0) ? 1 : 0;
						System.out.println("receiver : " + packet + " : �޾���");
						System.out.println();
					} else {
						System.out
								.println("receiver : " + packet + " : ��Ŷ���(Discard), " + packet.charAt(2) + "�� ACK ����");
						System.out.println();
					}
					if (i < 3) {
						Thread.sleep(2000);
						out.writeObject(String.valueOf(sequence));
						i++;
					} else {
						Thread.sleep(2000);
						out.writeObject(String.valueOf((sequence + 1) % 2));
						i = 0;
					}

				} catch (Exception e) {
				}
			} while (!packet.equals("end"));
			System.out.println(">>���� �Ϸ� : " + data);
			out.writeObject("���� ����");
		} catch (Exception e) {
		} finally {
			try {
				in.close();
				out.close();
				reciever.close();
			} catch (Exception e) {
			}
		}
	}

	public static void main(String args[]) {
		stopwait_receiver_20195301_�̿��� s = new stopwait_receiver_20195301_�̿���();

		s.run();

	}
}