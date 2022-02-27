import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class simple_sender_20195301_이용훈 {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		Socket socket = null;

		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress("localhost", 1013));
			System.out.println("*****수신자와 연결 성공*****");

			byte[] bytes = null;
			String send = null;

			OutputStream out = socket.getOutputStream();
			System.out.print("보낼 값을 입력하세요 : ");
			String send1 = sc.next();
			System.out.println();

			System.out.print("패킷으로 만드는 중");
			Thread.sleep(1000);
			bytes = send1.getBytes("UTF-8");
			out.write(bytes);
			out.flush();
			System.out.println(">>데이터 보내기 성공");

			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!socket.isClosed()) {
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}