
// TODO: add imports as needed

import static org.junit.jupiter.api.Assertions.*; // org.junit.Assert.*; 
import org.junit.jupiter.api.Assertions;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Random;

/** TODO: add class header comments here */
public class HashTableTest {

	// TODO: add other fields that will be used by multiple tests

	// TODO: add code that runs before each test method
	@Before
	public void setUp() throws Exception {

	}

	// TODO: add code that runs after each test method
	@After
	public void tearDown() throws Exception {

	}

	/**
	 * Tests that a HashTable returns an integer code indicating which collision
	 * resolution strategy is used. REFER TO HashTableADT for valid collision scheme
	 * codes.
	 */
	@Test
	public void test000_collision_scheme() {
		HashTableADT htIntegerKey = new HashTable<Integer, String>();
		int scheme = htIntegerKey.getCollisionResolution();
		if (scheme < 1 || scheme > 9)
			fail("collision resolution must be indicated with 1-9");
	}

	/**
	 * IMPLEMENTED AS EXAMPLE FOR YOU Tests that insert(null,null) throws
	 * IllegalNullKeyException
	 */
	@Test
	public void test001_IllegalNullKey() {
		try {
			HashTableADT htIntegerKey = new HashTable<Integer, String>();
			htIntegerKey.insert(null, null);
			fail("should not be able to insert null key");
		} catch (IllegalNullKeyException e) {
			/* expected */ } catch (Exception e) {
			fail("insert null key should not throw exception " + e.getClass().getName());
		}
	}

	/**
	 * Tests that inserting duplicate keys will throw a Duplicate key exception
	 */
	@Test
	public void test002_DuplicateKey() {
		try {
			// Create new Hashtable and insert new key into HashTable
			HashTableADT htIntegerKey = new HashTable<Integer, String>();
			htIntegerKey.insert(1, "A");
			htIntegerKey.insert(1, "A");
			// fails if duplicate key is successfully inserted
			fail("should not be able to insert duplicate key");
		} catch (DuplicateKeyException e) {
			/* expected */ } catch (Exception e) {
			fail("insert null key should not throw exception " + e.getClass().getName());
		}
	}

	/**
	 * Tests that getLoadFactorThreshold is equal to the one set in the constructor
	 * with no arguments
	 * 
	 */
	@Test
	public void test003_getLoadFactorThreshold() {

		// Create new Hashtable and insert new key into HashTable
		HashTableADT htIntegerKey = new HashTable<Integer, String>();

		if (!(htIntegerKey.getLoadFactorThreshold() == 0.75)) {
			fail("table size should have been duplicated");
		}

	}

	/**
	 * Tests that a HashTable throws a NoSuchElementException if try to get a
	 * negative key value
	 */
	@Test
	public void test004_get_Negative_Value()
			throws NoSuchElementException, IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
		// Create new Hashtable and insert new key into HashTable
		HashTableADT htIntegerKey = new HashTable<Integer, String>();

		htIntegerKey.insert(1, "A");
		htIntegerKey.insert(2, "B");
		htIntegerKey.insert(3, "C");

		// fails if get(key) does not catch an exception
		try {
			htIntegerKey.get(-1);
			// should probably be an index out of bounds exception
			fail("Should have thrown a NoSuchElementException");
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	/**
	 * tests capacity is getting resized
	 * 
	 * @throws NoSuchElementException, IllegalNullKeyException, KeyNotFoundException
	 */
	@Test
	public void test005_get__fixed_capacity()
			throws NoSuchElementException, IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
		// try {
		// Create new Hashtable and insert new key into HashTable
		HashTableADT htIntegerKey = new HashTable<Integer, String>();
		int initialCapacity = 5;
		Double loadFactorThreshold = 0.75;
		Double loadFactor = 0.0;
		int numKeys = 0;

		// inserts four keys
		htIntegerKey.insert(1, "A");
		htIntegerKey.insert(2, "B");
		htIntegerKey.insert(3, "C");
		htIntegerKey.insert(4, "C");

		// if capacity has not increased, fail
		if (!(htIntegerKey.getCapacity() == 11) || htIntegerKey.getCapacity() == 5) {
			fail("Capacity should have increased");
		}
	}

	/**
	 * Tests that proper load factor is returned
	 * 
	 * @throws NoSuchElementException, IllegalNullKeyException, KeyNotFoundException
	 */
	@Test
	public void test006_get__load_Factor()
			throws NoSuchElementException, IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
		// try {
		// Create new Hashtable and insert new key into HashTable
		HashTableADT htIntegerKey = new HashTable<Integer, String>(5, 0.75);
//		int initialCapacity = 5;
//		Double loadFactor = 0.0;
		int numKeys = 0;

		htIntegerKey.insert(1, "A");
		numKeys++;
		htIntegerKey.insert(2, "B");
		numKeys++;
		htIntegerKey.insert(3, "C");
		numKeys++;

		// if loadFactor is correctly computated, fail
		if (!(htIntegerKey.getLoadFactor() == 0.6)) {
			fail("loadFactor should be nothing but this value");
		}
	}

	/**
	 * Tests that right number of keys is returned
	 * 
	 * @throws NoSuchElementException, IllegalNullKeyException,
	 *                                 KeyNotFoundException, KeyNotFoundException
	 */
	@Test
	public void test007_get_numKeys()
			throws NoSuchElementException, IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
		// Create new Hashtable and insert new key into HashTable
		HashTableADT htIntegerKey = new HashTable<Integer, String>();
		int initialCapacity = 5;
		Double loadFactor = 0.0;
		int numKeys = 0;

		htIntegerKey.insert(1, "A");
		// numKeys++;
		htIntegerKey.insert(2, "B");
		// numKeys++;
		htIntegerKey.insert(3, "C");
		// numKeys++;

		if (!(htIntegerKey.numKeys() == 3)) {
			fail("number of keys should be 3");
		}
	}

	/**
	 * Tests that insert and get are working
	 * 
	 * @throws NoSuchElementException, IllegalNullKeyException,
	 *                                 KeyNotFoundException, DuplicateKeyException
	 */
	@Test
	public void test008_insert_two_get_one_key()
			throws NoSuchElementException, IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
		// Create new Hashtable and insert new key into HashTable
		HashTableADT htIntegerKey = new HashTable<Integer, String>();
		int initialCapacity = 5;
		Double loadFactor = 0.0;
		int numKeys = 0;

		htIntegerKey.insert(1, "A");
		// numKeys++;
		htIntegerKey.insert(2, "B");
		// numKeys++;

		// ensures that the associated value of the key is returned
		if (!(htIntegerKey.get(1) == "A"))
			fail("value of extracted key should be A");
	}

	/**
	 * Tests that insert and remove work
	 * 
	 * @throws NoSuchElementException, IllegalNullKeyException,
	 *                                 KeyNotFoundException,
	 *                                 DuplicateKeyFoundException
	 */
	@Test
	public void test009_insert_two_remove_one_key()
			throws NoSuchElementException, IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
		// try {
		// Create new Hashtable and insert new key into HashTable
		HashTableADT htIntegerKey = new HashTable<Integer, String>();
//		int initialCapacity = 5;
//		Double loadFactor = 0.0;
		int numKeys = 0;

		htIntegerKey.insert(1, "A");
		// numKeys++;
		htIntegerKey.insert(2, "B");
		// numKeys++;

		htIntegerKey.remove(2);

		// if the key 2 is returned, then the remove function does not work
		try {
			htIntegerKey.get(2);
			fail("This should have returned a KeyNotFoundException");
		} catch (KeyNotFoundException e) {
		}

	}

	/**
	 * Tests that getLoadFactor work
	 * 
	 * @throws NoSuchElementException, IllegalNullKeyException,
	 *                                 KeyNotFoundException,
	 *                                 DuplicateKeyFoundException
	 */
	@Test
	public void test011_get_initial_capacity_LF()
			throws NoSuchElementException, IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
		// Create new Hashtable and insert new key into HashTable
		HashTableADT htIntegerKey = new HashTable<Integer, String>();

		// fail if not true
		if (!(htIntegerKey.getLoadFactor() == 0))
			fail("load factor for hashTable with no keys and initial capacity of 11 shoudl return 0");
	}

	/**
	 * Tests that remove non existing key works
	 * 
	 * @throws NoSuchElementException, IllegalNullKeyException,
	 *                                 KeyNotFoundException,
	 *                                 DuplicateKeyFoundException
	 */
	@Test
	public void test012_remove_non_existing()
			throws NoSuchElementException, IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
		// try {
		// Create new Hashtable and insert new key into HashTable
		HashTableADT htIntegerKey = new HashTable<Integer, String>();

		htIntegerKey.insert(1, "A");

		htIntegerKey.insert(2, "B");

		// fails if it removes a key that is not inserted
		htIntegerKey.remove(3);
		fail("KeyNotFoundExceptionShouldHaveBeenThrown");

	}

	/**
	 * checks that load Factor is updated
	 * 
	 * @throws NoSuchElementException, IllegalNullKeyException,
	 *                                 KeyNotFoundException,
	 *                                 DuplicateKeyFoundException
	 */
	@Test
	public void test013_update_load_factor()
			throws NoSuchElementException, IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
		// try {
		// Create new Hashtable and insert new key into HashTable
		HashTableADT htIntegerKey = new HashTable<Integer, String>(5, 0.75);

		htIntegerKey.insert(1, "A");

		htIntegerKey.insert(2, "B");

		htIntegerKey.insert(3, "C");

		// htIntegerKey.insert(4, "D");

		if (htIntegerKey.getLoadFactor() != 0.6)
			fail("KeyNotFoundExceptionShouldHaveBeenThrown");

	}
	
	@Test
	public void test014_insert_many()
			throws NoSuchElementException, IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
		// try {
		// Create new Hashtable and insert new key into HashTable
		HashTableADT htIntegerKey = new HashTable<Integer, String>(5, 0.75);
//
//		htIntegerKey.insert(1, "A");
//
//		htIntegerKey.insert(2, "B");
//
//		htIntegerKey.insert(3, "C");
		
		for(int i = 0; i < 100; i++) {
			System.out.print(i);
			htIntegerKey.insert(i, "i");
			
		}

		//double loadFactor = htIntegerKey.getLoadFactor() ;

		if (htIntegerKey.numKeys() != 100)
			fail("KeyNotFoundExceptionShouldHaveBeenThrown");

	}



}
