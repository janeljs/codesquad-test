# 1단계: 단어 밀어내기 구현하기

<details>
<summary> 문제 설명 및 요구사항 </summary>

## 문제 설명

1. 입력: 사용자로부터 단어 하나, 정수 숫자 하나( -100 <= N < 100) , L 또는 R을 입력받는다. L 또는 R은 대소문자 모두 입력 가능하다.
2. 주어진 단어를 L이면 주어진 숫자 갯수만큼 왼쪽으로, R이면 오른쪽으로 밀어낸다.
3. 밀려나간 단어는 반대쪽으로 채워진다.

## 입력 및 출력 예시

홀수 줄은 입력, 짝수 줄은 출력이다.

```
> apple 3 L
leapp

> banana 6 R
banana

> carrot -1 r
arrotc

> cat -4 R
atc
```

## 1단계 코딩 요구사항

- 컴파일 및 실행되지 않을 경우 불합격
- 자기만의 기준으로 최대한 간결하게 코드를 작성한다.
</details>

<br/>

# 구조

입력값을 받아오고 프로그램을 실행시키기 위한 Main class와 단어를 밀어내기 위한 PushWords class를 구현했다.

### 📑 목차

[1. Main class](#Main-클래스)  
[2. PushWords class](#PushWords-클래스)

<br/>

## Main 클래스

1. PROMPT를 final 변수로 선언한다.

```java
private static final String PROMPT = "> ";
```

2. Scanner를 사용해서 사용자가 입력한 값을 받아온다.

```java
Scanner sc = new Scanner(System.in);
System.out.print(PROMPT);
```

3. 단어(word), 주어진 숫자(n), 이동 방향(direction)을 각각 다른 변수에 저장한 뒤 스캐너를 닫아준다.

```java
String word = sc.next();
int n = sc.nextInt();
String direction = sc.next().toLowerCase();
sc.close();
```

4. PushWords 인스턴스를 생성한 뒤, isLeft가 true라면 pushLeft메서드를 실행하고, false라면 pushRight 메서드를 실행한다.

```java
PushWords pw = new PushWords(word);

if (pw.isLeft(n, direction)) pw.pushLeft(n);
else pw.pushRight(n);
```

5. 결과값을 출력한다.

```java
pw.printWord(pw.deque);
```

<br/>

## PushWords 클래스

1. 단어 밀어내기 구현을 위한 deque을 선언한다.

```java
Deque<Character> deque;
```

2. word를 인자로 받고, word 문자열의 각 글자를 deque에 집어넣는 생성자를 정의한다.

```java
    public PushWords(String word) {
        deque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
    }
```

3. 단어 밀어내기 구현에 필요한 메서드를 정의한다.

| 메서드               | 기능                                                           |
| -------------------- | -------------------------------------------------------------- |
| isLeft(n, direction) | 이동 방향 확인, 왼쪽으로 밀어야 되면 true, 아니면 false를 반환 |
| pushLeft(n)          | 단어를 n의 절댓값만큼 왼쪽으로 밀기                            |
| pushRight(n)         | 단어를 n의 절댓값만큼 오른쪽으로 밀기                          |
| printWord(deque)     | 단어 출력                                                      |

<br/>

> **isLeft 메서드 : 이동 방향 확인, 왼쪽으로 밀어야 되면 true, 아니면 false를 반환**

n이 양수 일 때는 입력한 문자대로, 음수일 때는 반대 방향으로 push된다는 점을 고려하여 코드를 설계하였다.

```java
    boolean isLeft(int n, String direction) {
        if ((n > 0 && direction.equals("l")) || (n < 0 && direction.equals("r")))
            return true;
        return false;
    }
```

<br/>

> **pushLeft 메서드 : 단어를 n의 절댓값만큼 왼쪽으로 밀기**

n이 음수인 경우를 고려하여, 이동 횟수를 저장하는 변수(numOfMovements)에 n의 절댓값을 저장하였다. deque의 첫 번째 원소를 꺼내어 마지막에 삽입하는 작업을 이동 횟수만큼 반복하면, 주어진 숫자 갯수만큼 단어를 밀어내고 밀려나간 단어를 반대쪽에 채울 수 있다.

```java
    void pushLeft(int n) {
        int numOfMovements = Math.abs(n);
        for (int i = 0; i < numOfMovements; i++) {
            deque.addLast(deque.removeFirst());
        }
    }
```

<br/>

> **pushRight 메서드 : 단어를 n의 절댓값만큼 오른쪽으로 밀기**

pushLeft와 같은 방법으로 구현할 수 있다. 이 경우에는 deque의 마지막 원소를 꺼내어 deque의 첫 번째 원소로 삽입해주면 된다.

```java
    void pushRight(int n) {
        int numOfMovements = Math.abs(n);
        for (int i = 0; i < numOfMovements; i++) {
            deque.addFirst(deque.removeLast());
        }
    }
```

<br/>

> **printWord 메서드 : 단어 출력**

pushLeft 또는 pushRight 메서드를 실행 한 뒤, deque를 순회하며 요소들을 출력하면 결과값을 얻을 수 있다.

```java
    void printWord(Deque<Character> deque) {
        for (char x : deque) {
            System.out.print(x);
        }
    }
```
