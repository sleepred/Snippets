# Console

 
## 사용자에게서 1개의 값 입력 받기

```java
Scanner scanner = new Scanner(System.in);
 
// 값 입력 전에 Console에 표시할 내용. println()이 아니고 print()임.   
System.out.print("VALUE : ");
 
// 값 받기
String value = scanner.nextLine();
     
scanner.close();
 
[[값 처리]]
```

## 사용자에게서 계속 값 입력 받기

```java
Scanner scanner = new Scanner(System.in);
     
while (true) {
    // 값 입력 전에 Console에 표시할 내용. println()이 아니고 print()임.
    System.out.print("VALUE : ");
     
    // 값 받기
    String value = scanner.nextLine();
     
    // 종료 문자면 break
    if ("q".equals(value)) break;
     
    [[값 처리]]
}
     
scanner.close();
```


## BufferedReader를 이용한 문자 입력받기
 - 키보드에서 한 줄 단위로 입력을 받아보자
 - 문자열 입력 방법을 배운다.

```java
import java.io.*;
public class ReadFromKeyBoard {
 
    public static void main(String[] args) {
        try{
            InputStreamReader isr=new InputStreamReader(System.in);
            BufferedReader br=new BufferedReader(isr);
            String s="";
            System.out.println("끝내고 싶다면 ctrl+c를 입력하시오");//ctrl+c
            //while(!(s=br.readLine()).equals("@esc")){//@esc
            while((s=br.readLine())!=null){//ctrl+c
                 System.out.println(s);//출력
            }
            br.close();
            isr.close();
        }catch(Exception ee){
            System.out.println(ee.toString());
        }
    }
}
```


## System.in을 이용한 문자 입력받기
 - 문자 하나를 키보드를 이용하여 입력
 - 문자를 여러번 받아 문자열을 만드는 방법

### InputCharacters.java
```java 
import java.io.*;
public class InputCharacters {
 
    public static void main(String[] args) {
        try{
            InputStreamReader in=new InputStreamReader(System.in);
            StringBuffer sb=new StringBuffer();
            StringBuffer sb2=new StringBuffer();
            int ch;
            while ((ch = in.read()) != -1) {
              if (ch != '\n') {//한줄단위로 받기 위해서
                  sb.append((char)ch);
              } else {//여러줄을 붙이기 위해서
                sb2.append(sb.toString()+"\n");
                sb = new StringBuffer();//
              }
            }
            System.out.println(sb2.toString());
            in.close();
        }catch(Exception ee){
            System.out.println(ee.toString());
        }
    }
}
```

### ReadFromSystem1.java
```java 
import java.io.*;
public class ReadFromSystem1 {
 
    public static void main(String[] args) {
        byte[] b=new byte[1024];
        int len=0;
        try{
            len=System.in.read(b);  //섹션 102
        }
        catch(Exception e){
            System.out.println("입력실패");
        }
        String str=new String(b,0,len-2);//'\n'과 '\r'이 붙어 있어서  2를 뺀다.
        System.out.println(str);
        try{
            len=System.in.read(b);
            System.out.write(b, 0, len);
        }
        catch(Exception e){
            System.out.println("입력실패");
        }
    }
}
```
