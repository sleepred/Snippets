package snippets;



import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;


public class SortUtil {
	public static int[] sort(int[] arr) {
		Arrays.sort(arr);
		return arr;
	}

	public static int[] sort(Enumeration<Integer> en, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = en.nextElement();
		}
		return sort(arr);
	}

	public static String[] sort(String[] arr) {
		Arrays.sort(arr);
		return arr;
	}
	
	public static String[] sort(String[] arr, boolean asc) {
		if (asc) {
			Arrays.sort(arr);
		} else {
			Arrays.sort(arr, Collections.reverseOrder());
		}
		return arr;
	}	

	public static String[] sort_string(Enumeration<String> en, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = (String) en.nextElement();
		}
		return sort(arr);
	}

	public static String[] sort_string(Iterator<String> itr, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = itr.next();
		}
		return sort(arr);
    }
	
	public static String[] sort_string(Iterator itr, int size, boolean asc) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = (String) itr.next();
		}
		return sort(arr, asc);
	}	

}