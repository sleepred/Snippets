class MinAbsSliceSum {
    public int solution(int[] A) {
    	int minAbs = Integer.MAX_VALUE;
    	for(int i=0; i<A.length-1; i++){
    		for(int j=i+1; j<A.length; j++){
    			int curAbs = solution(A, i, j);
    			if(minAbs > curAbs){
    				minAbs = curAbs;
    			};
    		}
    	}
        return minAbs;
    }
    
    public int solution(int[] A, int start, int end){
    	int sum = 0;
    	System.out.print("[");
    	for(int i=start; i<=end; i++){
    		System.out.print(A[i] + ",");
    		sum += A[i];
    	}
    	System.out.print("]");
    	System.out.println(" = " + Math.abs(sum));
    	
    	return Math.abs(sum);
    }
    
    public static void main(String[] args) {
		System.out.println("expected 1, result " + new MinAbsSliceSum().solution(new int[]{2, -4, 6, -3, 9}));
		System.out.println("expected 1, result " + new MinAbsSliceSum().solution(new int[]{-4, 6, -3, 9, 2}));
		System.out.println("expected 2, result " + new MinAbsSliceSum().solution(new int[]{6, -3, 9, 2, -4}));
	}
}