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