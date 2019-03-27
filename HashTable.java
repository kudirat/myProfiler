import java.util.ArrayList;
import java.util.NoSuchElementException;
// TODO: comment and complete your HashTableADT implementation
// DO ADD UNIMPLEMENTED PUBLIC METHODS FROM HashTableADT and DataStructureADT TO YOUR CLASS
// DO IMPLEMENT THE PUBLIC CONSTRUCTORS STARTED
// DO NOT ADD OTHER PUBLIC MEMBERS (fields or methods) TO YOUR CLASS
//
// TODO: implement all required methods
//
// TODO: describe the collision resolution scheme you have chosen
// identify your scheme as open addressing or bucket

// TODO: explain your hashing algorithm here 
// NOTE: you are not required to design your own algorithm for hashing,
//       since you do not know the type for K,
//       you must use the hashCode provided by the <K key> object
//       and one of the techniques presented in lecture
//
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

	// TODO: ADD and comment DATA FIELD MEMBERS needed for your implementation

	// TODO: comment and complete a default no-arg constructor

	// private fields that will later be used for implementation
	private int hashFunction;

	private int hashIndex;

	private int initialCapacity;

	private double loadFactorThreshold;

	private double loadFactor;

	private int numKeys;

	private V value;

	// inner class
	private class HashTableNode<K, V> {

		// private methods that will be used to create nodes
		private K key;
		private V value;

		/**
		 * This method returns the private field value
		 * 
		 * @return
		 */
		private V getValue() {
			return this.value;
		}

		/**
		 * This method returns the private field key
		 * 
		 * @return key
		 */
		private K getKey() {
			return this.key;
		}

		/**
		 * This method sets the value of the private field value
		 * 
		 * @param value
		 */
		private void setValue(V value) {
			this.value = value;
		}

		/**
		 * This method sets the key value pair that can be used to create a node to be
		 * inserted into the table
		 * 
		 * @param key, value
		 */
		public HashTableNode(K key, V value) {
			setKey(key);
			setValue(value);
		}

		/**
		 * This method sets the key that will be used to create a node to be inserted
		 * 
		 * @param key
		 */
		public void setKey(K key) {
			this.key = key;
		}

	}

	// hashTable array that will be the main table
	private HashTableNode[] hashTable;

	/**
	 * This method sets the value of initialCapacity and loadFactorThreshold with
	 * parameters inserted by users 3/14/19 - this method is giving me issues;
	 * commented method out
	 * 
	 * @param table
	 * @return newTable
	 * @throws IllegalNullException, KeyNotFoundException
	 */
//	private HashTableNode[] reHash(HashTableNode[] table) throws IllegalNullKeyException, KeyNotFoundException {
//		
//		
//	//new table that will hold elements from old table	
//	HashTableNode newTable[] = new HashTableNode[table.length*2 +1];
//	
//	//will be used in rehashing
//	int newCapacity = newTable.length;
//	
//	//creates a key that will hold the currKey
//	K currKey = null;
//	int newHash = 0;
//	
//	//iterates through the table, and rehashes elements to be added to the new table
//	for (int i = 0; i < initialCapacity; i++) {
//		//get the key at index i
//		//hash it according to new table
//		currKey = (K) table[i];
//		newHash = currKey.hashCode() % newCapacity;
//		HashTableNode node = new HashTableNode(currKey, get(currKey));
//		newTable[newHash] = node;
//		
//	}
//		return newTable;
//		
//	}

	/**
	 * This method sets the value of initialCapacity and loadFactorThreshold with
	 * parameters inserted by users
	 * 
	 * @param initialCapacity, loadFactorThreshold
	 */
	public HashTable() {
		// this.loadFactor = 0.0;
		this.loadFactorThreshold = 0.75;
		this.initialCapacity = 11;
		hashTable = new HashTableNode[initialCapacity];
		// hashTable = null;
		this.numKeys = 0;

	}

	/**
	 * This method sets the value of initialCapacity and loadFactorThreshold with
	 * parameters inserted by users
	 * 
	 * @param initialCapacity, loadFactorThreshold
	 */
	public HashTable(int initialCapacity, double loadFactorThreshold) {

		this.initialCapacity = initialCapacity;
		this.loadFactorThreshold = loadFactorThreshold;
		hashTable = new HashTableNode[initialCapacity];
		// this.loadFactor = 0.0;
		// hashTable = null;
		this.numKeys = 0;

	}

	/**
	 * This method inserts a key, value pairing at its specified keyIndex
	 * 
	 * @param key, value
	 * @throws IllegalNullKeyException, DuplicateKeyException
	 */
	@Override
	public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {

		// throws exception if key is null
		if (key == null) {
			throw new IllegalNullKeyException();
		}

		// stores key value pair in node to be inserted
		HashTableNode node = new HashTableNode(key, value);

		// gets the hashcode for specified key as well as the index that it will be
		// inserted in
		int keyHash = key.hashCode();
		int keyIndex = keyHash % initialCapacity;

		// if that index is null, insert the new node
		// increase the number of keys
		if (hashTable[keyIndex] == null) {
			hashTable[keyIndex] = node;
			numKeys++;
			// updates the capacity and the load factor
			// after adding key, check capacity, capacity makes a call to rehash
//			loadFactor = getLoadFactor();
//			initialCapacity = getCapacity();
//			try {
//				reHash(hashTable);
//			} catch (KeyNotFoundException e) {
//				e.printStackTrace();
//			}
		}
		// if not null
		else if (!(hashTable[keyIndex] == null)) {
			// iterate through table, until a null index is found
			for (int i = 0; i < initialCapacity; i++) {
				// if index is null, insert new node
				// increase number of keys
				if (hashTable[keyIndex] == null) {
					hashTable[keyIndex] = node;
					numKeys++;
					// updates the capacity and the load factor
					// after adding key, check capacity, capacity makes a call to rehash
//					loadFactor = getLoadFactor();
//					initialCapacity = getCapacity();
//					try {
//						reHash(hashTable);
//					} catch (KeyNotFoundException e) {
//						e.printStackTrace();
//					}
				}

				// implement what you would do if you found matching keys and had to update the
				// value
				else if (hashTable[keyIndex] == key) {
					throw new DuplicateKeyException();
				}
			}
			// updates the capacity and the load factor
			loadFactor = getLoadFactor();
			initialCapacity = getCapacity();
		}

	}

	/**
	 * This method removes the key when found in the hashTable
	 * 
	 * @param key
	 * @return keyVal
	 * @throws IllegalNullKeyException
	 */
	@Override
	public boolean remove(K key) throws IllegalNullKeyException {

		boolean keyRemoved = false;

		// if key is null, throw exception
		if (key == null) {
			throw new IllegalNullKeyException();
		}

		// gets the hashcode for specified key as well as the index that it will be
		// inserted in
		int keyHash = key.hashCode();
		int keyIndex = keyHash % initialCapacity;

		// will be used as a counter to iterate through table
		int i = 0;

		// while i is not greater than the initialCapacity
		while (i < initialCapacity) {
			// if the key is at the index, set it to null
			if (hashTable[keyIndex] == key) {
				hashTable[keyIndex] = null;
				// decrease the amount of keys and break out of loop
				numKeys--;
				keyRemoved = true;
				break;
			} else {
				keyIndex++;
			}

			i++;
		}

		return keyRemoved;
	}

	/**
	 * This method retrieves the value associated with the specified key
	 * 
	 * @param key
	 * @return keyVal
	 * @throws IllegalNullKeyException, KeyNotFoundException
	 */
	@Override
	public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
		// TODO Auto-generated method stub

		V keyVal = null;
		// if key is null, throw new exception
		if (key == null) {
			throw new IllegalNullKeyException();
		}

		// gets the hashcode for specified key as well as the index that it will be
		// inserted in
		int keyHash = key.hashCode();
		int keyIndex = keyHash % initialCapacity;
		// throws exception if keyIndex is out of bounds
		if (keyIndex < 0) {
			throw new ArrayIndexOutOfBoundsException();
		}

		try {
			if (hashTable[keyIndex] != null) {
				keyVal = (V) hashTable[keyIndex].getValue();
			}
			// catches exception if thrown
		} catch (NoSuchElementException e) {

		}

		return keyVal;
	}

	/**
	 * Returns the number of keys
	 * 
	 * @returns numKeys
	 */
	@Override
	public int numKeys() {
		return numKeys;
	}

	/**
	 * This method returns the loadFactorThreshold that was passed into the
	 * constructor when making an instance of the hashTable
	 * 
	 * @return loadFactorThreshold
	 */
	@Override
	public double getLoadFactorThreshold() {
		if (loadFactor >= loadFactorThreshold) {
			// ensures that the table is resized and will have a positive value as its size
			initialCapacity = Math.abs(initialCapacity * 2 + 1);
		}
		// TODO Auto-generated method stub
		return loadFactorThreshold;
	}

	// Returns the current load factor for this hash table
	// load factor = number of items / current table size
	@Override
	public double getLoadFactor() {
		// TODO Auto-generated method stub
		double currLoadFactor = (double) numKeys / initialCapacity;
		return currLoadFactor;
	}

	/**
	 * Returns the current capacity(tableSize) of the hashTable Increases the
	 * tableSize once loadFactorThreshold is reached
	 * 
	 * @return initialCapacity
	 */
	@Override
	public int getCapacity() {

		int currCapacity = initialCapacity;
		// if loadFactorThreshold is reached
		if (loadFactor >= loadFactorThreshold) {
			// ensures that the table is resized and will have a positive value as its size
			currCapacity = Math.abs(currCapacity * 2 + 1);
		}

		return currCapacity;
	}

	/**
	 * This main method creates an instance of hashTable and tests the methods for
	 * it
	 * 
	 * @return returns scheme number which will be used to handle collisions
	 */
	@Override
	public int getCollisionResolution() {
		// TODO Auto-generated method stub
		int schemeNum = 4;
		return schemeNum;
	}

	/**
	 * This main method creates an instance of hashTable and tests the methods for
	 * it
	 * 
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		HashTable newTable = new HashTable();
//		
//		try {
//			newTable.insert(1, "A");
//		} catch (IllegalNullKeyException e) {
//		} catch (DuplicateKeyException e) {
//		}
//		
//		double factor = newTable.getLoadFactor();
//		
////		for (int i = 0; i < newTable.initialCapacity; i++) {
////		System.out.print(newTable);
////		}
////		
//		
//		
//	}
//		
}