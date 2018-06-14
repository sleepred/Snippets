# Thread

 
## ������ Thread ���

```java
Runnable runnable = new Runnable() {
    try {
        String threadName = Thread.currentThread().getName();
        [[ó�� ����]]
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
};
 
Thread thread = new Thread(runnable);
thread.start();
```

## ������ ���� Thread�� ����ϴ� ThreadPool

```java
ExecutorService threadPool = Executors.newFixedThreadPool(
        // CPU �ھ��� ����ŭ �ִ� �����带 ����ϴ� ������Ǯ�� ����
        Runtime.getRuntime().availableProcessors()
        );
 
Future future = threadPool.submit(new Runnable() {
    @Override
    public void run() {
        [[ó�� ���� �Ǵ� �ٸ� �޼��� ȣ��]]
    }
});
 
future.get();
 
// �۾� ť�� ����ϰ� �ִ� ��� �۾��� ó���� �ڿ� ������Ǯ�� ����
executorService.shutdown();
```


## �ִ� �ּ� Thread ��, ���� �ð� �̻� ��� Thread�� �ڵ����� �����ϴ� ThreadPool

```java
ExecutorService threadPool = new ThreadPoolExecutor(
    3,      // �ھ� ������ ����
    100,    // �ִ� ������ ����
    120L,   // ��� �ִ� �ð�
    TimeUnit.SECONDS,   // ��� �ִ� �ð� ����
    new SynchronousQueue<Runnable>()    // �۾� ť
);
 
Future future = threadPool.submit(new Runnable() {
    @Override
    public void run() {
        [[ó�� ���� �Ǵ� �ٸ� �޼��� ȣ��]]
    }
});
 
future.get();
 
// �۾� ť�� ����ϰ� �ִ� ��� �۾��� ó���� �ڿ� ������Ǯ�� ����
executorService.shutdown();
```


## ���� ����Ʈ
 - http://blog.eomdev.com/java/2016/04/06/Multi-Thread.html




## ������ ����ϱ�
 - ������(Thread) �� ���� ����� ������� ����
 - �� ���� ������ �ۼ����� ����.
 - �������̽� Runable�� �����ϰų� ������ Ŭ������ ����Ѵ�.
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

## ������� sleep �޼���
 - 50�и������帶�� ������ �̸��� ����ϰ� ����� ����.
 - ������� sleep �޼����� ������ ����.
 - Thread.sleep �޼��带 ����ϰ� ����ó���� ���ش�.
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
                Thread.sleep(50);//50/1000 ��
            }catch(InterruptedException ite){}
        }
    }
    public void print(){
            System.out.print(getName());//Thread����
    }
}
```
```java
public class  SleepThreadMain{
    public static void main(String[] args) {
        SleepThread t1=new SleepThread("a");
        SleepThread t2=new SleepThread("b");
        SleepThread t3=new SleepThread("c");
 
        t2.setPriority(7);//1~10 Ŭ���� �켭����
        t1.start();//t2�� t1���� �켱������
        try{
            t1.join();//t1�� ������ t2, t3�� �����Ѵ�.
        }catch(InterruptedException ite){}
        t2.start();
        t3.start();
    }
}
```

## ������� wait, notifyAll �޼��� �����ϱ�
- �ʿ��� �ڿ��� �غ�� ������ ������ �۾��� �������� ����.
- ������ �������� �ŷڼ��� ���̴� ����� ������ �� ����� �����Ѵ�.
- ����ȭ�� �޼��忡 ���� wait�� nofifyAll �޼��带 ����Ѵ�.

```java
public class CakePlate {
    private int breadCount=0;
    public CakePlate() {
    }
    public synchronized void makeBread(){
        if(breadCount>=10){
            try{
                System.out.println("���� ���´�.");
                wait();
            }catch(InterruptedException ire){}
        }
        breadCount++;//���� 10���� �ȵǸ� �� ������.
        System.out.println("���� 1�� �� ����  �� : "+breadCount+"��");
        this.notifyAll();
    }
    public synchronized void eatBread(){
        if(breadCount<1){
            try{
                System.out.println("���� ���ڶ� ��ٸ�");
                wait();
            }catch(InterruptedException ire){}
        }
        breadCount--;//���� ������ ����.
        System.out.println("���� 1�� ����  �� : "+breadCount+"��");
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
        CakePlate cake=new CakePlate();//Cake ���� �غ�
        CakeEater eater=new CakeEater(cake);//cake ���� ����
        CakeMaker baker=new CakeMaker(cake);//cake ���� ����
 
        //baker.setPriority(6);//�켱����--���� ä�� �ְ� ��������.      
        baker.start();//���� ä�� �ְ� ��������.
        eater.start();
    }
}
```

## ������� �ڿ� ���� - ����ȭ
 - ���� ���� �����忡�� ���� �ڿ��� ���ÿ� ����� �� ���� ������.
 - ����ȭ(Synchronized) ������ ������ ����.
 - synchronized Ű���带 ����Ѵ�.
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

## ������� �ڿ� ���� - ����ʵ�
 - �� ���� �����尡 ��� �ʵ� �ϳ��� �����ϰ� ������
 - ��� �ʵ带 �����ϴ� ��츦 �˾ƺ���.
 - ���� �ڿ�(���� ��ü�� ��� �ʵ�)�� ���� ���� �����尡 ������ �� �ִ�.
 <�����尡 ���� �ڿ��� �����ϴ� ���>
 - ���� ���� �����忡�� �� ��ü�� ��� �ʵ带 ����Ϸ��� ���
 - ���� ���� �����忡�� ���� Ÿ������ ������ ��ü�� ���¸� �ʵ带 ����Ϸ��� ���
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

## �������� �⺻ �޼���� Ư¡
 - ����ǰ� �ִ� ������ �̸��� �������.
 - �����尡 �����ϴ� �⺻ �޼��� ������ ����.
 - getname()�� ������ �̸���, Thread.currentThead()�� ���� �������� �����带 �˷��ش�.
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
