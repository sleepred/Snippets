import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CoinChange {
	private static Map<String, List<Integer>> map = null;
	
	public static int countCoinChangeCase(int amount, int[] v) {
		map = new HashMap<String, List<Integer>>();
		pick(amount, v, new ArrayList<Integer>());
		
//		for(String key: map.keySet()){
//			printList(map.get(key));
//		}
		return map.size();
	}
	
	public static String getListKey(List<Integer> list){
		Collections.sort(list, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		String key = "";
		for(int val : list){
			key += val;
		}
		
		return key;
	}
	
	public static void printList(List<Integer> list){
		for(int val : list){
			System.out.print(val + ",");
		}
		System.out.println();
	}
	
	public static void pick(int amount, int[] v, List<Integer> chain){
		if(amount == getSum(chain)){
			map.put(getListKey(chain), chain);
			return;
		}
		else if(amount < getSum(chain)){
			return;
		}
		
		for(int i=0; i<v.length; i++){
			List<Integer> newChain = new ArrayList<Integer>();
			newChain.addAll(chain);
			newChain.add(v[i]);
			
			pick(amount, v, newChain);
		}
	}
	
	public static int getSum(List<Integer> chain){
		int sum = 0;
		for(int val : chain){
			sum += val;
		}
		
		return sum;
	}
	
	public static void main(String[] args) {
		System.out.println(countCoinChangeCase(4, new int[]{ 1, 2, 3 }));
		System.out.println(countCoinChangeCase(10, new int[]{ 2, 5, 3, 6 }));
	}
}
