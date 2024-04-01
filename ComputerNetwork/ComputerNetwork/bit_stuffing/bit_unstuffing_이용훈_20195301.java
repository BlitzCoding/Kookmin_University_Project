import java.util.Scanner;

public class bit_unstuffing_이용훈_20195301 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String next = "";
		int count = 0;

		System.out.print("Stuffed bit 입력 : ");
		String s = sc.next();

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '1') {
				count++;
				next = next + s.charAt(i);
			} else {
				count = 0;
				next = next + s.charAt(i);
			}
			if (count == 5) {
				if ((i + 2) != next.length()) {
					next = next + s.charAt(i + 2);
				} else {
					next = next + '1';
				}
				i = i + 2;
				count = 1;
			}

		}
		System.out.println("Unstuffed bit : " + next);
	}
}
