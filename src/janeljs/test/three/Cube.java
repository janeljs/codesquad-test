package janeljs.test.three;

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

    
   


}
