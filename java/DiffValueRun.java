import java.util.HashMap;
import java.util.Map;

public class DiffValueRun {
	public static void main(String args[]){
		
		int[] val1 = { 100, 50, 1 };
		int[] val2 = { 1, 50, 100 };		
		int[] val3 = { 900, 789, 678, 567, 456, 345, 234, 123 };
		int[] val4 = { 900, 789, 678, 567, 456, 345, 234, 123, 900 };		
		int[] val5 = { 1, 3, 7, 15, 100, 212, 313, 414, 515 };	
		int[] val6 = { 17, 1, 7, 3, 15 };
				
		System.out.println(check(val1));
		System.out.println(check(val2));
		System.out.println(check(val3));		
		System.out.println(check(val4));	
		System.out.println(check(val5));	
		System.out.println(check(val6));			
	}
	
	public static int check(int vals[]){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for(int i=0; i<vals.length-1; i++){
			for(int j=i+1; j<vals.length; j++){
//				System.out.println(vals[i] + " - " + vals[j] + " = " + minus(vals[i], vals[j]));
				map.put(minus(vals[i], vals[j]), minus(vals[i], vals[j]));
			}
		}
		
		return map.size();
	}
	
	public static int minus(int val1, int val2){
		if(val1 > val2){
			return val1 - val2;
		}
		
		return val2 - val1;
	}
}
