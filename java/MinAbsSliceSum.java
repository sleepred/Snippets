/*
 MinAbsSliceSum
Subtypes: sorting 
Time: recommended limit 55 min

N개의 정수로 구성된 non-empty zero-indexed array A가 있다.

0 ≤ P ≤ Q < N인 정수 P, Q의 쌍을 array A의 slice라고 하자.

slice (P, Q)의 합은 A[P] + A[P+1] + ... + A[Q]이다.

slice 합의 절대값이 최소인 slice를 "min abs slice"라고 하자.
 
예를 들어 아래와 같은 array A에 대해, 
  A[0] = 2
  A[1] = -4
  A[2] = 6
  A[3] = -3
  A[4] = 9

A의 slice의 예는 아래와 같다. (모든 예를 기술한 것은 아님)

(0, 1), 합의 절대값 = |2 + (-4)| = 2
(0, 2), 합의 절대값 = |2 + (-4) + 6| = 4
(0, 3), 합의 절대값 = |2 + (-4) + 6 + (-3)| = 1
(1, 3), 합의 절대값 = |(-4) + 6 + (-3)| = 1
(1, 4), 합의 절대값 = |(-4) + 6 + (-3) + 9| = 8
(4, 4), 합의 절대값 = |9| = 9

slices (0, 3)과 and (1, 3)은 각각 합의 절대값이 1로 min abs slice이다.

주어진 정수 array에 대해 min abs slice의 합의 절대값을 구하는 함수를 작성하시오.
**/
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
