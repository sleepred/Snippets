# Data Management

 
## Array 정렬 (int[], String[], ...)

```java
Arrays.sort([[Array]]);
```


## Array 정렬 (Class 또는 특수 조건)

```java
Value[] values = [[values 생성]];
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


## List 정렬 (List<Integer>, List<String>, ...)

```java
Collections.sort([[List]]);
```

## List 정렬 (Class 또는 특수 조건)

```java
List<Value> values = [[values 생성]];
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

## Key와 Value List로 이루어진 Map 만들기

```java
private Map<String, List<[[Value]]>> valuesPerKey;
...
 
public void load() {
 
    valuesPerKey = new HashMap<>();
    ...
 
    while ([[key와 value가 존재할 때]]) {
        [[key와 value 얻기]]
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

## Map을 Key 순서로 정렬한 List 만들기

```java
Map<String, [[Value]]> map = ...;
List<[[KeyValuePairDTO]]> pairs = new ArrayList<>();
String[] keys = map.keySet().toArray(new String[0]);
Arrays.sort(keys);
for (String keyx: keys)
    pairs.add(new [[KeyValuePairDTO]](keyx, ([[Value]])map.get(keyx)));
```

## KeyValuePair List의 Binary Search
 - Collections의 Binary Search를 사용하려면 KeyValuePair List는 반드시 먼저 정렬되어 있어야 함

```java
// Collections의 Binary Search를 사용하려면 KeyValuePair List는 반드시 먼저 정렬되어 있어야 함
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
