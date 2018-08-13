/*
<1번 문항>

https://www.testdome.com/Questions/Java/BinarySearchTree/5373 (40min)
주어진 binary tree가 유효한 binary search tree(BST)인지 체크하는 function을 작성하시오.
binary search tree는 모든 node가 아래의 조건을 만족하는 binary tree이다.
- node의 값은 좌측 subtree의 모든 node의 값보다 크거나 같다.
- node의 값은 우측 subtree의 모든 node의 값보다 작다.

예를 들어 아래의 tree에 대해
 . n1 (Value: 1, Left: null, Right: null)
 . n2 (Value: 2, Left: n1, Right: n3)
 . n3 (Value: 3, Left: null, Right: null)
 
isValidBST(n2)의 return 값은 true이다.
설명) 
n1과 n3는 child가 존재하지 않는 유효한 binary search tree이다.
n2의 값 2는 좌측 subtree n1의 최대값 1보다 크거나 같으며, 
우측 subtree의 최소값 3보다 작으므로 n2는 유효한 binary search tree이다.
**/    

class Node {
    public int value;
    public Node left, right;

    public Node(int value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
}

public class BinarySearchTree {
    public static boolean isValidBST(Node root) {
    	boolean left = search(root.left, Integer.MIN_VALUE, root.value);
    	boolean right = search(root.right, root.value, Integer.MAX_VALUE);
    	
    	return left && right;
    }
    
    private static boolean search(Node node, int min, int max){
    	if(node == null){
    		return true;
    	}
    	
    	if(node.value <= min || node.value > max){
    		return false;
    	}
    	
    	if(!search(node.left, min, node.value)){
    		return false;
    	}
    	
    	if(!search(node.right, node.value, max)){
    		return false;
    	}
    	
    	return true;
    }
    
    public static void main(String[] args) {
        Node n1 = new Node(1, null, null);
        Node n3 = new Node(3, null, null);
        Node n2 = new Node(2, n1, n3);

        System.out.println(isValidBST(n2));
    }
}
