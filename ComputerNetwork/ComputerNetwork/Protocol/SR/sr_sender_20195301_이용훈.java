
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class sr_sender_20195301_�̿��� {

	private Socket socket = null;
	private DataInputStream input = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	ArrayList<Integer> a = new ArrayList<>();
	Scanner sc = new Scanner(System.in);

	public sr_sender_20195301_�̿���(String address, int port) throws InterruptedException {

		try {
			socket = new Socket(address, port);
			System.out.println("*****�����ڿ� �����******");

			input = new DataInputStream(System.in);
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

			out = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException i) {
			System.out.println(i);
		}

		String line = "";

		System.out.print("�۽� Window�� ũ�⸦ �Է��ϼ��� : "); // pipe
		int window = sc.nextInt();
		System.out.print("�۽��� �������� ũ�⸦ �Է��ϼ��� : ");
		int frames = sc.nextInt();
		try {
			out.writeUTF(String.valueOf(window));
			out.writeUTF(String.valueOf(frames));

		} catch (IOException e) {
			e.printStackTrace();
		}
		while (true) {
			try {
				line = in.readUTF();
				if (line.equals("Stop")) {
					System.out.println("��Ŷ ���� �Ϸ�!");
					break;
				} else if (line.equals("Window start")) {
					a.clear();
					a.add(0);
					System.out.println("=============================");
					String num = in.readUTF();
					while (!num.equals("Window end")) {
						a.add(Integer.parseInt(num));
						num = in.readUTF();
					}
					Random r = new Random();

					int corr = a.get(r.nextInt(a.size()));
					for (int i = 1; i < a.size(); i++) {
						Thread.sleep(1000);
						int sample = a.get(i).intValue();
						if (sample != corr)
							System.out.println("��Ŷ " + sample + " ���۵���");
						else
							System.out.println("��Ŷ " + sample + " ����!");
					}
					out.writeUTF(String.valueOf(corr));
					System.out.println("=============================");

				}
			} catch (IOException i) {
				System.out.println(i);
			}
		}

		try {
			input.close();
			out.close();
			socket.close();
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public static void main(String args[]) throws InterruptedException {
		sr_sender_20195301_�̿��� client = new sr_sender_20195301_�̿���("localhost", 1013);
	}
}