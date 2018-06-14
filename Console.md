# Console

 
## ����ڿ��Լ� 1���� �� �Է� �ޱ�

```java
Scanner scanner = new Scanner(System.in);
 
// �� �Է� ���� Console�� ǥ���� ����. println()�� �ƴϰ� print()��.   
System.out.print("VALUE : ");
 
// �� �ޱ�
String value = scanner.nextLine();
     
scanner.close();
 
[[�� ó��]]
```

## ����ڿ��Լ� ��� �� �Է� �ޱ�

```java
Scanner scanner = new Scanner(System.in);
     
while (true) {
    // �� �Է� ���� Console�� ǥ���� ����. println()�� �ƴϰ� print()��.
    System.out.print("VALUE : ");
     
    // �� �ޱ�
    String value = scanner.nextLine();
     
    // ���� ���ڸ� break
    if ("q".equals(value)) break;
     
    [[�� ó��]]
}
     
scanner.close();
```


## BufferedReader�� �̿��� ���� �Է¹ޱ�
 - Ű���忡�� �� �� ������ �Է��� �޾ƺ���
 - ���ڿ� �Է� ����� ����.

```java
import java.io.*;
public class ReadFromKeyBoard {
 
    public static void main(String[] args) {
        try{
            InputStreamReader isr=new InputStreamReader(System.in);
            BufferedReader br=new BufferedReader(isr);
            String s="";
            System.out.println("������ �ʹٸ� ctrl+c�� �Է��Ͻÿ�");//ctrl+c
            //while(!(s=br.readLine()).equals("@esc")){//@esc
            while((s=br.readLine())!=null){//ctrl+c
                 System.out.println(s);//���
            }
            br.close();
            isr.close();
        }catch(Exception ee){
            System.out.println(ee.toString());
        }
    }
}
```


## System.in�� �̿��� ���� �Է¹ޱ�
 - ���� �ϳ��� Ű���带 �̿��Ͽ� �Է�
 - ���ڸ� ������ �޾� ���ڿ��� ����� ���

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
              if (ch != '\n') {//���ٴ����� �ޱ� ���ؼ�
                  sb.append((char)ch);
              } else {//�������� ���̱� ���ؼ�
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
            len=System.in.read(b);  //���� 102
        }
        catch(Exception e){
            System.out.println("�Է½���");
        }
        String str=new String(b,0,len-2);//'\n'�� '\r'�� �پ� �־  2�� ����.
        System.out.println(str);
        try{
            len=System.in.read(b);
            System.out.write(b, 0, len);
        }
        catch(Exception e){
            System.out.println("�Է½���");
        }
    }
}
```
