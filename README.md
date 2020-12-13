# 3단계: 루빅스 큐브 구현하기

<details>
<summary> 문제 설명 및 요구사항 </summary>

## 문제 설명

- 큐브는 W, B, G, Y, O, R의 6가지 색깔을 가지고 있다.
- 입력: 각 조작법을 한 줄로 입력받는다.
- 출력: 큐브의 6면을 펼친 상태로 출력한다.
- Q를 입력받으면 프로그램을 종료하고, 조작 받은 명령의 갯수를 출력시킨다.

<br/>

## 큐브의 초기 상태

```
                B B B
                B B B
                B B B

 W W W     O O O     G G G     Y Y Y
 W W W     O O O     G G G     Y Y Y
 W W W     O O O     G G G     Y Y Y

                R R R
                R R R
                R R R
```

## 프로그램 예시

```
(초기 상태 출력)

CUBE> FRR'U2R

F
(큐브상태)

R
(큐브상태)

...

R
(큐브상태)

CUBE> Q
경과시간: 00:31 //추가 구현 항목
조작갯수: 6
이용해주셔서 감사합니다. 뚜뚜뚜.
```

## 추가 구현 기능

- 프로그램 종료 시 경과 시간 출력
- 큐브의 무작위 섞기 기능
- 모든 면을 맞추면 축하 메시지와 함께 프로그램을 자동 종료

<br/>

## 3단계 코딩 요구사항

- 가능한 한 커밋을 자주 하고 구현의 의미가 명확하게 전달되도록 커밋 메시지를 작성할 것
- 함수나 메소드는 한 번에 한 가지 일을 하고 가능하면 20줄이 넘지 않도록 구현한다.
- 함수나 메소드의 들여쓰기를 가능하면 적게(3단계까지만) 할 수 있도록 노력해 본다.

```
function main() {
      for() { // 들여쓰기 1단계
          if() { // 들여쓰기 2단계
              return; // 들여쓰기 3단계
          }
      }
  }
```

</details>

<br/>

# 구조

프롬프트를 실행하는 Main class와 Prompt 관련 메서드를 저장하는 Prompt class, 큐브에 관련된 정보와 push 메서드들이 들어있는 Cube class를 구현하였다.

### 📑 목차

[1. Main class](#Main-클래스)  
[2. Prompt class](#Prompt-클래스)  
[3. Cube class](#Cube-클래스)

<br/>

## Main 클래스

1. 큐브의 동작과 직접적으로 관련된 메서드들은 Prompt 클래스 안에 담아주었고, Main class는 Prompt 객체를 생성하고 runPrompt()를 실행하는 형태로 간단하게 구현하였다.
2. runPrompt()실행 전에 startTime 변수에 시간을 저장하고, 실행을 마친 후에 endTime 변수에 시간을 저장함으로써 `프로그램 종료 시 경과 시간 출력` 기능을 구현했다.

```java
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
```

## Prompt 클래스

| 메서드                        | 기능                                                                          |
| ----------------------------- | ----------------------------------------------------------------------------- |
| getCommandKeyList(scanner)    | 사용자로부터 입력받은 명령어를 저장하는 cmdQueue를 ArrayList의 형태로 반환    |
| isInteger(cmd)                | 문자열 cmd를 정수로 변환할 수 있을 경우 true, 변환이 불가능할 경우 false 반환 |
| selectCommand(cube, cmdQueue) | cmdQueue의 명령어를 차례로 실행                                               |
| runPrompt()                   | 간단한 프롬프트 출력 및 사용자의 입력을 바탕으로 프로그램 실행                |

<br/>

> **getCommandKeyList 메서드 : 사용자로부터 입력받은 명령어를 저장하는 cmdQueue를 ArrayList의 형태로 반환**  
> **isInteger 메서드 : 문자열 cmd를 정수로 변환할 수 있을 경우 true, 변환이 불가능할 경우 false 반환**

1. step-2의 getCommandKeyList 메서드와 거의 유사하지만, step-3에서는 F2와 같은 입력이 존재한다는 차이점이 있다.
2. 먼저 문자열 cmd를 정수로 변환할 수 있을 경우 true, 변환이 불가능할 경우 false 반환하는 isInteger 메서드를 정의해주었다.
3. isInteger 메서드 실행 결과 cmdKey가 정수라면 cmdKey를 정수로 변환하여 n에 저장한다.
4. n회 만큼 숫자 바로 전에 나왔던 명령어 알파벳을 cmdQueue에 넣어주면 된다.

```java
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
```

> **selectCommand 메서드 : cmdQueue의 명령어를 차례로 실행**

1. step-2의 selectCommand 메서드와 거의 비슷하지만, step-3에서는 큐브를 무작위로 섞을 수 있는 "S"라는 명령어를 추가하였다.
2. "S"가 나올 경우 scrambleCube()메서드를 실행하여 큐브를 섞고, "Q"가 나올 경우 프로그램을 종료한다.
3. 이외에 회전과 관련된 명령어일 경우에는 rotateCube()와 printRubiksCube를 차례로 실행한다.

```java
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
```

> **runPrompt 메서드 : 간단한 프롬프트 출력 및 사용자의 입력을 바탕으로 프로그램 실행**

1. step-2의 runPrompt 메서드와 유사하지만, 정답을 확인하고 축하메시지를 출력하는 부분을 추가하였다.
2. checkAnswer 메서드 실행 결과가 true이면 축하메시지가 출력되고 프로그램은 자동으로 종료된다.

```java
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
```

## Cube 클래스

1. 루빅스 큐브의 6개 면을 저장할 2차원 배열을 인스턴스 변수로 선언해 주었다.
2. COMMAND_KEYS는 scrambleCube 메서드를 위해, count는 총 조작갯수를 구하기 위해 선언해주었다.

```java
    String[][] cubeLeft, cubeFront, cubeRight, cubeUp, cubeBack, cubeDown;
    private final String[] COMMAND_KEYS = { "L", "L'", "R", "R'", "U", "U'", "D", "D'", "F", "F'", "B", "B'" };
    static int count = 0;
```

3. 생성자에서 2차원 배열 객체를 생성하고, initCube 메서드를 호출하여 초깃값을 부여하였다.

```java
    public Cube() {
        cubeLeft = new String[3][3];
        cubeFront = new String[3][3];
        cubeRight = new String[3][3];
        cubeUp = new String[3][3];
        cubeBack = new String[3][3];
        cubeDown = new String[3][3];
        initCube();
    }
```

4. 큐브와 관련된 다양한 메서드들을 정의하였다. 메서드의 크기를 줄이기 위해 정의한 하위 메서드 중 일부는 아래 목록에서 제외하였다.

| 메서드                        | 기능                                                 |
| ----------------------------- | ---------------------------------------------------- |
| initCube()                    | 큐브를 다 맞춰진 상태로 초기화                       |
| scrambleCube()                | 큐브를 무작위로 섞기                                 |
| rotateCube(cmd)               | 명령어 문자열에 따라 회전 메서드를 실행              |
| rotateClockwise(cubeC)        | 큐브의 한 면을 시계 방향으로 회전                    |
| rotateCounterclockwise(cubeC) | 큐브의 한 면을 시계 반대 방향으로 회전               |
| rotateFront(cmd)              | 큐브의 앞면을 90도 회전                              |
| rotateBack(cmd)               | 큐브의 뒷면을 90도 회전                              |
| rotateLeft(cmd)               | 큐브의 왼쪽 면을 90도 회전                           |
| rotateRight(cmd)              | 큐브의 오른쪽 면을 90도 회전                         |
| rotateUp(cmd)                 | 큐브의 윗면을 90도 회전                              |
| rotateDown(cmd)               | 큐브의 바닥을 90도 회전                              |
| printRubiksCube()             | 큐브의 6면을 2차원으로 펼쳐진 상태로 출력            |
| checkAnswer()                 | 큐브가 완전히 맞춰졌다면 true, 아니라면 false를 반환 |

initCube() 큐브를 다 맞춰진 상태로 초기화
scrambleCube() 큐브를 무작위로 섞기
rotateCube(cmd) 명령어 문자열에 따라 회전 메서드를 실행
rotateClockwise(cubeC) 큐브의 한 면을 시계 방향으로 회전
rotateCounterclockwise(cubeC) 큐브의 한 면을 시계 반대 방향으로 회전
rotateFront(cmd) 큐브의 앞면을 90도 회전
rotateBack(cmd) 큐브의 뒷면을 90도 회전
rotateLeft(cmd) 큐브의 왼쪽 면을 90도 회전
rotateRight(cmd) 큐브의 오른쪽 면을 90도 회전
rotateUp(cmd) 큐브의 윗면을 90도 회전
rotateDown(cmd) 큐브의 바닥을 90도 회전
printRubiksCube() 큐브의 6면을 2차원으로 펼쳐진 상태로 출력
checkAnswer() 큐브가 완전히 맞춰졌다면 true, 아니라면 false를 반환
