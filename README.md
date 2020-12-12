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

## 요구사항

- 컴파일 및 실행되지 않을 경우 불합격
- 자기만의 기준으로 최대한 간결하게 코드를 작성한다.
</details>

<br/>

# 구조

입력값을 받아오고 프로그램을 실행시키기 위한 Main class와 단어를 밀어내기 위한 PushWords class를 구현했다.

## 1. Main 클래스

- Scanner를 사용해서 사용자가 입력한 값을 받아온다.

```java
Scanner sc = new Scanner(System.in);
System.out.print(PROMPT);
```

- 단어(word), 주어진 숫자(n), 이동 방향(direction)을 각각 다른 변수에 저장한 뒤 스캐너를 닫아준다.

```java
String word = sc.next();
int n = sc.nextInt();
String direction = sc.next().toLowerCase();
sc.close();
```

- PushWords 인스턴스를 생성한 뒤, isLeft 메서드를 통해 이동 방향을 확인한다.

```java
PushWords pw = new PushWords(word);
boolean left = pw.isLeft(n, direction);
```

- left가 true라면 pushLeft메서드를 실행하고, false라면 pushRight 메서드를 실행 한 뒤, 결과값을 출력한다.

```java
if (left) pw.pushLeft(n);
else pw.pushRight(n);
pw.printWord(pw.deque);
```

<br/>

## 2. PushWords 클래스

| name                         | description                           |
| ---------------------------- | ------------------------------------- |
| checkDirection(n, direction) | 이동 방향 확인                        |
| pushLeft(n)                  | 단어를 n의 절댓값만큼 왼쪽으로 밀기   |
| pushRight(n)                 | 단어를 n의 절댓값만큼 오른쪽으로 밀기 |
