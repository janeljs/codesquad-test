package janeljs.test.two;

import java.util.ArrayDeque;
import java.util.Deque;

public class FlatCube {
	Deque<String> cubeTop, cubeMiddle, cubeBottom;
	
	public FlatCube() {
		cubeTop = new ArrayDeque<>();
		cubeMiddle = new ArrayDeque<>();
		cubeBottom = new ArrayDeque<>();
		
		String[][] cube = {{"R","R","W"},{"G","C","W"},{"G","B","B"}};
		
		for (int i = 0; i<3; i++) {
			cubeTop.add(cube[0][i]);
			cubeMiddle.add(cube[1][i]);
			cubeBottom.add(cube[2][i]);
		}
	}
}
