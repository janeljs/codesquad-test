package janeljs.test.one;

import java.util.Scanner;

public class Main {
	private static final String PROMPT = "> ";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print(PROMPT);

		String word = sc.next();
		int n = sc.nextInt();
		String direction = sc.next().toLowerCase();

		sc.close();
		
		PushWords pw = new PushWords(word);

		if (pw.isLeft(n, direction)) pw.pushLeft(n);
		else pw.pushRight(n);
		
		pw.printWord(pw.deque);
	}
}
