package janeljs.test.two;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class FlatCube {
    Deque<String> cubeTop, cubeMiddle, cubeBottom;

    public FlatCube() {
        cubeTop = new ArrayDeque<>();
        cubeMiddle = new ArrayDeque<>();
        cubeBottom = new ArrayDeque<>();

        String[][] cube = { { "R", "R", "W" }, { "G", "C", "W" }, { "G", "B", "B" } };

        for (int i = 0; i < 3; i++) {
            cubeTop.add(cube[0][i]);
            cubeMiddle.add(cube[1][i]);
            cubeBottom.add(cube[2][i]);
        }

    }

    void pushCubeByCommands(String cmd) {
        Map<String, Runnable> commands = new HashMap<>();
        commands.put("U", () -> pushLeft(cubeTop));
        commands.put("U'", () -> pushRight(cubeTop));
        commands.put("R", () -> pushRightUp());
        commands.put("R'", () -> pushRightDown());
        commands.put("L", () -> pushLeftDown());
        commands.put("L'", () -> pushLeftUp());
        commands.put("B", () -> pushRight(cubeBottom));
        commands.put("B'", () -> pushLeft(cubeBottom));
        commands.get(cmd).run();
    }

    void pushLeft(Deque<String> deque) {
        deque.addLast(deque.removeFirst());
    }

    void pushRight(Deque<String> deque) {
        deque.addFirst(deque.removeLast());
    }

    void pushLeftUp() {
        String temp = cubeTop.removeFirst();
        cubeTop.addFirst(cubeMiddle.removeFirst());
        cubeMiddle.addFirst(cubeBottom.removeFirst());
        cubeBottom.addFirst(temp);
    }

    void pushLeftDown() {
        String temp = cubeBottom.removeFirst();
        cubeBottom.addFirst(cubeMiddle.removeFirst());
        cubeMiddle.addFirst(cubeTop.removeFirst());
        cubeTop.addFirst(temp);
    }

    void pushRightUp() {
        String temp = cubeTop.removeLast();
        cubeTop.add(cubeMiddle.removeLast());
        cubeMiddle.add(cubeBottom.removeLast());
        cubeBottom.add(temp);
    }

    void pushRightDown() {
        String temp = cubeBottom.removeLast();
        cubeBottom.add(cubeMiddle.removeLast());
        cubeMiddle.add(cubeTop.removeLast());
        cubeTop.add(temp);
    }

    void printFlatCube() {
        printCubeComponents(cubeTop);
        printCubeComponents(cubeMiddle);
        printCubeComponents(cubeBottom);
    }

    void printCubeComponents(Deque<String> deque) {
        for (String x : deque) {
            System.out.print(x + " ");
        }
        System.out.println();
    }

}
