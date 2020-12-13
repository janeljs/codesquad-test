package janeljs.test.three;



public class Main {
    static long startTime, endTime, totalTime;

    public static void main(String[] args) {
        startTime = System.nanoTime();
        Prompt p = new Prompt();
        p.runPrompt();
        endTime   = System.nanoTime();
        totalTime = endTime - startTime;
        double seconds = (double) totalTime / 1_000_000_000.0;
        long t = Math.round(seconds);
        
        System.out.printf("경과시간: %02d:%02d\n", t / 60, t % 60);
        System.out.println("조작갯수: " + Cube.count);
        System.out.println("이용해주셔서 감사합니다. 뚜뚜뚜.");
    }
}
