package second_stage;

/**
 * Custom type used instead of Integer to make possible
 * add equal Integers to Set
 */
public class MyPair {
    private final String key;
    private final int value;

    public MyPair(String aKey, int aValue)
    {
        key   = aKey;
        value = aValue;
    }

    public String key()   { return key; }
    public int value() { return value; }
}
