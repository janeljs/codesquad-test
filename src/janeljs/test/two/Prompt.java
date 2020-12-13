package janeljs.test.two;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Prompt {
	private boolean isLoop = true;

	void runPrompt() {
		FlatCube cube = new FlatCube();
		cube.printFlatCube();

		Scanner sc = new Scanner(System.in);

		final String PROMPT = "CUBE> ";
		
		
		while (isLoop) {
			System.out.print("\n" + PROMPT);
			selectCommand(cube, getCommandKeyList(sc));
		}
		sc.close();
	}

	Queue<String> getCommandKeyList(Scanner sc) {
		String cmd = sc.nextLine();
		String cmdKey = null;
		Queue<String> cmdQueue = new LinkedList<>();
		for (int i = 0; i < cmd.length(); i++) {
			cmdKey = "" + cmd.charAt(i);
			if (i < cmd.length() - 1) {
				String cmdNext = "" + cmd.charAt(i + 1);
				if (cmdNext.equals("'")) {
					cmdKey = cmdKey + cmdNext;
					i++;
				}
			}
			cmdQueue.add(cmdKey);
		}
		return cmdQueue;
	}

	void selectCommand(FlatCube cube, Queue<String> cmdQueue) {
		for (String x : cmdQueue) {
			if (x.equals("Q")) {
				isLoop = false;
				System.out.println("Bye~");
				break;
			}
			System.out.println("\n" + x);
			cube.pushCubeByCommands(x);
			cube.printFlatCube();
		}
	}
}
