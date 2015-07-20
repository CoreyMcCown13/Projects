public class Map<KeyType, ValueType> {
	private static final int SIZE = 16;

	private Entry<KeyType,ValueType> items[] = new Entry[SIZE];

	class Entry<KeyType,ValueType> {
		KeyType key;
		ValueType val;
		Entry<KeyType,ValueType> next;

		Entry(KeyType k, ValueType v) {
			key = k;
			val = v;
		}
	}

	public void put(KeyType thisKey, ValueType thisValue) {
		int hash = thisKey.hashCode() % SIZE;
		Entry<KeyType, ValueType> thisEntry = items[hash];

		if(thisEntry != null) {
			if(thisEntry.key.equals(thisKey))
				thisEntry.val = thisValue;

			else {
				while(thisEntry.next != null) 
					thisEntry = thisEntry.next;

				Entry<KeyType, ValueType> oldEntry = new Entry<KeyType, ValueType>(thisKey, thisValue);
				thisEntry.next = oldEntry;
			}

		} else {
			Entry<KeyType, ValueType> newEntry = new Entry<KeyType, ValueType>(thisKey, thisValue);
			items[hash] = newEntry;
		}
	}
	
	public ValueType get(KeyType thisKey) {
		int hash = thisKey.hashCode() % SIZE;
		Entry<KeyType,ValueType> thisEntry = items[hash];

		while(thisEntry != null) {
			if(thisEntry.key.equals(thisKey)) 
				return thisEntry.val;

			thisEntry = thisEntry.next;
		}
		return null;
	}

	public boolean isEmpty()
	{
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) 
				return false;
		}
		return true;
	}

	public void makeEmpty()
	{
		items = new Entry[SIZE];
	}

	public static void main(String[] args) {
		Map<Integer, String> testMap = new Map<Integer, String>();
		System.out.println("New Map Created.");
		System.out.println("Is map empty?: " + testMap.isEmpty() + "\nAdding values...");
		testMap.put(1, "Hello");
		testMap.put(2, "World");
		testMap.put(3, "Testing");
		testMap.put(4, "Tree");
		System.out.println("Is map empty?: " + testMap.isEmpty() + "\nDisplaying values...");
		System.out.println("1 : " + testMap.get(1));
		System.out.println("2 : " + testMap.get(2));
		System.out.println("3 : " + testMap.get(3));
		System.out.println("4 : " + testMap.get(4));
		System.out.println("Emptying map...");
		testMap.makeEmpty();
		System.out.println("Is map empty?: " + testMap.isEmpty());
	}
}
