/**
주어진 금액 N과 S = {S1, S2, ..., Sm}인 동전 리스트에 대해 
해당 동전 리스트로 주어진 금액 N을 만들 수 있는 조합의 개수를 구하시오. (동전의 순서는 고려하지 않음)

예1)
N = 4, S = {1, 2, 3}
정답 : 4개 {1,1,1,1},{1,1,2},{2,2},{1,3}

예2)
N = 10, S = {2, 5, 3, 6}
정답 : 5개 {2,2,2,2,2},{2,2,3,3},{2,2,6},{2,3,5},{5,5}

public class CoinChange {
	public static int countCoinChangeCase(int amount, int[] v) {
		//TODO
	}

	public static void main(String[] args) {
		int amount = 5;
		int[] v = { 1, 2, 3 };
		System.out.println(countCoinChangeCase(amount, v));
	}
}
*/
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
