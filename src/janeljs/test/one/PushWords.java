package janeljs.test.one;

import java.util.ArrayDeque;
import java.util.Deque;

public class PushWords {
    Deque<Character> deque;

    public PushWords(String word) {
        deque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
    }

    boolean isLeft(int n, String direction) {
        if ((n > 0 && direction.equals("l")) || (n < 0 && direction.equals("r")))
            return true;
        return false;
    }

    void pushLeft(int n) {
        int numOfMovements = Math.abs(n);
        for (int i = 0; i < numOfMovements; i++) {
            deque.addLast(deque.removeFirst());
        }
    }

    void pushRight(int n) {
        int numOfMovements = Math.abs(n);
        for (int i = 0; i < numOfMovements; i++) {
            deque.addFirst(deque.removeLast());
        }
    }

    void printWord(Deque<Character> deque) {
        for (char x : deque) {
            System.out.print(x);
        }
    }

}
