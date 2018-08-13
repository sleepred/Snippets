/*
<2번 문항>

https://www.testdome.com/Questions/Java/Run/4955 (20min)

주어진 string에 대해 최대 run의 zero-base index를 return 하는 function을 작성하시오.

여기서 run은 동일한 character가 연이어 나타나는 것을 의미한다.
만약 1개 이상의 run이 동일한 길이를 가진다면, 첫번째 run의 index를 return 하시오.

예를 들어 "abbcccddddcccbba"의 최대 run은 dddd이며 첫 d이 index는 6이므로 
indexOfLongestRun("abbcccddddcccbba")의 return 값은 6이어야 한다.
*/

public class Run {
    public static int indexOfLongestRun(String str) {
        if(str == null || str.length() == 0){
        	return -1;
        }
        char pre = str.charAt(0);
        
        RunData curRunData = new RunData();
        curRunData.runChar = pre;
        curRunData.idx = 0;
        curRunData.count = 1;
        
        RunData maxRunData = curRunData;
        for(int i=1; i<str.length(); i++){
        	char cur = str.charAt(i);
        	
        	if(pre == cur){
        		curRunData.count++;
        	}
        	else{
        		curRunData = new RunData();
        		curRunData.runChar = cur;
        		curRunData.idx = i;
        		curRunData.count = 1;
        	}
        	if(maxRunData.count < curRunData.count){
        		maxRunData = curRunData;
        	}
        	pre = cur;
        }
        
        return maxRunData.idx;
    }

    public static void main(String[] args) {
        System.out.println(indexOfLongestRun("abbbbbbbbbbbbbbbbbbb"));
    }
}
class RunData{
	public int count;
	public int idx;
	public char runChar;
}
