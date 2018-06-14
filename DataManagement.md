# Data Management

 
## Array ���� (int[], String[], ...)

```java
Arrays.sort([[Array]]);
```


## Array ���� (Class �Ǵ� Ư�� ����)

```java
Value[] values = [[values ����]];
Arrays.sort(values, new Comparator<Value>() {
    @Override
    public int compare(Value value0, Value value1) {
 
        // return value0.getString().compareTo(value1.getString());
 
 
        // if (value0.getInt() < value1.getInt()) {
        //  return 1;
        // } else if (value0.getInt() > value1.getInt()) {
        //  return -1;
        // } else {
        //  return 0;
        // }
    }
});
```


## List ���� (List<Integer>, List<String>, ...)

```java
Collections.sort([[List]]);
```

## List ���� (Class �Ǵ� Ư�� ����)

```java
List<Value> values = [[values ����]];
Collections.sort(values, new Comparator<Value>() {
    @Override
    public int compare(Value value0, Value value1) {
 
        // return value0.getString().compareTo(value1.getString());
 
 
        // if (value0.getInt() < value1.getInt()) {
        //  return 1;
        // } else if (value0.getInt() > value1.getInt()) {
        //  return -1;
        // } else {
        //  return 0;
        // }
    }
});
```

## Key�� Value List�� �̷���� Map �����

```java
private Map<String, List<[[Value]]>> valuesPerKey;
...
 
public void load() {
 
    valuesPerKey = new HashMap<>();
    ...
 
    while ([[key�� value�� ������ ��]]) {
        [[key�� value ���]]
        List<[[Value]]> values = valuesPerKey.get(key);
        if (values == null) {
            values = new ArrayList<>();
            valuesPerKey.put(key, values);
        }
        values.add(value);
    }
     
    ...
}
```

## Map�� Key ������ ������ List �����

```java
Map<String, [[Value]]> map = ...;
List<[[KeyValuePairDTO]]> pairs = new ArrayList<>();
String[] keys = map.keySet().toArray(new String[0]);
Arrays.sort(keys);
for (String keyx: keys)
    pairs.add(new [[KeyValuePairDTO]](keyx, ([[Value]])map.get(keyx)));
```

## KeyValuePair List�� Binary Search
 - Collections�� Binary Search�� ����Ϸ��� KeyValuePair List�� �ݵ�� ���� ���ĵǾ� �־�� ��

```java
// Collections�� Binary Search�� ����Ϸ��� KeyValuePair List�� �ݵ�� ���� ���ĵǾ� �־�� ��
List<[[KeyValuePairDTO]]> values = ...;
 
int index = Collections.binarySearch(values, new [[KeyValuePairDTO]](code), new Comparator<[[KeyValuePairDTO]]>() {
    @Override
    public int compare([[KeyValuePairDTO]] v1, [[KeyValuePairDTO]] v2) {
        return v1.getKey().compareTo(v2.getKey());
    }
});
 
if (index >= 0) {
    [[KeyValuePairDTO]] value = values.get(index);
    ...
} else {
    ...
}
```
