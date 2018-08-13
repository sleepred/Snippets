package com.lgcns.exercise.evaluate;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * 소괄호와 사칙연산, Math 클래스에서 제공하는 function로 구성된 수식을 String 형태로 제공한다.
 * 해당 수식의 계산 값을 구하는 함수를 작성하시오.
 * 필수 : java.util.Stack 사용
 */
/*
*  % java Evaluate 
*  ( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) ) 
*  101.0 
*
*  % java Evaulate
*  ( ( 1 + sqrt ( 5 ) ) / 2.0 ) 
*  1.618033988749895
*/

public class Evaluate {
	
	public static void main(String[] args) {
		Evaluate eval = new Evaluate();
		System.out.println("101.0 : " + eval.evaluate("( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )"));
		System.out.println("1.618033988749895 : " + eval.evaluate("( ( 1 + sqrt ( 5 ) ) / 2.0 )"));		
		System.out.println("6.6 : " + eval.evaluate("( 1 + 2 * 3 - sqrt ( 4 ) / 5 )"));
	}
	
	public double evaluate(String input) {
		Stack<String> stack = new Stack<String>();
		
		List<String> postFix = new ArrayList<String>();
		
		String[] inputArray = input.split(" ");
		for(String inputChar: inputArray) {
			//‘(‘를 만나면 스택에 푸시한다. 
			if (inputChar.equals("(")) {
				stack.push(inputChar);
			// ‘)’를 만나면 스택에서 ‘(‘가 나올 때까지 팝하여 출력하고 ‘(‘는 팝하여 버린다. 
			} else if (inputChar.equals(")")) {
				while(!stack.peek().equals("(")) {
					postFix.add((String)stack.pop());
				}
				if (stack.peek().equals("(")) {
					stack.pop();
				}

			//연산자를 만나면 스택에서 그 연산자보다 낮은 우선순위의 연산자를 만날 때까지  
		    //팝하여 출력한 뒤에 자신을 푸시한다.(우선순위가 같거나 높은것은 팝한다.) 
			} else if (isOperator(inputChar)) {
				while (getPrecedence((String) stack.peek()) < getPrecedence(inputChar)) {
					postFix.add((String)stack.pop());
				}
				stack.push(inputChar);

			} else {
				postFix.add(inputChar);
			}
		}
		
		while(!stack.isEmpty()){
			postFix.add((String)stack.pop());
		}
		
		System.out.println(postFix);

		for (int i=0; i<postFix.size(); i++) {
			if(isBinaryOperator(postFix.get(i))) {
				String second = (String) stack.pop();
				String first = (String) stack.pop();
				if (postFix.get(i).equals("*")) {
					stack.push(String.valueOf(Double.parseDouble(first) * Double.parseDouble(second)));	
				} else if (postFix.get(i).equals("/")) {
					stack.push(String.valueOf(Double.parseDouble(first) / Double.parseDouble(second)));	
				} else if (postFix.get(i).equals("+")) {
					stack.push(String.valueOf(Double.parseDouble(first) + Double.parseDouble(second)));	
				} else if (postFix.get(i).equals("-")) {
					stack.push(String.valueOf(Double.parseDouble(first) - Double.parseDouble(second)));	
				}  
			} else if (isUnaryOperator(postFix.get(i))){
				if (postFix.get(i).equals("sqrt")) {
					stack.push(String.valueOf(Math.sqrt(Double.parseDouble((String)stack.pop()))));
				}
			} else {
				stack.push(postFix.get(i));
			}
		}
		return Double.parseDouble(stack.pop());
	}
	
	private boolean isBinaryOperator(String inputChar) {
		if (inputChar.equals("*") || inputChar.equals("/")
				|| inputChar.equals("+") || inputChar.equals("-")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isUnaryOperator(String inputChar) {
		if (inputChar.equals("sqrt")){
			return true;
		} else {
			return false;
		}
	}

	private boolean isOperator(String inputChar) {
		if ((inputChar.equals("*") || inputChar.equals("/")
				|| inputChar.equals("+") || inputChar.equals("-") || inputChar.equals("sqrt"))) {
			return true;
		} else {
			return false;
		}
	}
	private int getPrecedence(String operator){
		if (operator.equals("*") || operator.equals("/")) {
			return 1;
		} else if(operator.equals("sqrt")){
			return 0;
		} else { 
			return 2;
		}
	}
}
