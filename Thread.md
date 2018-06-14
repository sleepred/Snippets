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



