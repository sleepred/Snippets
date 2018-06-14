# Thread

 
## 간단한 Thread 사용

```java
Runnable runnable = new Runnable() {
    try {
        String threadName = Thread.currentThread().getName();
        [[처리 로직]]
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
};
 
Thread thread = new Thread(runnable);
thread.start();
```

## 정해진 수의 Thread만 사용하는 ThreadPool

```java
ExecutorService threadPool = Executors.newFixedThreadPool(
        // CPU 코어의 수만큼 최대 스레드를 사용하는 스레드풀을 생성
        Runtime.getRuntime().availableProcessors()
        );
 
Future future = threadPool.submit(new Runnable() {
    @Override
    public void run() {
        [[처리 로직 또는 다른 메서드 호출]]
    }
});
 
future.get();
 
// 작업 큐에 대기하고 있는 모든 작업을 처리한 뒤에 스레드풀을 종료
executorService.shutdown();
```


## 최대 최소 Thread 수, 일정 시간 이상 노는 Thread는 자동으로 제거하는 ThreadPool

```java
ExecutorService threadPool = new ThreadPoolExecutor(
    3,      // 코어 스레드 개수
    100,    // 최대 스레드 개수
    120L,   // 놀고 있는 시간
    TimeUnit.SECONDS,   // 놀고 있는 시간 단위
    new SynchronousQueue<Runnable>()    // 작업 큐
);
 
Future future = threadPool.submit(new Runnable() {
    @Override
    public void run() {
        [[처리 로직 또는 다른 메서드 호출]]
    }
});
 
future.get();
 
// 작업 큐에 대기하고 있는 모든 작업을 처리한 뒤에 스레드풀을 종료
executorService.shutdown();
```


## 참고 사이트
 - http://blog.eomdev.com/java/2016/04/06/Multi-Thread.html




## 쓰레드 사용하기
 - 쓰레드(Thread) 두 개를 만들어 실행시켜 보자
 - 두 가지 쓰레드 작성법을 배운다.
 - 인터페이스 Runable을 구현하거나 쓰레드 클래스를 상속한다.
```java
public class  MyRun implements Runnable
{
    public void run(){
        show();
    }
    public void show(){
        for(int i=0;i<100;i++){
            System.out.print("S");
        }
    }
}
```
```java
public class MyThread extends Thread
{
    public void run(){
        for(int i=0;i<100;i++){
            System.out.print("T");
        }
    }
}
```
```java
public class   MyRunMain{
    public static void main(String[] args)
    {
        MyRun mr1=new MyRun();
        Thread t1=new Thread(mr1);
        MyThread t2=new MyThread();
        t1.start();
        t2.start();
        for(int i=0;i<100;i++){
            System.out.print("M");
        }
    }
}
```

## 쓰레드와 sleep 메서드
 - 50밀리세컨드마다 쓰레드 이름을 출력하게 만들어 보자.
 - 쓰레드와 sleep 메서드의 사용법을 배운다.
 - Thread.sleep 메서드를 사용하고 예외처리를 해준다.
```java
public class  SleepThread extends Thread{
    public SleepThread(String name){
        setName(name);
    }
    public void run(){show();}
    public void show(){
        for(int i=0 ;i<50;i++){
            print();
            try{
                Thread.sleep(50);//50/1000 초
            }catch(InterruptedException ite){}
        }
    }
    public void print(){
            System.out.print(getName());//Thread에서
    }
}
```
```java
public class  SleepThreadMain{
    public static void main(String[] args) {
        SleepThread t1=new SleepThread("a");
        SleepThread t2=new SleepThread("b");
        SleepThread t3=new SleepThread("c");
 
        t2.setPriority(7);//1~10 클수록 우서순의
        t1.start();//t2가 t1보다 우선이지만
        try{
            t1.join();//t1을 끝낸후 t2, t3를 실행한다.
        }catch(InterruptedException ite){}
        t2.start();
        t3.start();
    }
}
```

## 쓰레드와 wait, notifyAll 메서드 이해하기
- 필요한 자원이 준비될 때까지 쓰레드 작업을 지연시켜 보자.
- 공유된 데이터의 신뢰성을 높이는 방법과 쓰레드 간 통신을 이해한다.
- 동기화된 메서드에 대한 wait과 nofifyAll 메서드를 사용한다.

```java
public class CakePlate {
    private int breadCount=0;
    public CakePlate() {
    }
    public synchronized void makeBread(){
        if(breadCount>=10){
            try{
                System.out.println("빵이 남는다.");
                wait();
            }catch(InterruptedException ire){}
        }
        breadCount++;//빵이 10개가 안되면 더 만들자.
        System.out.println("빵을 1개 더 만듬  총 : "+breadCount+"개");
        this.notifyAll();
    }
    public synchronized void eatBread(){
        if(breadCount<1){
            try{
                System.out.println("빵이 모자라 기다림");
                wait();
            }catch(InterruptedException ire){}
        }
        breadCount--;//빵이 있으니 먹자.
        System.out.println("빵을 1개 먹음  총 : "+breadCount+"개");
        this.notifyAll();
    }
}
```
```java
public class CakeMaker extends Thread {
    private CakePlate cake;
    public CakeMaker(CakePlate cake){
        setCakePlate(cake);
    }
    public void setCakePlate(CakePlate cake){
        this.cake=cake;
    }
    public CakePlate getCakePlate(){
        return cake;
    }
    public void run(){
        for(int i=0;i<30;i++){
            cake.makeBread();
        }
    }
}
```
```java
public class CakeEater extends Thread {
    private CakePlate cake;
    public CakeEater(CakePlate cake){
        setCakePlate(cake);
    }
    public void setCakePlate(CakePlate cake){
        this.cake=cake;
    }
    public CakePlate getCakePlate(){
        return cake;
    }
    public void run(){
        for(int i=0;i<30;i++){
            cake.eatBread();
        }
    }
}
```
```java
public class CakeEatings {
 
    public static void main(String[] args) {
        CakePlate cake=new CakePlate();//Cake 접시 준비
        CakeEater eater=new CakeEater(cake);//cake 접시 공유
        CakeMaker baker=new CakeMaker(cake);//cake 접시 공유
 
        //baker.setPriority(6);//우선순위--먼저 채워 넣고 시작하자.      
        baker.start();//먼저 채워 넣고 시작하자.
        eater.start();
    }
}
```

## 쓰레드와 자원 공유 - 동기화
 - 여러 개의 쓰레드에서 같은 자원을 동시에 사용할 수 없게 만들자.
 - 동기화(Synchronized) 이유와 사용법을 배운다.
 - synchronized 키워드를 사용한다.
```java
public class  StaticPrint implements Runnable{
    private static int i=0; //
    public void run(){show();}
    public void show(){
        for(  ;i<100;i++){
            if(((Thread.currentThread()).getName()).equals("a") ){
                System.out.print("[A"+i+"]");
            }else if(((Thread.currentThread()).getName()).equals("b") ){
                System.out.print("[B"+i+"]");
            }else if(((Thread.currentThread()).getName()).equals("c") ){
                System.out.print("[C"+i+"]");
            }
        }
    }
}
```
```java
public class   StaticPrintMain{
    public static void main(String[] args) {
        StaticPrint mr1=new StaticPrint();
        StaticPrint mr2=new StaticPrint();
        StaticPrint mr3=new StaticPrint();
        Thread t1=new Thread(mr1,"a");
        Thread t2=new Thread(mr2,"b");
        Thread t3=new Thread(mr3,"c");
        t1.start();  t2.start();  t3.start();
    }
}
```
```java
public class  StaticLockPrint implements Runnable{
    private static int i; //
    static { i=5;}
    public void run(){show();}
    public void show(){
        synchronized(StaticLockPrint.class){
            for(  ;i<100;i++){
                if(((Thread.currentThread()).getName()).equals("a") ){
                    System.out.print("[A"+i+"]");
                }else if(((Thread.currentThread()).getName()).equals("b") ){
                    System.out.print("[B"+i+"]");
                }else if(((Thread.currentThread()).getName()).equals("c") ){
                    System.out.print("[C"+i+"]");
                }
            }
        }
    }
}
/*
synchronized(StaticLockPrint.class){
    for(  ;i<100;i++){
        if(((Thread.currentThread()).getName()).equals("a") ){
            System.out.print("[A"+i+"]");
        }else if(((Thread.currentThread()).getName()).equals("b") ){
            System.out.print("[B"+i+"]");
        }else if(((Thread.currentThread()).getName()).equals("c") ){
            System.out.print("[C"+i+"]");
        }
    }
}
*/
```
```java
public class   StaticLockPrintMain{
    public static void main(String[] args) {
        StaticLockPrint mr1=new StaticLockPrint();
        StaticLockPrint mr2=new StaticLockPrint();
        StaticLockPrint mr3=new StaticLockPrint();
        Thread t1=new Thread(mr1,"a");
        Thread t2=new Thread(mr2,"b");
        Thread t3=new Thread(mr3,"c");
        t1.start();  t2.start();  t3.start();
    }
}
```

## 쓰레드와 자원 공유 - 멤버필드
 - 세 개의 쓰레드가 멤버 필드 하나를 공유하게 만들자
 - 멤버 필드를 공유하는 경우를 알아본다.
 - 같은 자원(같은 객체의 멤버 필드)을 여러 개의 쓰레드가 공유할 수 있다.
 <쓰레드가 같은 자원을 공유하는 경우>
 - 여러 개의 쓰레드에서 한 객체의 멤버 필드를 사용하려는 경우
 - 여러 개의 쓰레드에서 동일 타입으로 생성된 객체의 스태릭 필드를 사용하려는 경우
```java
public class  MemberPrint implements Runnable{
    private  int i=0; //
    public void run(){show();}
    public void show(){
        for(  ;i<100;i++){
            if(((Thread.currentThread()).getName()).equals("a") ){
                System.out.print("[A"+i+"]");
            }else if(((Thread.currentThread()).getName()).equals("b") ){
                System.out.print("[B"+i+"]");
            }else if(((Thread.currentThread()).getName()).equals("c") ){
                System.out.print("[C"+i+"]");
            }
        }
    }
}
```
```java
public class   MemberPrintMain{
    public static void main(String[] args) {
        MemberPrint mr1=new MemberPrint();
        Thread t1=new Thread(mr1,"a");
        Thread t2=new Thread(mr1,"b");
        Thread t3=new Thread(mr1,"c");
        t1.start();  t2.start();  t3.start();
    }
}
```

## 쓰레드의 기본 메서드와 특징
 - 실행되고 있는 쓰레드 이름을 출력하자.
 - 쓰레드가 제공하는 기본 메서드 사용법을 배운다.
 - getname()은 쓰레드 이름을, Thread.currentThead()는 현재 실행중인 쓰레드를 알려준다.
```java
public class  MyRuns implements Runnable
{
    public void run(){
        show();
    }
    public void show(){
        for(int i=0;i<100;i++){
            if(((Thread.currentThread()).getName()).equals("a") ){
                System.out.print("a");
                //System.out.print("[A"+i+"]");
            }else if(((Thread.currentThread()).getName()).equals("b") ){
                //System.out.print("[B"+i+"]");
                System.out.print("b");
            }else if(((Thread.currentThread()).getName()).equals("c") ){
                //System.out.print("[C"+i+"]");
                System.out.print("c");
            }else{
                System.out.print("["+Thread.currentThread().getName()+i+"]");
            }
        }
    }
}
```

```java
public class   MyRunsMain
{
    public static void main(String[] args)
    {
        MyRuns mr1=new MyRuns();
        Thread t1=new Thread(mr1,"a");
        Thread t2=new Thread(mr1,"b");
        Thread t3=new Thread(mr1,"c");
        //Thread t3=new Thread(mr1);
        t1.start();
        t2.start();
        t3.start();
    }
}
```

```java
public class   MyRunMain{
    public static void main(String[] args)
    {
        MyRun mr1=new MyRun();
        Thread t1=new Thread(mr1);
        MyThread t2=new MyThread();
        t1.start();
        t2.start();
        for(int i=0;i<100;i++){
            System.out.print("M");
        }
    }
}
```
