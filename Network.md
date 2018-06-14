# Network

 
## ������ TCP/IP Server
���� ó�� �ϱ� ������ ���� �ݺ��ϴ� �����

```java
ServerSocket server = new ServerSocket([[���� ��Ʈ]]);
 
while (true) {
    Socket socket = server.accept();
 
    //----[ ������ �޾Ƽ� ó���ϱ� ]----------------------------------------
 
    InputStream       inputStream       = socket.getInputStream();
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    BufferedReader    bufferedReader    = new BufferedReader(inputStreamReader);
 
    String data   = "";
    char[] chars  = new char[256];
    int    length;
    while ((length = bufferedReader.read(chars)) != -1) {
        String newData = new String(chars, 0, length);
 
 
        // ���� ���۹��� ������(newData) ó��
        data += newData;
    }
 
    [[���۹��� ��ü ������(data) ó��]]
 
    //----[ Client���� ������ ������ ]----------------------------------------
 
    OutputStream       outputStream       = socket.getOutputStream();
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
    PrintWriter        printWriter        = new PrintWriter(outputStreamWriter);
 
    printWriter.print([[���� ������]]);
    printWriter.flush();
 
    //----[ Client���� ���� ���� ]----------------------------------------
     
    printWriter.close();
    outputStreamWriter.close();
    outputStream.close();
 
    bufferedReader.close();
    inputStreamReader.close();
    inputStream.close();
 
    socket.close();
 
    // ���۹��� ��ü �����Ͱ� ���� ����̸� break
}
 
server.close();
```


## ������ TCP/IP Client

```java
Socket socket = new Socket("[[IP]]", [[Port]]);
 
//----[ Server���� ������ ������ ]----------------------------------------
 
OutputStream       outputStream       = socket.getOutputStream();
OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
PrintWriter        printWriter        = new PrintWriter(outputStreamWriter);
         
printWriter.print([[���� ����]]);
printWriter.flush();
 
//----[ Server�� ������ �� �޾Ƽ� ó���ϱ� ]----------------------------------------
 
InputStream       inputStream       = socket.getInputStream();
InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
BufferedReader    bufferedReader    = new BufferedReader(inputStreamReader);
 
String data   = "";
char[] chars  = new char[256];
int    length;
while ((length = bufferedReader.read(chars)) != -1) {
    String newData = new String(chars, 0, length);
 
    // ���� ���۹��� ������(newData) ó��
    data += newData;
}
     
[[���۹��� ��ü ������(data) ó��]]
 
//----[ Server���� ���� ���� ]----------------------------------------
 
bufferedReader.close();
inputStreamReader.close();
inputStream.close();
 
printWriter.close();
outputStreamWriter.close();
outputStream.close();
 
socket.close();
```


## Echo Server/Client

### Echo Server
```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
 
public class EchoServer {
 
    public static void main(String[] args) throws IOException {
        int portNumber = 30000;
 
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket socket = serverSocket.accept();
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 
        String echo;
        while ((echo = in.readLine()) != null) {
            System.out.println("echo: " + echo);
            out.println(echo);
            out.flush();
        }
         
        in.close();
        out.close();
        socket.close();
        serverSocket.close();
    }
}
```

### Echo Client
```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
 
public class EchoClient {
 
    public static void main(String[] args) throws UnknownHostException, IOException {
        String hostName = "127.0.0.1";
        int portNumber = 30000;
 
        Socket socket = new Socket(hostName, portNumber);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
 
        String userInput;
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            out.flush();
            System.out.println("echo: " + in.readLine());
        }
 
        in.close();
        out.close();
        socket.close();
    }
}
```

## Echo Server/Multi Client
### Echo Server
```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
public class EchoServer implements Runnable {
     
    private Socket socket;
     
    public EchoServer(Socket socket) {
        this.socket = socket;
    }
     
    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
     
            String echo;
            while ((echo = in.readLine()) != null) {
                System.out.println("echo: " + echo + " (from " + socket.getPort() + ")");
                out.println(echo);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
             
        }
    }
         
    public static void main(String[] args) throws IOException {
        int portNumber = 30000;
 
        ServerSocket serverSocket = new ServerSocket(portNumber);
        ExecutorService es = Executors.newFixedThreadPool(5);
        while (true) {
            es.submit(new EchoServer(serverSocket.accept()));
        }
        //serverSocket.close();
    }
}
```


### Echo Client

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
 
public class EchoClient {
 
    public static void main(String[] args) throws UnknownHostException, IOException {
        String hostName = "127.0.0.1";
        int portNumber = 30000;
 
        Socket socket = new Socket(hostName, portNumber);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
 
        String userInput;
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            out.flush();
            System.out.println("echo: " + in.readLine());
        }
 
        in.close();
        out.close();
        socket.close();
    }
}
```
