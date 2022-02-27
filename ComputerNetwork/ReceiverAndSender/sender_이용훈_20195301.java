import java.util.Scanner;

public class sender_이용훈_20195301 {

	public static void main(String[] args) {
		String next = "";
		int count = 0;
		int level = 1;
		String x = "0";
		String result = new String();

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

		for (int i = 0; i < next.length(); i++) {
			if (next.charAt(i) == '0') {
				result = result + x;
			} else {
				if (x == "0") {
					if (level == 1) {
						x = "+";
						level = -1;
						result = result + x;
					} else {
						x = "-";
						level = 1;
						result = result + x;
					}
				} else {
					x = "0";
					result = result + x;
				}
			}
		}
		System.out.println("Physical layer에서 MLT-3 처리 결과 : " + result);
	}
}
