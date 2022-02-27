
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class sr_receiver_20195301_이용훈 {

	// Receiver
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;

	public sr_receiver_20195301_이용훈(int port) throws InterruptedException {
		System.out.println("*****연결 기다리는중*****");
		try {
			server = new ServerSocket(port);
			socket = server.accept();
			System.out.println("******연결됨*****");

			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
			String line = "";

			int lastFrameSent = 0;
			int window = Integer.parseInt(in.readUTF());
			System.out.println("Window의 크기 : " + window);
			int frames = Integer.parseInt(in.readUTF());
			System.out.println("패킷의 수 : " + frames);
			boolean loop = true;
			int sent = 1;

			int corr = 0;
			while (loop) {

				out.writeUTF("Window start");
				for (int i = 0; i < window; i++) {
					if (corr != 0) {
						System.out.println("ACK " + corr + " 전송");
						out.writeUTF(String.valueOf(corr));
						Thread.sleep(1000);
						if (corr == frames)
							break;
						if (lastFrameSent == 1)
							break;
					} else {
						System.out.println("ACK " + sent + " 전송");

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
				System.out.print("손상된 패킷 : ");
				corr = Integer.parseInt(in.readUTF());
				System.out.println(corr);

				if (corr == 0 && sent == frames)
					loop = false;

			}
			out.writeUTF("Stop");
			System.out.println("수신완료");

			socket.close();
			in.close();
		} catch (IOException i) {
		}
	}

	public static void main(String args[]) throws InterruptedException {
		sr_receiver_20195301_이용훈 server = new sr_receiver_20195301_이용훈(1013);
	}
}