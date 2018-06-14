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



