/**
 3n+1 문제
 
 아래와 같은 알고리즘을 고려하여 문제를 푸시오.
 
 1. 임의의 숫자 N으로 시작한다.( 0 < N < 1,000,000)
 2. N 이 짝수이면 2로 나눈다.
 3. N 이 홀수이면 3을 곱한 후 1을 더한다.
 4. 위의 과정을 N = 1 이 될 때까지 반복한다.
 
 만약 N = 22 이면
  22 11 34 17 52 26 13 40 20 10 5 16 8 4 2 1
 과 같은 결과가 나온다. 수행 횟수는 16이다.
 
 
INPUT
0보다 크고 1,000,000 보다 적은 두 수를 입력 받는다.
 
OUTPUT
입력받은 두 수의 구간에서 위의 알고리즘을 적용하여 가장 많은 수행횟수를 출력한다.
 
 
Sample Input

1 10
100 200
201 210
900 1000

Sample Output

1 10 20
100 200 125
201 210 89
900 1000 174

- 1과 10 사이의 수[1,2,3,4,5,6,7,8,9,10] 를 3n+1 수행하면 20이 가장 많은 수행 횟수이다.
*/
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
