package library_management_system;

import java.util.BitSet;

public class BloomFilter {

    private BitSet bitSet;
    private int size;

    public BloomFilter(int size) {
        this.size = size;
        this.bitSet = new BitSet(size);
    }

    private int hash1(String item) {
        return Math.abs(item.hashCode()) % size;
    }

    private int hash2(String item) {
        int hash = 7;

        for (int i = 0; i < item.length(); i++) {
            hash = hash * 31 + item.charAt(i);
        }

        return Math.abs(hash) % size;
    }

    public void add(String item) {
        bitSet.set(hash1(item));
        bitSet.set(hash2(item));
    }

    public boolean mightContain(String item) {
        return bitSet.get(hash1(item)) && bitSet.get(hash2(item));
    }
}
