public class Frog {
    public static int numberOfWays(int n) {
//    	return numberOfWays1(n);
    	return numberOfWays2(n);
    }
    
    /**
     * by fibonacci
     */
    public static int numberOfWays1(int n) {
    	int pre = 1;
    	int cur = 1;
    	
    	if(n == 0 || n== 1){
    		return 1;
    	}
    	
    	for(int i=2; i<=n; i++){
    		int tmp = pre+cur;
    		pre = cur;
    		cur = tmp;
    	}
    	
    	return cur;
    }
    
    /**
     * by recursive
     */
    public static int numberOfWays2(int n) {
    	return numberOfWays2(n, 0);
    }
    
    private static int numberOfWays2(int n, int sum){
    	int ways = 0;
    	if(n == sum){
    		return 1;
    	}
    	if(n < sum){
    		return 0;
    	}
    	ways += numberOfWays2(n, sum+1);
    	ways += numberOfWays2(n, sum+2);
    	
    	return ways;
    }

    public static void main(String[] args) {
        System.out.println(numberOfWays(4));
    }
}