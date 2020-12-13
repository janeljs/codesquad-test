package janeljs.test.three;

import java.util.ArrayList;
import java.util.Scanner;

public class Prompt {
    
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
    
    
}
