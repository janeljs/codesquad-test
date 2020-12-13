package janeljs.test.two;

import java.util.ArrayList;
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

    ArrayList<String> getCommandKeyList(Scanner sc) {
        String cmd = sc.nextLine();
        ArrayList<String> cmdQueue = new ArrayList<>();
        int countSingleQuote = 0;
        for (int i = 0; i < cmd.length(); i++) {
            String cmdKey = Character.toString(cmd.charAt(i)).toUpperCase();

            if (cmdKey.equals("'")) {
                countSingleQuote++;
                cmdQueue.set(i - countSingleQuote, cmdQueue.get(i - countSingleQuote) + "'");
                continue;
            }
            cmdQueue.add(cmdKey);
        }

        return cmdQueue;
    }

    void selectCommand(FlatCube cube, ArrayList<String> cmdQueue) {
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
