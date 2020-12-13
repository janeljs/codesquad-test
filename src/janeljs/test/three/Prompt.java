package janeljs.test.three;

import java.util.ArrayList;
import java.util.Scanner;

public class Prompt {
    private final String PROMPT = "CUBE> ";
    private boolean isLoop = true;
    
    void runPrompt() {
        Cube cube = new Cube();
        cube.printRubiksCube();

        Scanner sc = new Scanner(System.in);

        while (isLoop) {
            System.out.print("\n" + PROMPT);
            selectCommand(cube, getCommandKeyList(sc));
            if(cube.checkAnswer()) {
                System.out.println("\n🎉 정답입니다. 축하드립니다. 뚜뚜뚜.");
                isLoop = false;
            }
        }
        sc.close();
    }
    
    boolean isInteger(String cmd) {
        try {

            Integer.parseInt(cmd);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
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
            
            int n = 0;
            if (isInteger(cmdKey)) n = Integer.parseInt(cmdKey);

            for (int j = 0; j < n - 1; j++) {
                cmdQueue.add(cmdQueue.get(i - 1 - countSingleQuote));
            }

            if (!isInteger(cmdKey)) cmdQueue.add(cmdKey);
        }
        return cmdQueue;
    }
    
    void selectCommand(Cube cube, ArrayList<String> cmdQueue) {
        for (String x : cmdQueue) {
            if (x.equals("S")) {
                System.out.println("\nNewly Scrambled Rubik's Cube");
                cube.scrambleCube();
                cube.printRubiksCube();
                break;
            }
            if (x.equals("Q")) {
                isLoop = false;
                break;
            }
            System.out.println("\n" + x);
            cube.rotateCube(x);
            cube.printRubiksCube();
        }
    }
    
    
}
