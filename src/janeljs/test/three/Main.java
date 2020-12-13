package janeljs.test.three;



public class Main {


    public static void main(String[] args) {
        Prompt p = new Prompt();
        p.runPrompt();
        System.out.println("조작갯수: " + Cube.count);
        System.out.println("이용해주셔서 감사합니다. 뚜뚜뚜.");
    }
}
