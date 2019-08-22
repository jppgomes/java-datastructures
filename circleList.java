
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
 
/**
 * @author Crunchify.com
 * 
 */
 
public class Main {
 
	private static Integer[] crunchifyArray;
	final private int arraySize;
	private static int first = 0;
	private int last = 0;
	private static int length = 0;
	static int sizeForDemo = 10;
 
	public Main(int arraySize) {
		super();
		this.arraySize = arraySize;
		Main.crunchifyArray = new Integer[arraySize];
	}
 
	public static void main(String args[]) {
		Main crunchifyObj = new Main(sizeForDemo);
 
		for (int i = 1; i <= 15; i++) {
			int crunchifyInteger = ThreadLocalRandom.current().nextInt(1, 50);
			log("Adding element: " + crunchifyInteger);
			crunchifyObj.put(crunchifyInteger);
		}
 
		log("\nHere is sorted ArrayList (last 10 elements): ");
		Integer[] sortedArray = getSortedArrayList();
 
		for (int crunchifyArrayVal : sortedArray) {
			log(crunchifyArrayVal + " ");
		}
	}
 
	// Simple Log Utility
	private static void log(Object value) {
		System.out.println(value);
 
	}
 
	// Get First Element
	public static int getFront() {
		return first;
	}
 
	// Put element into Circular ArrayList
	public synchronized void put(int element) {
		crunchifyArray[last] = element;
		if (length < arraySize) {
			length++;
		} else {
			first = (first + 1) % arraySize;
		}
		last = (last + 1) % arraySize;
	}
 
	// Sort crunchifyArray
	public static Integer[] getSortedArrayList() {
		if (length == 0) {
			return null;
		}
		Integer[] newArray = new Integer[length];
		System.arraycopy(crunchifyArray, 0, newArray, 0, length);
		//Arrays.sort(newArray);
		return Arrays.copyOf(newArray, sizeForDemo);
	}
 
	// Get array Length
	public int getLength() {
		return length;
	}
}