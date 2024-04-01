import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class simple_receiver_20195301_�̿��� {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress("localhost", 1013));

			while (true) {
				System.out.println("*****���� ��ٸ�******");
				socket = serverSocket.accept();
				InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
				System.out.println("*****���� ������******");

				byte[] bytes = null;
				String send = null;

				InputStream in = socket.getInputStream();
				bytes = new byte[100];
				int readByteCount = in.read(bytes);
				send = new String(bytes, 0, readByteCount, "UTF-8");
				System.out.print("��Ŷ ���� �Ϸ�");
				Thread.sleep(1000);
				System.out.println(">> ������ �ޱ� ���� " + send);

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