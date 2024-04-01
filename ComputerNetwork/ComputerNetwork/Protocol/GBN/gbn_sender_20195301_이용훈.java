import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class gbn_sender_20195301_이용훈 {

	public static void main(String[] args) throws IOException {
		Socket sender = new Socket("localhost", 1013);

		BufferedInputStream in = new BufferedInputStream(sender.getInputStream());
		DataOutputStream out = new DataOutputStream(sender.getOutputStream());
		Scanner sc = new Scanner(System.in);
		Random rand = new Random();

		System.out.println("*****연결 수락함*****");
		System.out.print("요청할 패킷의 수를 입력하세요 : ");
		int n = sc.nextInt();
		System.out.println();

		out.write(n);
		out.flush();

		int choice = rand.nextInt(5);
		out.write(choice);

		int check = 0;
		int i = 0;

		System.out.println("================");
		if (choice == 0) { // 완벽하게 수신되는 케이스
			for (int j = 0; j < n; ++j) {
				i = in.read();
				System.out.println("송신한 패킷 : " + i);
				out.write(i);
				out.flush();
			}
			out.flush();
		} else { // 수신 오류가 나는 케이스
			for (int j = 0; j < n; ++j) {
				i = in.read();
				if (i == check) {
					System.out.println("송신한 패킷 : " + i);
					out.write(1);
					++check;
				} else {
					System.out.println("버리는 패킷(Discard) : " + (i - 1));
					j--;
					System.out.println("패킷 재전송");
					out.write(-1);
				}
				out.flush();
			}
		}

		in.close();
		out.close();
		System.out.println("*****송신완료*****");
	}
}
