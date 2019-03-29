/**
 * Filename:   MyProfiler.java
 * Project:    p3b-201901     
 * Authors:    TODO: add your name(s) and lecture numbers here
 *
 * Semester:   Spring 2019
 * Course:     CS400
 * 
 * Due Date:   TODO: add assignment due date and time
 * Version:    1.0
 * 
 * Credits:    TODO: name individuals and sources outside of course staff
 * 
 * Bugs:       TODO: add any known bugs, or unsolved problems here
 */

// Used as the data structure to test our hash table against
import java.util.TreeMap;

public class MyProfiler<K extends Comparable<K>, V> {

    HashTableADT<K, V> hashtable;
    TreeMap<K, V> treemap;
    
    public MyProfiler() {
        // TODO: complete the Profile constructor
        // Instantiate your HashTable and Java's TreeMap
    	
    	//calling the HashTable() and the TreeMap() method should create an instance of it 
    	hashtable = new HashTable<K, V>();
    	treemap = new TreeMap<K, V>();
    	
    
    }
    
    public void insert(K key, V value) {
    
    	
    	try {
			hashtable.insert(key,value);
		} catch (IllegalNullKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//inserts new key value pairing into treemap
    	treemap.put(key,  value);
    	
        // TODO: complete insert method
        // Insert K, V into both data structures
    }
    
    public void retrieve(K key) {
    	
    	//calls the get method on hashtable
    	try {
			hashtable.get(key);
		} catch (IllegalNullKeyException e) {
			// catches exception if key is null
			e.printStackTrace();
		} catch (KeyNotFoundException e) {
			//catches exception if key is not found
			e.printStackTrace();
		}
    	
    	//retrieves key's corresponding value
    	treemap.get(key);
    	
        // TODO: complete the retrieve method
        // get value V for key K from data structures
    }
    
    public static void main(String[] args) {
        //try {
            int numElements = Integer.parseInt(args[0]);

            MyProfiler<Integer, Integer> myProfile = new MyProfiler<Integer, Integer>();
            
            int i = 0;
            int j = 0;
            
            //repeatedly inserting into myProfile as long as i is less than or equal to the numElements
            while (i < numElements) {
            	myProfile.insert(i, i);
            	i++;
            }
            
//            for (int a = 0; a <= numElements; a++) {
//            	//myProfile.insert(a);
//            }
          //repeatedly getting from myProfile as long as i is less than or equal to the numElements
            
            while (j < numElements) {
            	//myProfile.get(key);
            	j++;
            }
            
//            for (int g = 0; g <= numElements; g++) {
//            	//myProfile.get(g);
//            }
            // TODO: complete the main method. 
            // Create a profile object. 
            // For example, Profile<Integer, Integer> profile = new Profile<Integer, Integer>();
            // execute the insert method of profile as many times as numElements
            // execute the retrieve method of profile as many times as numElements
            // See, ProfileSample.java for example.
            
        
            String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
            System.out.println(msg);
//        }
//        catch (Exception e) {
//            System.out.println("Usage: java MyProfiler <number_of_elements>");
//            System.exit(1);
//        }
    }
}
