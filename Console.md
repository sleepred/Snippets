# Console

 
# ����ڿ��Լ� 1���� �� �Է� �ޱ�

```java
Scanner scanner = new Scanner(System.in);
 
// �� �Է� ���� Console�� ǥ���� ����. println()�� �ƴϰ� print()��.   
System.out.print("VALUE : ");
 
// �� �ޱ�
String value = scanner.nextLine();
     
scanner.close();
 
[[�� ó��]]
```

# ����ڿ��Լ� ��� �� �Է� �ޱ�

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