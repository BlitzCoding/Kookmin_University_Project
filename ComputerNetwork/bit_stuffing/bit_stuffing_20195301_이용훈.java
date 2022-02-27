import java.util.Scanner;

public class bit_stuffing_20195301_이용훈 {

	public static void main(String[] args) {
		String next = "";
		int count = 0;

		Scanner sc = new Scanner(System.in);

		System.out.print("bit 입력 : ");
		String s = sc.next();

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '1') {
				count++;
				next = next + s.charAt(i);
			} else {
				next = next + s.charAt(i);
				count = 0;
			}

			if (count == 5) {
				next = next + '0';
				count = 0;
			}
		}

		System.out.println("Stuffed bit : " + next);

	}

}
