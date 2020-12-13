# 2단계: 평면 큐브 구현하기

<details>
<summary> 문제 설명 및 요구사항 </summary>

## 문제 설명

3 X 3의 2차원 배열이 아래처럼 있다.

```
R R W
G C W
G B B
```

사용자 입력을 받아서 아래의 동작을 하는 프로그램을 구현하시오

```
> U  가장 윗줄을 왼쪽으로 한 칸 밀기 RRW -> RWR
> U' 가장 윗줄을 오른쪽으로 한 칸 밀기 RRW -> WRR
> R  가장 오른쪽 줄을 위로 한 칸 밀기 WWB -> WBW
> R' 가장 오른쪽 줄을 아래로 한 칸 밀기 WWB -> BWW
> L  가장 왼쪽 줄을 아래로 한 칸 밀기 RGG -> GRG (L의 경우 R과 방향이 반대임을 주의한다.)
> L' 가장 왼쪽 줄을 위로 한 칸 밀기 RGG -> GGR
> B  가장 아랫줄을 오른쪽으로 한 칸 밀기 GBB -> BGB (B의 경우도 U와 방향이 반대임을 주의한다.)
> B' 가장 아랫줄을 왼쪽으로 한 칸 밀기 GBB -> BBG
> Q  Bye~를 출력하고 프로그램을 종료한다.
```

## 요구사항

- 처음 시작하면 초기 상태를 출력한다.
- 간단한 프롬프트 (CLI에서 키보드 입력받기 전에 표시해주는 간단한 글자들 - 예: CUBE> )를 표시해 준다.
- 한 번에 여러 문자를 입력받은 경우 순서대로 처리해서 매 과정을 화면에 출력한다.

## 동작 예시

```
R R W
G C W
G B B

CUBE> UUR

U
R W R
G C W
G B B

U
W R R
G C W
G B B

R
W R W
G C B
G B R

CUBE> Q
Bye~
```

## 2단계 코딩 요구사항

- 너무 크지 않은 함수 단위로 구현하려고 노력할 것
- 전역변수의 사용을 자제할 것
- 객체와 배열을 적절히 활용할 것

</details>

<br/>

# 구조

프롬프트를 실행하는 Main class와 Prompt 관련 메서드를 저장하는 Prompt class, 평면 큐브에 관련된 정보와 push 메서드들이 들어있는 FlatCube class를 구현했다.

### 📑 목차

[1. Main class](#Main-클래스)  
[2. Prompt class](#Prompt-클래스)  
[3. FlatCube class](#FlatCube-클래스)

<br/>

## Main 클래스

Prompt 객체와 runPrompt() 메서드를 활용하여 Main 클래스는 단순하게 구현하였다.

```java
Prompt p = new Prompt();
p.runPrompt();
```

<br/>

## Prompt 클래스

| 메서드                            | 기능                                                                       |
| --------------------------------- | -------------------------------------------------------------------------- |
| getCommandKeyList(scanner)        | 사용자로부터 입력받은 명령어를 저장하는 cmdQueue를 ArrayList의 형태로 반환 |
| selectCommand(flatCube, cmdQueue) | cmdQueue의 명령어를 차례로 실행                                            |
| runPrompt()                       | 간단한 프롬프트 출력 및 selectCommand 메서드 실행                          |

<br/>

> **getCommandKeyList 메서드 : 사용자로부터 입력받은 명령어를 저장하는 cmdQueue를 ArrayList의 형태로 반환**

1. cmd에 사용자의 입력값을 저장하고, 입력값을 명령어 단위로 저장할 수 있는 ArrayList를 선언한다.
2. cmd를 순회하며 명령어를 대문자로 변환하여 cmdKey에 저장한다.
3. 알파벳인 경우 cmdQueue에 cmdKey를 추가하고, `'`가 나온 경우 바로 전 순회 때 추가된 cmdKey에 `'`를 추가해준 뒤 continue 문을 사용해서 다음 순회로 넘어간다.
4. 명령어 리스트가 저장된 cmdQueue를 반환한다.

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
		cmdQueue.add(cmdKey);
	}

	return cmdQueue;
}
```

<br/>

> **selectCommand 메서드 : cmdQueue의 명령어를 차례로 실행**

1. cmdQueue 안에 있는 명령어에 대하여 pushCubeByCommands 메서드와 printFlatCube 메서드를 실행하여 명령에 따라 큐브를 밀고 그 결과값을 출력한다.
2. 만약 평면 큐브를 밀라는 명령어가 아닌 종료 명령어("Q")가 나온다면, isLoop를 false로 바꾼 뒤 프로그램을 종료한다.

```java
void selectCommand(FlatCube cube, ArrayList<String> cmdQueue) {
	for (String x : cmdQueue) {
		if (x.equals("Q")) {
			isLoop = false;
			System.out.println("Bye~");
			break;
		}
		System.out.println("\n" + x);
		cube.pushCubeByCommands(x);
		cube.printFlatCube();
	}
}
```

<br/>

> **runPrompt 메서드 : 간단한 프롬프트 출력 및 selectCommand 메서드 실행**

1. FlatCube 객체를 생성한 뒤, 초기 상태를 출력한다.
2. 이후 "Q" 명령을 받아 `isLoop=false`가 되기 전까지 간단한 프롬프트(CUBE> )를 출력하고, 입력받은 명령어를 실행할 수 있는 selectCommand 메서드를 호출한다.
3. 스캐너를 닫아준다.

```java
void runPrompt() {
	FlatCube cube = new FlatCube();
	cube.printFlatCube();

	Scanner sc = new Scanner(System.in);

	final String PROMPT = "CUBE> ";

	while (isLoop) {
		System.out.print("\n" + PROMPT);
		selectCommand(cube, getCommandKeyList(sc));
	}
	sc.close();
}
```

<br/>

## FlatCube 클래스

1. 단어 밀어내기 구현을 위한 deque를 선언한다.

```java
Deque<String> cubeTop, cubeMiddle, cubeBottom;
```

2. 생성자에서 각 인스턴스 변수에 대한 객체를 생성해주고, 생성된 객체를 문제에서 지정한 초기값으로 초기화해준다.

```java
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
```

3. 단어 밀어내기 및 큐브 출력에 필요한 메서드를 정의한다.

| 메서드                    | 기능                                    |
| ------------------------- | --------------------------------------- |
| pushCubeByCommands(cmd)   | 명령어 문자열에 따라 밀기 메서드를 실행 |
| pushLeft(deque)           | deque를 왼쪽으로 한 칸 밀기             |
| pushRight(deque)          | deque를 오른쪽으로 한 칸 밀기           |
| pushLeftUp()              | 가장 왼쪽 줄을 위로 한 칸 밀기          |
| pushLeftDown()            | 가장 왼쪽 줄을 아래로 한 칸 밀기        |
| pushRightUp()             | 가장 오른쪽 줄을 위로 한 칸 밀기        |
| pushRightDown()           | 가장 오른쪽 줄을 아래로 한 칸 밀기      |
| pushCubeComponents(deque) | 입력받은 deque을 출력                   |
| printFlatCube()           | 평면 큐브를 출력                        |

<br/>

> **pushCubeByCommands 메서드 : 명령어 문자열에 따라 밀기 메서드를 실행**

1. Prompt class에서 if~else 또는 switch~case문 사용을 피하기 위해 HashMap에 명령어 별 메서드를 저장하였다.
2. pushCubeByCommands함수가 실행될 때마다 cmd 문자열을 키로 갖고있는 메서드가 실행된다.

```java
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
```

<br/>

> **pushLeft 메서드 : deque를 왼쪽으로 한 칸 밀기**  
> **pushRight 메서드 : deque를 오른쪽으로 한 칸 밀기**

1. deque의 첫 번째 원소를 꺼내어 마지막에 삽입하면 큐브가 왼쪽으로 한 칸 밀어지며, 반대의 경우에는 오른쪽으로 한 칸 밀어진다.
2. 매개변수로는 좌, 우로 이동하는 cubeTop 또는 cubeBottom을 넣으면 된다.

```java
void pushLeft(Deque<String> deque) {
	deque.addLast(deque.removeFirst());
}

void pushRight(Deque<String> deque) {
	deque.addFirst(deque.removeLast());
}
```

<br/>

> **pushLeftUp 메서드 : 가장 왼쪽 줄을 위로 한 칸 밀기**

1. FlatCube는 cubeTop, cubeMiddle, cubeBottom에 저장된 deque으로 구현되어 있기 때문에 위, 아래 구현의 경우 좌, 우에 비하여 약간 더 복잡하다.
2. temp 변수에 cubeTop의 첫번째 원소를 저장해둔 뒤, cubeMiddle의 첫 번째 원소를 꺼내어 cubeTop의 맨 앞에, cubeBottom의 첫 번째 원소를 꺼내어 cubeMiddle 맨 앞에 삽입한다.
3. 마지막으로 temp에 저장되어 있던 값을 cubeBottom의 맨 앞에 삽입해주면 된다.
4. pushLeftDown, pushRightUp, pushRightDown 메서드의 경우에도 동일한 로직으로 구현할 수 있다.

```java
void pushLeftUp() {
		String temp = cubeTop.removeFirst();
		cubeTop.addFirst(cubeMiddle.removeFirst());
		cubeMiddle.addFirst(cubeBottom.removeFirst());
		cubeBottom.addFirst(temp);
	}
```

<br/>

> **pushCubeComponents 메서드 : 입력받은 deque을 출력**  
> **printFlatCube 메서드 : 평면 큐브를 출력**

1. 들여쓰기를 최소화하기 위해 큐브를 출력하는 메서드를 두 개로 분할했다.
2. printFlatCube 메서드 호출 시 평면 큐브의 형태로 값이 출력된다.

```java
void printCubeComponents(Deque<String> deque) {
	for (String x : deque) {
		System.out.print(x + " ");
	}
	System.out.println();
}

void printFlatCube() {
	printCubeComponents(cubeTop);
	printCubeComponents(cubeMiddle);
	printCubeComponents(cubeBottom);
}
```
