# File

 
## 텍스트 파일 한 번에 모두 읽기 (Utility 타입)
 - 대용량 파일일 때는 메모리가 부족할 수 있으므로 '텍스트 파일 라인 단위로 읽어서 처리' 방식을 사용
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


## 텍스트 파일 라인 단위로 읽어서 처리

```java
FileReader     fileReader     = null;
BufferedReader bufferedReader = null;
try {
    fileReader     = new FileReader(filePath);
    bufferedReader = new BufferedReader(fileReader);
    String line;
    while ((line = bufferedReader.readLine()) != null) {
        [[line 값 처리]]
    }
     
} finally {
    if (bufferedReader != null) try { bufferedReader.close(); } catch (Exception ex) { /* Do Nothing */ }
    if (fileReader     != null) try { fileReader    .close(); } catch (Exception ex) { /* Do Nothing */ }
}
```


## 텍스트 파일 쓰기 (Utility 타입)

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

## 폴더 내 파일 리스트 처리

```java
File folder = new File([[폴더 경로]]);
for (File filex: folder.listFiles()) {
    String fileName     = filex.getName();
    String absolutePath = filex.getAbsolutePath();
    [[파일 처리]]
}
```

## 파일의 특정 바이트부터 특정 길이만큼 읽기
 - offset 위치를 계산할 때는 New Line 문자 ("\n" or "\r\n")의 길이도 계산에 포함할 지 검토해야 함
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

## BufferedReader, PrintWriter를 이용한 파일 읽고 쓰기
 - 원하는 파일을 읽고 저장하는 방법을 알아보자
 - IO 클래스의 적합한 사용법을 배운다.
 - FileReader와 FileWriter를 사용한다.
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
            pw.flush();//PrintWriter(fw, true)일 때는 필요없다.
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

## FileReader를 이용한 파일 읽기
 - 원하는 파일을 읽어들인다.
 - 원하는 파일을 읽어들인다.
 - FileReader를 사용한다.

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