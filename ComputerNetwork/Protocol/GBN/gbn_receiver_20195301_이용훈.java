import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class gbn_receiver_20195301_이용훈 {

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("*****연결 기다리는중******");
		ServerSocket ss = new ServerSocket(1013);
		Socket receiver = new Socket();
		receiver = ss.accept();
		BufferedInputStream in = new BufferedInputStream(receiver.getInputStream());
		DataOutputStream out = new DataOutputStream(receiver.getOutputStream());
		System.out.println("*****연결 수락함*****");
		System.out.println();

		int p = in.read();

		boolean f[] = new boolean[p];

		int pc = in.read();

		if (pc == 0) {
			for (int i = 0; i < p; ++i) {
				System.out.println("받은 프레임 숫자 : " + i);
				out.write(i);
				out.flush();
				System.out.println("처리중");

				Thread.sleep(1000);

				int a = in.read();
				System.out.println("수신 처리 완료 : " + a);
			}
			out.flush();
		} else {
			for (int i = 0; i < p; ++i) {
				if (i == 3) {
					System.out.println("패킷 번호 전송(Lost) : " + i);
				} else {
					System.out.println("패킷 번호 전송 : " + i);
					out.write(i);
					out.flush();

					Thread.sleep(1000);

					int a = in.read();

					if (a != 255) {
						System.out.println("패킷 " + i + "에 대한 ACK를 " + i + "로 받았습니다.");
						System.out.println();
						f[i] = true;
					}
				}
			}

			for (int a = 0; a < p; a++) {
				if (f[a] == false) {
					System.out.println("패킷 재전송 : " + a);
					out.write(a);
					out.flush();

					Thread.sleep(1000);

					int b = in.read();
					System.out.println("패킷 " + a + "저장, ACK " + a + "를 보냈습니다.");
					System.out.println();
					f[a] = true;
				}
			}
			out.flush();
		}
		in.close();
		out.close();
		System.out.println(">>수신완료");
	}
}