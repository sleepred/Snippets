/**
희용이는 구글에 입사하기 위해 면접시험을 보러 갔다.

희용이는 구글 입사 시험 기출문제로 "1 ~ 1,000,000까지 1의 개수"를 묻는 문제가 나왔다는 사실을 vega선생님으로 부터 들어서 이 문제에 대한 대답을 준비하고 갔다.

면접실에 들어가자 면접관은 다음과 같이 질문을 하였다.

"1 ~ 1,000,000까지의 1의 개수 대신, a부터 b까지의 정수 중 k라는 숫자가 몇 번 나왔는지 알아내는 프로그램을 작성해보세요."

희용이는 순간 당황했지만 이 사이트에서 유사한 문제를 본 적이 있기 때문에 응용해서 무사히 풀수 있었다.

이 프로그램을 작성하시오.
-------------------------------------
http://codeup.kr/JudgeOnline/problem.php?id=3711
*/

package z0728;

public class CountNumber {

	public int count(int start, int end, int number) {

		if (number < 0 || number > 10) {
			return 0;
		}
		
		int count = 0;
		char num = String.valueOf(number).charAt(0);
		for (int i = start; i <= end; i++) {
			char[] chars = String.valueOf(i).toCharArray();
			
			for (int j = 0; j < chars.length; j++) {
				if (num == chars[j]) {
					count++;
				}
			}
		}
		
		return count;
	}
	
	public static void main(String[] args) {
		System.out.println("expected 11, result " + new CountNumber().count(20,  30, 2));
		System.out.println("expected 21, result " + new CountNumber().count(0,  100, 1));
	}
}
