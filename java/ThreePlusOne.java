
public class ThreePlusOne {

	public static void main(String[] args) {
		ThreePlusOne tpo = new ThreePlusOne();
		System.out.println(tpo.getMaxCount(1, 10));
		System.out.println(tpo.getMaxCount(100, 200));
		System.out.println(tpo.getMaxCount(201, 210));
		System.out.println(tpo.getMaxCount(900, 1000));
		
//		System.out.println(tpo.cal(22));
	}

	public int getMaxCount(int start, int end){
		int max = -1;
		
		for(int i=start; i<=end; i++){
			int cur = cal(i);
			
			if(max < cur){
				max = cur;
			}
		}
		
		return max;
	}
	
	public int cal(int val){
		int count = 1 ;
		while(val != 1){
			if(val % 2 == 0){
				val = val / 2;
			}
			else{
				val = val * 3 + 1;
			}
			count++;
		}
		
		return count;
	}
}
