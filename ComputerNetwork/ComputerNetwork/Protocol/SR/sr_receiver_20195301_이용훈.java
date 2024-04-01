
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class sr_receiver_20195301_�̿��� {

	// Receiver
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;

	public sr_receiver_20195301_�̿���(int port) throws InterruptedException {
		System.out.println("*****���� ��ٸ�����*****");
		try {
			server = new ServerSocket(port);
			socket = server.accept();
			System.out.println("******�����*****");

			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
			String line = "";

			int lastFrameSent = 0;
			int window = Integer.parseInt(in.readUTF());
			System.out.println("Window�� ũ�� : " + window);
			int frames = Integer.parseInt(in.readUTF());
			System.out.println("��Ŷ�� �� : " + frames);
			boolean loop = true;
			int sent = 1;

			int corr = 0;
			while (loop) {

				out.writeUTF("Window start");
				for (int i = 0; i < window; i++) {
					if (corr != 0) {
						System.out.println("ACK " + corr + " ����");
						out.writeUTF(String.valueOf(corr));
						Thread.sleep(1000);
						if (corr == frames)
							break;
						if (lastFrameSent == 1)
							break;
					} else {
						System.out.println("ACK " + sent + " ����");

						out.writeUTF(String.valueOf(sent));
						if (sent == frames) {
							lastFrameSent = 1;
							break;
						}
						sent++;

					}
					corr = 0;

				}

				out.writeUTF("Window end");
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
				}
				System.out.print("�ջ�� ��Ŷ : ");
				corr = Integer.parseInt(in.readUTF());
				System.out.println(corr);

				if (corr == 0 && sent == frames)
					loop = false;

			}
			out.writeUTF("Stop");
			System.out.println("���ſϷ�");

			socket.close();
			in.close();
		} catch (IOException i) {
		}
	}

	public static void main(String args[]) throws InterruptedException {
		sr_receiver_20195301_�̿��� server = new sr_receiver_20195301_�̿���(1013);
	}
}