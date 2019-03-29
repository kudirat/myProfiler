
	import java.util.ArrayList;
	import java.util.LinkedList;


	// describe the collision resolution scheme you have chosen
	
	//
	// explain your hashing algorithm here
	//
	// NOTE: you are not required to design your own algorithm for hashing,
	// since you do not know the type for K,
	// you must use the hashCode provided by the <K key> object
	// and one of the techniques presented in lecture
	

	/**
	 * 
	 * @author deepl
	 *
	 */
	public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
		
		 /**
	     * This private class will be used to manage the key and value pairings
	     *that will be used to create nodes to be inserted into the hashTable data structure
	     */
	    private class HashTableNode {
	    	//key value variables to be initialized in constructor
	        private K key; 
	        private V value; 

	        /**
	         * Initializes the key value variables with values that will be passed into the parameter
	         * Key Value pairings will be stored into a node object that will be used for the data structure
	         * @param key, value
	         */
	        private HashTableNode(K key, V value) {
	            this.key = key;
	            this.value = value;
	        }
	    }
		
		// private fields that will later be used for implementation

		private int initialCapacity;

		private double loadFactorThreshold;

		private double loadFactor;

		private int numKeys;

		private V value;

		//hashTable will be declared as a ArrayList of LinkedLists of elements of type HashTableNode
	    private ArrayList<LinkedList<HashTableNode>> hashTable; 
	    
	 // Arraylist that will hold elements that will be inserted into table
	    private LinkedList<HashTableNode> arrBuckets = new LinkedList<HashTableNode>(); 
	   
	
	    /**
		 * This method sets the key value pair that can be used to create a node to be
		 * inserted into the table
		 * 
		 * @param key, value
		 */
	    public HashTable() {
	    	// initializing private fields
			this.loadFactorThreshold = 0.75;
			this.initialCapacity = 11;		
			this.numKeys = 0;
			this.loadFactor = 0.0;
			//initializing ArrayList of LinkedList of elements of type HashTableNode
		     hashTable = new ArrayList<LinkedList<HashTableNode>>(); 
		     
		    //Populates the hashTable/ArrayList with linked lists in each of the indexes until it
		     //reaches the default table capacity
			for (int index = 0; index < initialCapacity; ++index) {
			hashTable.add(new LinkedList<>());
			}
	 
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
			this.loadFactor = 0.0;
			//initializing ArrayList of LinkedList of elements of type HashTableNode
		    hashTable = new ArrayList<LinkedList<HashTableNode>>();
		    //Populates the hashTable/ArrayList with linked lists in each of the indexes until it
		     //reaches the default table capacity
			for (int index = 0; index < initialCapacity; ++index) {
			hashTable.add(new LinkedList<>());
			}
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
	        
	        // gets the hashcode for specified key as well as the index that it will be
			// inserted in the hashtable
	        int keyHash = hash(key);
	        int keyIndex = keyHash % initialCapacity;
	        //create a new HashTableNode object that will be created using the key, value pairing
	        //this elemtn will be added intot he data structure
	        HashTableNode newNode = new HashTableNode(key, value);
	        
	        // the linked list where we'll specifically be inserting the new node
	        LinkedList<HashTableNode> destination = hashTable.get(keyIndex);
	        
	        //this loop checks for a duplicate key in destination linked list
	        for (int index = 0; index < destination.size(); ++index) {
	            // if a key is found in the linked list that equals the key to be inserted, DuplicateKeyException is thrown
	            if (destination.get(index).key.equals(key)) {
	                throw new DuplicateKeyException();
	            }
	        }
	        
	        System.out.print(this.getLoadFactor());
	        
	        if(this.getLoadFactorThreshold() <= this.getLoadFactor()) {
		        rehash();
		        }
	        //Once the duplicate key is checked for, and none has been found, we add the new node to the linked list
	        destination.add(newNode);
	        
	        //sets the linkedList at the correct hashed index
	        hashTable.set(keyIndex, destination); 
	        
	        //we add the newNode in arrBuckets as well so that when a rehash occurs, we'll be able to keep track of the elements in the original table
	        arrBuckets.add(newNode); 
	        //increment the number of keys once a new element is added
	        numKeys++; 
	     // updates the capacity and the load factor
	     // after adding key, check capacity, capacity will call for a rehash if loadFactor is greater than the loadFactorThreshold
	        

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
			
			//will keep track of whether or not the key has been removed
			K keyRetrieved = null;
			boolean keyRemoved = false;
			// if key is null, throw exception
	        if (key == null) {
	            throw new IllegalNullKeyException();
	        }

	     // gets the hashcode for specified key as well as the index that it will be
			// deleted from hashTable
			int keyHash = Math.abs(key.hashCode());
			int keyIndex = keyHash % initialCapacity;
	        
			//linked list that we will be searching through for the key
	        LinkedList<HashTableNode> destination = hashTable.get(keyIndex);
	        
	        //loop that will aid in searching through linkedlist
	        for (int i = 0; i <= destination.size(); ++i) {
	            // if key at i equals key, then remove key at i
	        	keyRetrieved = destination.get(keyIndex).key;
	            if (keyRetrieved.equals(key)) {
	            	destination.remove(keyRetrieved); 
	            	//also remove from arrBuckets. We will no longer need to keep track of it
	                arrBuckets.remove(destination.get(i)); 
	                
	                // decrement number of keys and break out of loop
	                numKeys--;
	                return keyRemoved = true;
	            }
	        }
	        return keyRemoved; // if key is not found
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
			
			//will be used to store the key value when found
			V keyVal = null;
			
			//if key is null, throw new exception
	        if (key == null) {
	            throw new IllegalNullKeyException();
	        }
	        
	        //gets index of where in the table we will be searching in
	        int keyHash = key.hashCode();
			int keyIndex = keyHash % initialCapacity;

			//we will be searching through this linked list for our key
	        LinkedList<HashTableNode> destination = hashTable.get(keyIndex);
	        //loop through the linked list until we find the key
	        for (int index = 0; index < destination.size(); ++index) {
	        	//if key at index equals our key, then 
	            if (destination.get(index).key.equals(key)) {
	                keyVal =  destination.get(index).value;
	            }
	        }
	        //if keyVal is not set to a value and is still null, throw exception
	        if (keyVal == null) {
	        	throw new KeyNotFoundException();
	        }
	        return keyVal;
	    }
		
		 /**
	     * using java's built in hash code function, this method will generate a hashcode for each key passed through
	     * 
	     * @param key
	     * @return hashIndex
	     */
	    public int hash(K key) {
	        int hashIndex;
	        hashIndex = Math.abs(key.hashCode()) % getCapacity();
	        return hashIndex;
	    }
	    /**
	     *This method sets the value of initialCapacity and loadFactorThreshold with
	     * parameters inserted by users
	     */
	    private void rehash() {
	        
	    	int keyIndex = 0;
	    	
	    	//create a place holder table and populate it with linkedlists
	        ArrayList<LinkedList<HashTableNode>> temp = new ArrayList<LinkedList<HashTableNode>>();
	        
	        
	        for (int i = 0; i < initialCapacity; ++i) {
	            temp.add(new LinkedList<>());
	        }
	        
	        //we will use a loop to loop through arrBuckets that has been holding the elements that have been inserted into hashTable
	        for (int index = 0; index < arrBuckets.size(); ++index) {
	        	//rehashes key at index 
	            keyIndex = hash(arrBuckets.get(index).key); 
	            
	            // creates a new node with key value pair in arrBuckets
	            HashTableNode newNode = new HashTableNode(arrBuckets.get(index).key, arrBuckets.get(index).value);
	            //inserting the newNode at that hash index
	            LinkedList<HashTableNode> destination = temp.get(keyIndex);
	            //insert in linked list
	            destination.add(newNode); 
	          //sets the linkedList at the correct hashed index
	          //  destination.set(keyIndex, newNode); 
	        }
	        
	        //sets the hashTable contents to those of temp hashTable
	        this.hashTable = temp;

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
	        return loadFactorThreshold;
	    }

	    /**
	     * 
	     * Returns the current load factor for this hash table
	     *  load factor = number of items / current table size
	     */
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
			
//	        if (getLoadFactor() >= getLoadFactorThreshold()) {
//	            currCapacity = Math.abs(currCapacity * 2 + 1);
//	            //rehash will give a new hashCode and reallocate keys in the case that a new table is created
//	            rehash(); 
//	        }
	        return currCapacity;
	    }

	    /**
	     * @return returns scheme number which will be used to handle collisions
	     */
	    @Override
	    public int getCollisionResolution() {
	    	int schemeNum = 5;
			return schemeNum;
	    }
	    
	   
	}

	
	
	
	
	
	
	
	
	
	
	

