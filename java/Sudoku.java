import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Sudoku {
	private static int CHK_SUM = 45;
	
	public static void main(String[] args) throws IOException {
		Sudoku sudoku = new Sudoku();
		String[] result = sudoku.solve(parseInput());
		print(result);
	}

	private String[] solve(String[] parseInput) {
		int intArray[][] = parseIntArray(parseInput);
		
		while(!isCompleted(intArray)){
			solveRow(intArray);
			solveCol(intArray);
			solveSquare(intArray);
		}
		
		/*for(int rowIdx = 0; rowIdx<intArray.length; rowIdx++){
			for(int colIdx = 0; colIdx < intArray[rowIdx].length; colIdx++){
				System.out.print(intArray[rowIdx][colIdx] + " ");
			}
			System.out.println();
		}*/
		
		for(int rowIdx = 0; rowIdx<intArray.length; rowIdx++){
			String line = "";
			for(int colIdx = 0; colIdx < intArray[rowIdx].length; colIdx++){
				line = line + intArray[rowIdx][colIdx] + " ";
			}
			parseInput[rowIdx] = line;
		}
		
		return parseInput;
	}
	
	private boolean isCompleted(int intArray[][]){
		for(int rowIdx = 0; rowIdx<intArray.length; rowIdx++){
			for(int colIdx = 0; colIdx < intArray[rowIdx].length; colIdx++){
				if(intArray[rowIdx][colIdx] == 0){
					return false;
				}
			}
		}
		
		return true;
	}
	
	private void solveRow(int intArray[][]){
		for(int rowIdx = 0; rowIdx<intArray.length; rowIdx++){
			int sum = 0;
			int zeroCount = 0;
			int zeroIdx = 0;
			for(int colIdx = 0; colIdx < intArray[rowIdx].length; colIdx++){
				int cur = intArray[rowIdx][colIdx];
				if(cur == 0){
					zeroCount++;
					zeroIdx = colIdx;
				}
				sum+=cur;
			}
			if(zeroCount == 1){
				intArray[rowIdx][zeroIdx] = CHK_SUM - sum;
			}
		}
	}
	
	private void solveCol(int intArray[][]){
		for(int colIdx = 0; colIdx < intArray[0].length; colIdx++){
			int sum = 0;
			int zeroCount = 0;
			int zeroIdx = 0;
			for(int rowIdx = 0; rowIdx<intArray.length; rowIdx++){
				int cur = intArray[rowIdx][colIdx];
				if(cur == 0){
					zeroCount++;
					zeroIdx = rowIdx;
				}
				sum+=cur;
			}
			if(zeroCount == 1){
				intArray[zeroIdx][colIdx] = CHK_SUM - sum;
			}
		}
	}
	
	private void solveSquare(int intArray[][]){
		solveSquare(intArray, 0, 2, 0, 2);
		solveSquare(intArray, 3, 5, 0, 2);
		solveSquare(intArray, 6, 8, 0, 2);
		
		solveSquare(intArray, 0, 2, 3, 5);
		solveSquare(intArray, 3, 5, 3, 5);
		solveSquare(intArray, 6, 8, 3, 5);
		
		solveSquare(intArray, 0, 2, 6, 8);
		solveSquare(intArray, 3, 5, 6, 8);
		solveSquare(intArray, 6, 8, 6, 8);
	}
	
	private void solveSquare(int intArray[][], int rowStart, int rowEnd, int colStart, int colEnd){
		int sum = 0;
		int zeroCount = 0;
		int zeroRowIdx = 0;
		int zeroColIdx = 0;
		for(int rowIdx = rowStart; rowIdx<= rowEnd; rowIdx++){
			for(int colIdx = colStart; colIdx <= colEnd; colIdx++){
				int cur = intArray[rowIdx][colIdx];
				if(cur == 0){
					zeroCount++;
					zeroRowIdx = rowIdx;
					zeroColIdx = colIdx;
				}
				sum+=cur;
			}
		}
		if(zeroCount == 1){
			intArray[zeroRowIdx][zeroColIdx] = CHK_SUM - sum;
		}
	}
	
	
	
	private int[][] parseIntArray(String[] parseInput){
		int intArray[][] = new int[parseInput.length][];
		
		for (int rowIdx=0; rowIdx < parseInput.length; rowIdx++) {
			String line = parseInput[rowIdx];
			String colValues[] = line.split(" ");
			intArray[rowIdx] = new int[colValues.length];
			
			for(int colIdx = 0; colIdx<colValues.length; colIdx++){
				intArray[rowIdx][colIdx] = Integer.parseInt(colValues[colIdx]);
			}
		}
		return intArray;
	}

	private static void print(String[] result) {
		for (String resultLine : result) {
			System.out.println(resultLine);
		}
	}

	private static String[] parseInput() throws IOException {
		Path filePath = new File("data/sudoku.txt").toPath();
		Charset charset = Charset.forName("UTF-8");

		List<String> stringList = Files.readAllLines(filePath, charset);
		return stringList.toArray(new String[] {});
	}

}
