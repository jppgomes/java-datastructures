package org.sjbanerjee.ignite;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

 
public class Main {
 
	private static Map<String, String>[] crunchifyArray;
	final private int arraySize;
	private static int first = 0;
	private int last = 0;
	private static int length = 0;
	static int sizeForDemo = 10;
 
	public Main(int arraySize) {
		super();
		this.arraySize = arraySize;
		Main.crunchifyArray =  (Map<String, String>[]) new Map[arraySize];
	}
 
	public static void main(String args[]) {
		for (int j = 1; j <= 15; j++) {
	
			Main crunchifyObj = new Main(sizeForDemo);
			System.out.println("Main: " + crunchifyObj);
			for (int i = 1; i <= 15; i++) {
				String userData = "{nmCli: jp} " + i;
				log("Adding element: " + userData);
				Map<String, String> usersDataMap = new HashMap<String, String>();
				usersDataMap.put(String.valueOf(i) + " 03921-kds", userData);
				crunchifyObj.put(usersDataMap);
			}
	 
			log("\nHere is sorted ArrayList (last 10 elements): ");
			Map<String, String>[] sortedArray = getSortedArrayList();
	 
			for (Map<String, String> crunchifyArrayVal : sortedArray) {
				log(crunchifyArrayVal + " ");
			}
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
	public synchronized void put(Map<String, String> element) {
		crunchifyArray[last] = element;
		if (length < arraySize) {
			length++;
		} else {
			first = (first + 1) % arraySize;
		}
		last = (last + 1) % arraySize;
	}
 
	// Sort crunchifyArray
	public static Map<String, String>[] getSortedArrayList() {
		if (length == 0) {
			return null;
		}
		Map<String, String>[] newArray =  (Map<String, String>[]) new Map[length];
		System.arraycopy(crunchifyArray, 0, newArray, 0, length);
		//Arrays.sort(newArray);
		return Arrays.copyOf(newArray, sizeForDemo);
	}
 
	// Get array Length
	public int getLength() {
		return length;
	}
}