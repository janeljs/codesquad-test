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
    
    void rotateBack(String cmd) {
        Deque<String> cmdBack = new ArrayDeque<>();
        cmdBack.add(cubeUp[0][0] + cubeUp[0][1] + cubeUp[0][2]);
        cmdBack.add(cubeRight[0][2] + cubeRight[1][2] + cubeRight[2][2]);
        cmdBack.add(cubeDown[2][2] + cubeDown[2][1] + cubeDown[2][0]);
        cmdBack.add(cubeLeft[2][0] + cubeLeft[1][0] + cubeLeft[0][0]);

        cubeBack = rotateInnerCube(cmd, cmdBack, cubeBack);
        String[] temp = saveCommandAsStringArray(cmdBack);

        for (int i = 0; i < 3; i++) {
            cubeUp[0][i] = Character.toString(temp[0].charAt(i));
            cubeRight[i][2] = Character.toString(temp[1].charAt(i));
            cubeDown[2][2 - i] = Character.toString(temp[2].charAt(i));
            cubeLeft[2 - i][0] = Character.toString(temp[3].charAt(i));
        }
        count++;
    }
    
    void rotateLeft(String cmd) {
        Deque<String> cmdLeft = new ArrayDeque<>();
        cmdLeft.add(cubeUp[0][0] + cubeUp[1][0] + cubeUp[2][0]);
        cmdLeft.add(cubeFront[0][0] + cubeFront[1][0] + cubeFront[2][0]);
        cmdLeft.add(cubeDown[0][0] + cubeDown[1][0] + cubeDown[2][0]);
        cmdLeft.add(cubeBack[2][2] + cubeBack[1][2] + cubeBack[0][2]);

        cubeLeft = rotateInnerCube(cmd, cmdLeft, cubeLeft);
        String[] temp = saveCommandAsStringArray(cmdLeft);

        for (int i = 0; i < 3; i++) {
            cubeUp[i][0] = Character.toString(temp[0].charAt(i));
            cubeFront[i][0] = Character.toString(temp[1].charAt(i));
            cubeDown[i][0] = Character.toString(temp[2].charAt(i));
            cubeBack[2 - i][2] = Character.toString(temp[3].charAt(i));
        }
        count++;
    }
    
    void rotateRight(String cmd) {
        Deque<String> cmdRight = new ArrayDeque<>();
        cmdRight.add(cubeUp[0][2] + cubeUp[1][2] + cubeUp[2][2]);
        cmdRight.add(cubeFront[0][2] + cubeFront[1][2] + cubeFront[2][2]);
        cmdRight.add(cubeDown[0][2] + cubeDown[1][2] + cubeDown[2][2]);
        cmdRight.add(cubeBack[2][0] + cubeBack[1][0] + cubeBack[0][0]);

        cubeRight = rotateInnerCube(cmd, cmdRight, cubeRight);
        String[] temp = saveCommandAsStringArray(cmdRight);
        
        for (int i = 0; i < 3; i++) {
            cubeUp[i][2] = Character.toString(temp[0].charAt(i));
            cubeFront[i][2] = Character.toString(temp[1].charAt(i));
            cubeDown[i][2] = Character.toString(temp[2].charAt(i));
            cubeBack[2 - i][0] = Character.toString(temp[3].charAt(i));
        }
        count++;
    }
    void rotateUp(String cmd) {
        Deque<String> cmdUp = new ArrayDeque<>();
        cmdUp.add(cubeLeft[0][0] + cubeLeft[0][1] + cubeLeft[0][2]);
        cmdUp.add(cubeFront[0][0] + cubeFront[0][1] + cubeFront[0][2]);
        cmdUp.add(cubeRight[0][0] + cubeRight[0][1] + cubeRight[0][2]);
        cmdUp.add(cubeBack[0][0] + cubeBack[0][1] + cubeBack[0][2]);

        cubeUp = rotateInnerCube(cmd, cmdUp, cubeUp);
        String[] temp = saveCommandAsStringArray(cmdUp);

        for (int i = 0; i < 3; i++) {
            cubeLeft[0][i] = Character.toString(temp[0].charAt(i));
            cubeFront[0][i] = Character.toString(temp[1].charAt(i));
            cubeRight[0][i] = Character.toString(temp[2].charAt(i));
            cubeBack[0][i] = Character.toString(temp[3].charAt(i));
        }
        count++;
    }
    
    void rotateDown(String cmd) {
        Deque<String> cmdDown = new ArrayDeque<>();
        cmdDown.add(cubeLeft[2][0] + cubeLeft[2][1] + cubeLeft[2][2]);
        cmdDown.add(cubeFront[2][0] + cubeFront[2][1] + cubeFront[2][2]);
        cmdDown.add(cubeRight[2][0] + cubeRight[2][1] + cubeRight[2][2]);
        cmdDown.add(cubeBack[2][0] + cubeBack[2][1] + cubeBack[2][2]);

        cubeDown = rotateInnerCube(cmd, cmdDown, cubeDown);
        String[] temp = saveCommandAsStringArray(cmdDown);
        
        for (int i = 0; i < 3; i++) {
            cubeLeft[2][i] = Character.toString(temp[0].charAt(i));
            cubeFront[2][i] = Character.toString(temp[1].charAt(i));
            cubeRight[2][i] = Character.toString(temp[2].charAt(i));
            cubeBack[2][i] = Character.toString(temp[3].charAt(i));
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
