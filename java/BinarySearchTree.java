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