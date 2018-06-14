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

## File 갱신 체크
파일을 Open한 이후부터 갱신이 되었을 때 로직 처리
```java
try {
    File file = new File("REMOTE.TXT");
    while (file.length() <= 0) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
     
    RandomAccessFile rFile = new RandomAccessFile(file, "r");
    rFile.seek(file.length());
     
    String line = null;
    while (true) {
        line = rFile.readLine();
        if (line == null || line.isEmpty()) {
            try {
                Thread.sleep(500);
                continue;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
 
        //로직 처리        
        doSomething();
    }
} catch (IOException e) {
    e.printStackTrace();
}
```


## ObjectOutputStream을 이용하여 객체를 파일에 저장하기
 - Student 객체를 저장한다.
 - OutjectoutputStream과 ObjectInputStream 사용법을 익힌다.
 - 직렬화 객체만 readobject와 writeObject를 사용하여 읽고 저장할 수 있다.
```java
import java.io.*;
import java.util.Vector;
public class ObjectStudentWrite {
    public int write(String fname, Vector v) throws IOException{
    //public int write(String fname, Vector<Student> v) throws IOException{//JAVA5
        int objectNumber=0;
        try {
            FileOutputStream fos=new FileOutputStream(fname);
            ObjectOutputStream oos=new ObjectOutputStream(fos);//throws
            objectNumber=v.size();
            oos.writeInt(objectNumber);
            System.out.println(objectNumber+"개의 Student가 입력됨");
            for(int i=0;i<objectNumber;i++){
                oos.writeInt(i);
                oos.writeObject((Student)v.get(i));
                //oos.writeObject(v.get(i));//JAVA5
                oos.flush();
                System.out.println(i+"번째의  Student가 입력됨");
            }
            oos.close();   fos.close();
        } catch (FileNotFoundException e) {
            System.out.println("잘못된 파일이름을 입력했습니다.");
        } catch(Exception ee){
            throw new IOException("타입이 이상합니다."+ee);
        }
        return objectNumber;
    }
    public void read(String fname) throws IOException{
        try {
            FileInputStream fis = new FileInputStream(fname);
            ObjectInputStream ois=new ObjectInputStream(fis);//throws
            int objectNumber=ois.readInt();
            System.out.println(objectNumber+"개의 Student를 읽음");
            for(int i=0;i<objectNumber;i++){
                try {
                    System.out.print(ois.readInt()+"번째 :");
                    System.out.println((Student)ois.readObject());
                } catch (ClassNotFoundException e1) {
                    System.out.println("잘못된 타입입니다..");
                }
            }
            ois.close();  fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("잘못된 파일이름을 입력했습니다.");
        }
    }
}
```
```java
import java.io.IOException;
import java.util.Vector;
public class ObjectStudentWriteMain {
    public static void main(String[] args) {
        ObjectStudentWrite osw=new ObjectStudentWrite();
        Vector v=new Vector(5, 5);
        //Vector<Student> v=new Vector<Student>(5, 5);//JAVA5
        v.add(new Student("홍길동",17,"한양"));
        v.add(new Student("홍길순",15,"순천"));
        v.add(new Student("몽룡",20,"화천"));
        v.add(new Student("춘향",18,"삼척"));
        try {
            osw.write("stu.txt",v);
            osw.read("stu.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## SaxParser 사용하기

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<class>
    <student rollno="393">
        <firstname>dinkar</firstname>
        <lastname>kad</lastname>
        <nickname>dinkar</nickname>
        <marks>85</marks>
    </student>
 
 
    <student rollno="493">
        <firstname>Vaneet</firstname>
        <lastname>Gupta</lastname>
        <nickname>vinni</nickname>
        <marks>95</marks>
    </student>
 
    <student rollno="593">
        <firstname>jasvir</firstname>
        <lastname>singn</lastname>
        <nickname>jazz</nickname>
        <marks>90</marks>
    </student>
 
</class>
```
```java
package adun.snippet.ssp.sax;
 
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
 
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
 
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
public class SaxTest {
 
    public static void main(String[] args){
        InputStream in = null;
        try {
            in = SaxTest.class.getResourceAsStream("student.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            SaxHandler handler = new SaxHandler();
            saxParser.parse(in, handler);
            Map<Integer, Student> model = handler.getModel();
            System.out.println(model.entrySet());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
 
    }
 
    public static void parseZip(){
        InputStream zis = null;
 
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            SaxHandler handler = new SaxHandler();
            Map<Integer, Student> model = handler.getModel();
 
            URL url = SaxZipTest.class.getResource("student.zip");
            File file = new File(url.getFile());
            ZipFile zipfile = new ZipFile(file);
            ZipEntry entry = zipfile.getEntry("student.xml");
            if (entry != null) {
                zis = zipfile.getInputStream(entry);
                saxParser.parse(zis, handler);
            }
 
            zis = zipfile.getInputStream(zipfile.getEntry("student2.xml"));
            saxParser.parse(zis, handler);
 
            System.out.println(model.entrySet());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e) {
                }
            }
        }
 
    }
    public static class SaxHandler extends DefaultHandler {
 
        private Map<Integer, Student> model = new HashMap<Integer, Student>();
 
        /**
         * xml의 element의 path를 저장하는 변수
         */
        private StringBuffer path = new StringBuffer();
 
        /**
         * 현재 parsing을 하고 있는 xml element node의 name
         */
        private String nodeName;
 
        /**
         * 현재 parsing을 하고 있는 xml element node의 value
         */
        private StringBuffer nodeValue;
 
        /**
         * 현재 parsing을 하고 있는 위치를 저장할 location 객체
         */
        private Locator loc;
 
        /**
         * 현재 parsing을 하고 있는 Student 객체
         */
        private Student student;
 
        @Override
        public void setDocumentLocator(Locator locator){
            this.loc = locator;
        }
 
        public Locator getDocumentLocator(){
            return this.loc;
        }
 
        public void startElement(String namespaceURI, String localName, String qName, Attributes attrs) throws SAXException{
            path.append("/").append(qName);
            if ("/class".equals(path.toString())) {
            } else if ("/class/student".equals(path.toString())) {
                student = new Student();
                student.setRollno(Integer.parseInt(attrs.getValue("rollno")));
            }
            nodeName = qName;
        }
 
        /**
         * xml의 element가 닫힐때 자동으로 호출
         *
         * @param namespaceURI - namespace URI
         * @param localName - The local name (without prefix), xml Namespace processing을 사용하지 않으므로 사용되지 않는다.
         * @param qname - The qualified name (with prefix)
         * @throws SAXException
         */
        public void endElement(String namespaceURI, String localName, String qName) throws SAXException{
            //            System.out.println("endElement : " + path);
            if (nodeName.equals(qName)) { //leap node
                String value = nodeValue.toString().trim();
                if ("/class/student/firstname".equals(path.toString())) {
                    student.setFirstName(value);
                } else if ("/class/student/lastname".equals(path.toString())) {
                    student.setLastName(value);
                } else if ("/class/student/nickname".equals(path.toString())) {
                    student.setNickName(value);
                } else if ("/class/student/marks".equals(path.toString())) {
                    student.setMarks(Integer.parseInt(value));
                }
 
            } else {
                if ("/class/student".equals(path.toString())) {
                    model.put(student.getRollno(), student);
                    student = null;
                }
            }
            nodeValue = null;
            path.delete(path.lastIndexOf("/"), path.length());
        }
 
        /**
         * xml의 element node에 character data가 존재하는 경우 자동으로 호출되며 해당 data를 nodeValue 전역변수에 할당한다.
         *
         * @param ch - The characters
         * @param start - The start position in the character array
         * @param length - The number of characters to use from the character array
         * @throws SAXException
         */
        public void characters(char[] ch, int start, int length) throws SAXException{
            if (length == 0) return;
            String text = new String(ch, start, length);
            if (nodeValue == null) nodeValue = new StringBuffer(text);
            else nodeValue.append(text);
        }
 
        /**
         * xml을 통해서 만들어진 Map을 return한다.
         *
         * @return Map
         */
        public Map<Integer, Student> getModel(){
            return model;
        }
 
        @Override
        public void endDocument() throws SAXException{}
    }
 
    public static class Student {
 
        private String firstName;
 
        private String lastName;
 
        private String nickName;
 
        private int marks;
 
        private int rollno;
 
        public Student() {
 
        }
 
        public int getRollno(){
            return rollno;
        }
 
        public void setRollno(int rollno){
            this.rollno = rollno;
        }
 
        public String getFirstName(){
            return firstName;
        }
 
        public void setFirstName(String firstName){
            this.firstName = firstName;
        }
 
        public String getLastName(){
            return lastName;
        }
 
        public void setLastName(String lastName){
            this.lastName = lastName;
        }
 
        public String getNickName(){
            return nickName;
        }
 
        public void setNickName(String nickName){
            this.nickName = nickName;
        }
 
        public int getMarks(){
            return marks;
        }
 
        public void setMarks(int marks){
            this.marks = marks;
        }
 
        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append(getFirstName()).append(" ");
            sb.append(getLastName());
            sb.append("(").append(getNickName()).append(":");
            sb.append(getMarks()).append(")");
            return sb.toString();
        }
    }
}
```


## 데이터 주고받기(DataOutputStream)
 - 데이터를 주고 받는 방법을 알아본다.
 - DatainputStream과 DataOutputStream의 사용법을 배운다.
 - writeXXX메서드와 readXXX 메서드를 사용한다.
```java
import java.io.*;
public class WritingDatas {
    public void writingData(String fname, boolean append)throws IOException{
        FileOutputStream fos=null;
        DataOutputStream dos=null;
        try {
            fos=new FileOutputStream(new File(fname), append);
            dos=new DataOutputStream(fos);
            dos.writeBoolean(append); dos.writeByte(123);    dos.writeChar(75);
            dos.writeDouble(34.56); dos.writeFloat(345.23f); dos.writeInt(123);
            dos.writeLong(345L);    dos.writeUTF("홍길동");
            dos.flush(); //fos.close();
        } catch (FileNotFoundException e) {
            System.out.println("잘못된 파일이름을 입력했습니다.");
        }
    }
    public void readingData(String fname)throws IOException{
        try {
            FileInputStream fis=new FileInputStream(new File(fname));
            DataInputStream dis=new DataInputStream(fis);
            System.out.println("append가능? :"+dis.readBoolean());
            System.out.println("read byte :"+dis.readByte());
            System.out.println("read char :"+dis.readChar());
            System.out.println("read double :"+dis.readDouble());
            System.out.println("read float :"+dis.readFloat());
            System.out.println("read int :"+dis.readInt());
            System.out.println("read long :"+dis.readLong());
            System.out.println("read utf :"+dis.readUTF());
            dis.close();
        } catch (FileNotFoundException e) {
            System.out.println("잘못된 파일이름을 입력했습니다.");
        }
    }
}
```
```java

import java.io.*;
public class WritingDatasMain {
    public static void main(String[] args) {
        WritingDatas wd=new WritingDatas();
        try {
            wd.writingData("writed.txt",false);
            wd.readingData("writed.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```
```java
import java.io.*;
public class WritingDatasUsing {
    public void writingData(String fname, boolean append, WritingData wd)
      throws IOException{
        FileOutputStream fos=null;
        DataOutputStream dos=null;
        try {
            fos=new FileOutputStream(new File(fname), append);
            dos=new DataOutputStream(fos);
            dos.writeUTF(wd.getName());
            dos.writeInt(wd.getAge());
            dos.writeDouble(wd.getHeight());
            dos.writeBoolean(wd.isMan());
        } catch (FileNotFoundException e) {
            System.out.println("잘못된 파일이름을 입력했습니다.");
        }
    }
    public WritingData readingData(String fname)throws IOException{
        FileInputStream fis=null;
        DataInputStream dis=null;
        WritingData wd=new WritingData();
        try {
            fis=new FileInputStream(new File(fname));
            dis=new DataInputStream(fis);
            wd.setName(dis.readUTF());
            wd.setAge(dis.readInt());
            wd.setHeight(dis.readDouble());
            wd.setMan(dis.readBoolean());
        } catch (FileNotFoundException e) {
            System.out.println("잘못된 파일이름을 입력했습니다.");
        }
        return wd;
    }
}
```
```java
import java.io.IOException;
 
public class WritingDatasUsingMain {
 
    public static void main(String[] args) {
        WritingDatasUsing wdu=new WritingDatasUsing();
        WritingData wd=new WritingData();
        wd.setName("홍길순");
        wd.setAge(20);
        wd.setHeight(165);
        wd.setMan(false);
         
        WritingData wd2=null;
        try {
            wdu.writingData("abc.txt",true, wd);
            wd2=wdu.readingData("abc.txt");
            System.out.println(wd2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}}
```


## 디렉토리 및 파일 검색하면서 작업 하기 (JDK 1.7)
 - JDK 1.7 부터 사용 가능
 
```java
package adun.snippet.ssp.file;
 
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
 
public class FileWorkerTest {
 
  public static void main(String[] args) throws IOException{
 
    File root = new File("d:/_");
    TestWalker walker = new TestWalker();
    Files.walkFileTree(root.toPath(), walker);
  }
 
  public static class TestWalker extends SimpleFileVisitor<Path> {
 
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException{
      return super.postVisitDirectory(dir, exc);
    }
 
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException{
      System.out.println("D:" + dir);
      return super.preVisitDirectory(dir, attrs);
    }
 
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
      System.out.println("F:" + file);
      return super.visitFile(file, attrs);
    }
 
  }
}
```


## 디렉토리에서 파일 찾기
 - 디렉토리에서 원하는 파일이 있는지 확인
 - File 클래스에서 메서드 활용
### FileSearch.java
```java
import java.io.*;
import java.util.*;
public class FileSearch {
    public void printDirectory(String fname)throws IOException{
        File f=new File(fname);
        if(f.exists()){
            System.out.println("절대경로: "+f.getAbsolutePath());//
            System.out.println("(대문자)절대경로: "+f.getCanonicalPath());//throws
            System.out.println("수정날짜: "+new Date(f.lastModified()));//수정날짜
            System.out.println("Read가능? "+f.canRead());//
            System.out.println("Write가능? "+f.canWrite());//
            if(f.isDirectory()){
                System.out.println("디렉토리인가? "+f.isDirectory());//
                File []fs= f.listFiles();
                for(int i=0; i<fs.length;i++){
                    if(fs[i].isDirectory()){
                        System.out.println(fs[i].getAbsoluteFile());//절대경로
                    }
                }
            }else{
                System.out.println("파일이름 : "+f.getName());//파일이름
                System.out.println("파일 길이 : "+f.length()+" byte");//파일길이
                System.out.println("숨긴 파일인가? "+f.isHidden());
                System.out.println("파일구분문자 "+File.pathSeparatorChar);
                System.out.println("경로구분문자 "+File.separator);//경로구분문자
            }
        }else{
            System.out.println(fname+"이 없다.");
            System.exit(1);
        }
    }
}
```
### FileSearchMain.java
```java
public class FileSearchMain {
 
    public static void main(String[] args) {
        FileSearch fs=new FileSearch();
        try{
            fs.printDirectory("c:\\");//"C:\"
            //fs.printDirectory("FileSearch.java");
            //fs.printDirectory("C:\\WINDOWS");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
```
