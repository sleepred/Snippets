import java.util.HashMap;
import java.util.Map;


public class SelfNumberAnswer {

	public static void main(String[] args) {
		SelfNumberAnswer sn = new SelfNumberAnswer();
		
		System.out.println("Expected : 25 " + " / Your Answer :" + sn.sum(1, 10));
		System.out.println("Expected : 76 " + " / Your Answer :" + sn.sum(1, 35));
		System.out.println("Expected : 1227365 " + " / Your Answer :" + sn.sum(1, 5000));
	}
	
	
	public int sum(int start, int end){
		Map<Integer, Integer> generatorMap = makeGeneratorMap(end);
		
		int sum = 0;
		for(int i=start; i<=end; i++){
			if(!generatorMap.containsKey(i)){
				sum += i;
			}
		}
		return sum;
	}
	
	public Map<Integer, Integer> makeGeneratorMap(int end){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for(int i=0; i<end; i++){
			map.put(getDestValue(i), i);
		}
		
		/*
		 * 
		Iterator<Integer> it = map.keySet().iterator();
		while(it.hasNext()){
			int key = it.next();
			int value = map.get(key);
			System.out.println("key :" + key + ", value :" + value);
		}
		 */
		
		return map;
	}
	
	public int getDestValue(int num){
		String val = String.valueOf(num);
		int sum = 0;
		for(int i=0; i<val.length(); i++){
			sum += Integer.parseInt(String.valueOf(val.charAt(i)));
		}
		return sum + num;
	}
}
