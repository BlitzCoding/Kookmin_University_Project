import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class simple_receiver_20195301_이용훈 {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress("localhost", 1013));

			while (true) {
				System.out.println("*****연결 기다림******");
				socket = serverSocket.accept();
				InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
				System.out.println("*****연결 수락함******");

				byte[] bytes = null;
				String send = null;

				InputStream in = socket.getInputStream();
				bytes = new byte[100];
				int readByteCount = in.read(bytes);
				send = new String(bytes, 0, readByteCount, "UTF-8");
				System.out.print("패킷 수신 완료");
				Thread.sleep(1000);
				System.out.println(">> 데이터 받기 성공 " + send);

				in.close();
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!serverSocket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}