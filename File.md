# File

 
## �ؽ�Ʈ ���� �� ���� ��� �б� (Utility Ÿ��)
 - ��뷮 ������ ���� �޸𸮰� ������ �� �����Ƿ� '�ؽ�Ʈ ���� ���� ������ �о ó��' ����� ���
```java
public static String read(String filePath) throws IOException {
 
    StringBuilder  stringBuilder;
    FileReader     fileReader     = null;
    BufferedReader bufferedReader = null;
    try {
        stringBuilder  = new StringBuilder();
        fileReader     = new FileReader(filePath);
        bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null)
            stringBuilder.append(line).append('\n');
         
    } finally {
        if (bufferedReader != null) try { bufferedReader.close(); } catch (Exception ex) { /* Do Nothing */ }
        if (fileReader     != null) try { fileReader    .close(); } catch (Exception ex) { /* Do Nothing */ }
    }
     
    return stringBuilder.toString();
}
```


## �ؽ�Ʈ ���� ���� ������ �о ó��

```java
FileReader     fileReader     = null;
BufferedReader bufferedReader = null;
try {
    fileReader     = new FileReader(filePath);
    bufferedReader = new BufferedReader(fileReader);
    String line;
    while ((line = bufferedReader.readLine()) != null) {
        [[line �� ó��]]
    }
     
} finally {
    if (bufferedReader != null) try { bufferedReader.close(); } catch (Exception ex) { /* Do Nothing */ }
    if (fileReader     != null) try { fileReader    .close(); } catch (Exception ex) { /* Do Nothing */ }
}
```


## �ؽ�Ʈ ���� ���� (Utility Ÿ��)

```java
public static void write(String filePath, String content) throws IOException {
 
    FileWriter     fileWriter     = null;
    BufferedWriter bufferedWriter = null;
    try {
        fileWriter     = new FileWriter(filePath);
        bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(content);
 
    } finally {
        if (bufferedWriter != null) try { bufferedWriter.close(); } catch (Exception ex) { /* Do Nothing */ }
        if (fileWriter     != null) try { fileWriter    .close(); } catch (Exception ex) { /* Do Nothing */ }
    }
}
```

## ���� �� ���� ����Ʈ ó��

```java
File folder = new File([[���� ���]]);
for (File filex: folder.listFiles()) {
    String fileName     = filex.getName();
    String absolutePath = filex.getAbsolutePath();
    [[���� ó��]]
}
```

## ������ Ư�� ����Ʈ���� Ư�� ���̸�ŭ �б�
 - offset ��ġ�� ����� ���� New Line ���� ("\n" or "\r\n")�� ���̵� ��꿡 ������ �� �����ؾ� ��
```java
public static byte[] readBytes(String filePath, int offset, int length) throws IOException {
 
    RandomAccessFile randomFile = null;
    try {
        randomFile = new RandomAccessFile(filePath, "r"); 
        randomFile.seek(offset);
         
        byte[] dataBytes = new byte[length];
        randomFile.readFully(dataBytes);
         
        return dataBytes;
         
    } finally {
        if (randomFile != null) try { randomFile.close(); } catch (Exception ex) { /* Do Nothing */ }
    }
}
```

## BufferedReader, PrintWriter�� �̿��� ���� �а� ����
 - ���ϴ� ������ �а� �����ϴ� ����� �˾ƺ���
 - IO Ŭ������ ������ ������ ����.
 - FileReader�� FileWriter�� ����Ѵ�.
```java
import java.io.*;
public class ReadAndWriteFromFile{
   public void readFile(String fn) throws IOException{
        FileReader fr=new FileReader(fn);
        BufferedReader br=new BufferedReader(fr);
        StringBuffer sb=new StringBuffer();
        String temp="";
        while((temp=br.readLine())!=null){
            sb.append(temp+"\n");
        }
        System.out.println(sb.toString());
        br.close();
        fr.close();
   }
   public void readnwrite(String fn, boolean append) throws IOException{
        String s=null;
        FileWriter fw=new FileWriter(fn,append);//append true
        PrintWriter pw=new PrintWriter(fw);//flush false
        //PrintWriter pw=new PrintWriter(fw, true);//flush true
        while((s=readbuff())!=null){   //CTRL+C
            pw.println(s);
            pw.flush();//PrintWriter(fw, true)�� ���� �ʿ����.
        }
        pw.close();
        fw.close();
   }
   public String readbuff() throws IOException{
        InputStreamReader isr=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(isr);
        return br.readLine();
    }
}
```
```java
import java.io.*;
public class ReadAndWriteFromFileMain
{
    public static void main(String[] args)
    {
        String fname="aaa.txt";
        ReadAndWriteFromFile baw=new ReadAndWriteFromFile();
        try{
            baw.readnwrite(fname,false);//clear
            //baw.readnwrite("aaa.txt",true);//append
            baw.readFile(fname);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
}
```

## FileReader�� �̿��� ���� �б�
 - ���ϴ� ������ �о���δ�.
 - ���ϴ� ������ �о���δ�.
 - FileReader�� ����Ѵ�.

```java
import java.io.*;
public class ReadFromFile {
 
    public static void main(String[] args) {
        String fname="ReadFromKeyBoard.java";
        //String fname="src\\ReadFromKeyBoard.java";//eclipse
        try{
            FileReader fr=new FileReader(fname);
            BufferedReader br=new BufferedReader(fr);
            StringBuffer sb=new StringBuffer();
            String s="";
            while((s=br.readLine())!=null){
                sb.append(s+"\n");
            }
            s=sb.toString();
            System.out.println(s);
            br.close();
            fr.close();
        }catch(Exception ee){
            System.out.println(ee.toString());
        }
    }
}
```
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