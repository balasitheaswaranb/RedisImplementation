package com.Implementation;

public class CustomHashMap<K, V> {

	private Node<K, V>[] buckets;
	private Integer capacity = 16;

	@SuppressWarnings("unchecked")
	public CustomHashMap() {
		buckets = new Node[capacity];
	}

	private int index(K key) {
		return Math.abs(hashCode(key)) % capacity;
	}

	public void set(K key, V value) {
		Integer hash = index(key);
		Node<K, V> entry = new Node<K, V>(key, value);
		Node<K, V> temp = buckets[hash];
		if (temp == null) {
			buckets[hash] = entry;
		} else {
			while (temp != null) {
				if (temp.key.equals(key)) {

					temp.value = value;
					break;
				}
				if (temp.next != null) {
					temp = temp.next;
				} else {
					temp.next = entry;
				}
			}
		}
	}

	public V get(K key) {
		Integer hash = index(key);
		Node<K, V> temp = buckets[hash];
		while (temp != null) {
			if (temp.key.equals(key)) {
				return temp.value;
			}
			temp = temp.next;
		}
		return null;
	}

	public void unset(K key) {
		Integer hash = index(key);
		Node<K, V> temp = buckets[hash];
		Node<K, V> prev = null;
		while (temp != null) {
			if (temp.key.equals(key)) {
				if (prev != null) {
					prev.next = temp.next;
				} else {
					prev = temp.next;
					buckets[hash] = prev;
				}
				return;
			}
			prev = temp;
			temp = temp.next;
		}
	}

	public int count(V value) {
		int count = 0;
		for (int i = 0; i < capacity; i++) {
			Node<K, V> temp = buckets[i];
			while (temp != null) {
				if (temp.value.equals(value)) {
					count++;
				}
				temp = temp.next;
			}
		}
		return count;
	}

	public boolean update(K key, V value) {
		boolean update = false;
		for (int i = 0; i < capacity; i++) {
			Node<K, V> temp = buckets[i];
			while (temp != null) {
				if (temp.key.equals(key)) {
					temp.value = value;
					update = true;
					break;
				}
				temp = temp.next;
			}
		}
		return update;
	}

	public CustomHashMap<K, V> commit(CustomHashMap<K, V> newMap, CustomHashMap<K, V> currentMap) {
		for (int i = 0; i < capacity; i++) {
			Node<K, V> temp = buckets[i];
			while (temp != null) {
				currentMap.set(temp.key, temp.value);
				temp = temp.next;
			}
		}
		return currentMap;
	}

	public CustomHashMap<K, V> backup(CustomHashMap<K, V> newMap, CustomHashMap<K, V> currentMap) {
		for (int i = 0; i < capacity; i++) {
			Node<K, V> temp = buckets[i];
			while (temp != null) {
				newMap.set(temp.key, temp.value);
				temp = temp.next;
			}
		}
		return newMap;
	}

	public int hashCode(K key) {
		int hash = 0, i = 1;
		String key1 = String.valueOf(key);

		byte[] bt = key1.getBytes();
		for (byte b : bt) {
			if ((bt.length - 1) == 0) {
				hash += b;
			} else {
				hash += (int) (b * (Math.pow(31, (bt.length - i++))));
			}
		}
		return hash;
	}

	public void display() {

		for (Integer i = 0; i < capacity; i++) {
			Node<K, V> temp = buckets[i];
			while (temp != null) {
				System.out.println(temp.key);
				temp = temp.next;
			}
		}
	}
	
	public int valueLength(K key) {
		Integer hash = index(key);
		Node<K, V> temp = buckets[hash];
		while (temp != null) {
			if (temp.key.equals(key)) {
				String value = (String)temp.value;
				int strln = value.length();
				return strln;
			}
			temp = temp.next;
		}
		return 0;
	}
}
