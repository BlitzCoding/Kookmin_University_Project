import java.util.Scanner;

public class receiver_이용훈_20195301 {

	public static void main(String[] args) {
		String result = "";
		String first = "";
		int level = 1;
		String x = "0";
		Scanner sc = new Scanner(System.in);

		System.out.print("Physical layer에서 받은 MLT 신호 : ");
		String s = sc.next();

		for (int i = 0; i < s.length(); i++) {
			if (i < 1) {
				if (s.charAt(0) == '0') {
					first = "0";
				} else {
					first = "1";
				}

			} else {
				if (s.charAt(i) == '0') {
					if (s.charAt(i - 1) == '0') {
						x = "0";
						result = result + x;
					} else {
						x = "1";
						result = result + x;
					}
				} else {
					if (s.charAt(i - 1) == '0') {
						x = "1";
						result = result + x;
					} else {
						x = "0";
						result = result + x;
					}
				}
			}
		}

		System.out.println("bit stream으로 변환된 신호 : " + first + result);

		String last = first + result;
		int count = 0;
		String next = "";

		for (int i = 0; i < last.length(); i++) {
			if (last.charAt(i) == '1') {
				count++;
				next = next + last.charAt(i);
			} else {
				count = 0;
				next = next + last.charAt(i);
			}
			if (count == 5) {
				if ((i + 2) != next.length()) {
					next = next + last.charAt(i + 2);
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
