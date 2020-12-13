package janeljs.test.three;

import java.util.ArrayDeque;
import java.util.Deque;

public class Cube {

    String[][] cubeLeft, cubeFront, cubeRight, cubeUp, cubeBack, cubeDown;
    static int count = 0;
    
    public Cube() {
        cubeLeft = new String[3][3];
        cubeFront = new String[3][3];
        cubeRight = new String[3][3];
        cubeUp = new String[3][3];
        cubeBack = new String[3][3];
        cubeDown = new String[3][3];
        initCube();
    }

    void initCube() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cubeLeft[i][j] = "W";
                cubeFront[i][j] = "O";
                cubeRight[i][j] = "G";
                cubeBack[i][j] = "Y";
                cubeUp[i][j] = "B";
                cubeDown[i][j] = "R";
            }
        }
    }
    
    String[][] rotateClockwise(String[][] cubeC) {
        String[][] updatedCube = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                updatedCube[j][2 - i] = cubeC[i][j];
            }
        }
        return updatedCube;
    }
    
    String[][] rotateCounterclockwise(String[][] cubeC) {
        String[][] updatedCube = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                updatedCube[2 - j][i] = cubeC[i][j];
            }
        }
        return updatedCube;
    }
    
    String[] saveCommandAsStringArray (Deque<String> cmdC) {
        String[] cmdArr = new String[4];
        for (int i = 0; i < 4; i++) {
            cmdArr[i] = cmdC.pop();
        }
        return cmdArr;
    }
    
    void rotateFront(String cmd) {
        Deque<String> cmdFront = new ArrayDeque<>();
        cmdFront.add(cubeUp[2][0] + cubeUp[2][1] + cubeUp[2][2]);
        cmdFront.add(cubeRight[0][0] + cubeRight[1][0] + cubeRight[2][0]);
        cmdFront.add(cubeDown[0][2] + cubeDown[0][1] + cubeDown[0][0]);
        cmdFront.add(cubeLeft[2][2] + cubeLeft[1][2] + cubeLeft[0][2]);
        
        cubeFront = rotateInnerCube(cmd, cmdFront, cubeFront);
        String[] temp = saveCommandAsStringArray(cmdFront);

        for (int i = 0; i < 3; i++) {
            cubeUp[2][i] = Character.toString(temp[0].charAt(i));
            cubeRight[i][0] = Character.toString(temp[1].charAt(i));
            cubeDown[0][2 - i] = Character.toString(temp[2].charAt(i));
            cubeLeft[2 - i][2] = Character.toString(temp[3].charAt(i));
        }
        count++;
    }

    String[][] rotateInnerCube(String cmd, Deque<String> cmdC, String[][] cubeC) {
        if (cmd.equals("F") || cmd.equals("L") || cmd.equals("D")) {
            cmdC.addFirst(cmdC.removeLast());
            String[][] updatedCube = rotateClockwise(cubeC);
            return updatedCube;
        }
        if (cmd.equals("F'") || cmd.equals("L'") || cmd.equals("D'")) {
            cmdC.addLast(cmdC.removeFirst());
            String[][] updatedCube = rotateCounterclockwise(cubeC);
            return updatedCube;
        }
        if (cmd.equals("U") || cmd.equals("R") || cmd.equals("B")) {
            cmdC.addLast(cmdC.removeFirst());
            String[][] updatedCube = rotateClockwise(cubeC);
            return updatedCube;
        }
        cmdC.addFirst(cmdC.removeLast());
        String[][] updatedCube = rotateCounterclockwise(cubeC);
        return updatedCube;
    }
    
   


}
