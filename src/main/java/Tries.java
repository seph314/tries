/**
 * Created by Anders on 2017-09-20.
 */

import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class Tries implements Iterable<Map.Entry<String, Integer>> {

    private static final int ASCII = 256; // 256-way trie
    private Node root = new Node();
    private int val = 0;


    /**
     * Nested node class used to create objects representing a node
     */
    public static class Node{
        private int value = 0;
        private Node[] next = new Node[ASCII];  // one for each ASCII sign
                                                // then you can loop through the Node array for each ASCII sign
    }

    /**
     * inserts key
     * If k was not associated with any value v before, associate it with 1, otherwise with v + 1 where v was the old value.
     *
     * @param key is a key, such as "cat", "something else", and so on...
     */
    public void put(String key){
        /*  Increases the value of val if val > 0
            To make shore each key has it's own number in increasing order
            If a key already has a value it gains a new value which is +1 from the highest number so far
         */
// OM man vill numrera dem i sigande ordning
//        if (val > 0)
//            val += 1;
//        else
//            val = 1;
        val = 1;
        if (get(key) != 0)
            val = get(key) +1;

        root = put(root, key, val, 0);
    }

    /**
     * Private helper method that does the magic when the public put method is called
     * Goes through all characters in a String key and builds a new node
     *
     * @param node is a Node object representing the node we will put in
     * @param key is a key, such as "cat", "something else", and so on...
     * @param val is the value which corresponds to the key encoded in the path from that node to the root
     * @param i is a number representing a nodes place in the trie tree
     * @return a node
     */
    private Node put(Node node, String key, int val, int i){
        if (node == null)
            node = new Node();

        if (i == key.length()){
            node.value = val;
            return node;
        }

        char character = key.charAt(i);
        node.next[character] = put(node.next[character], key, val, i+1);
        return node;
    }

    /**
     * get's key
     * uses helper method to find it
     * @param key is a key, such as "cat", "something else", and so on...
     * @return the value of the node
     */
    public int get(String key){
        Node node = get(root, key, 0);
        if (node == null)
            return 0;
        return node.value;
    }

    /**
     * private helper method that does the magic when the public get method is called
     *
     * @param node is a Node object representing the node we will put in
     * @param key is a key, such as "cat", "something else", and so on...
     * @param i i is a number representing a nodes place in the trie tree
     * @return a node
     */
    private Node get(Node node, String key, int i){
        if (node == null)
            return null;
        if (i == key.length())
            return node;
        char character = key.charAt(i);
        return get(node.next[character], key, i+1);
    }


    /**
     *
     * @param prefix
     * @return
     */
    public int distinct(String prefix) {
        Node node = root;
        // loops through the length of the prefix and builds a new node
        for (int i = 0; i < prefix.length(); i++) {
            node = node.next[(prefix.charAt(i))];
            if (node == null)
                return 0;
        }
        // if the node dont have a value already, count amount of distinct keys with this prefix
        if (node.value == 0)
            return distinct(node);
        // else do the same thing, but add 1 for the key that's already there
        else
            return 1 + distinct(node);
    }

    /**
     * Return the number of distinct keys that have associated values on the sub-tree starting at k.
     * For example Distinct(”Do”) returns 2 for keys ”Dog” and ”Dogs”.
     * @param node is a Node object representing a node in our trie chain
     * @return
     */
    private int distinct(Node node) {
        int sum = 0;
        /*  loops through the length of node.next, which is 256
            if node.next isn't null and the value of that node isn't 0 the sum += 1
            then do it again for the place in the Node array
            */
        for (int i = 0; i < node.next.length; i++) {
            if (node.next[i] != null) {
                if (node.next[i].value != 0)
                    sum += 1;
                sum += distinct(node.next[i]);
            }
        }
        return sum;
    }



    /**
     *
     * @param prefix is the prefix we use as a start point
     * @return the sum of all associated values of the sub-tree starting at prefix.
     */
    public int count(String prefix) {
        Node node = root;
        // loops through the prefix and build a node of all the characters
        for (int i = 0; i < prefix.length(); i++) {
            node = node.next[(prefix.charAt(i))];
            if (node == null)
                return 0;
        }
        // returns the value of this node plus the sum of values for all underlying nodes
        return node.value + count(node);
    }
    // counts the sum och values for all underlying nodes
    private int count(Node node) {
        int sum = 0;
        for (int i = 0; i < node.next.length; i++) {
            if (node.next[i] != null)
                sum += node.next[i].value + count(node.next[i]);
        }
        return sum;
    }



    /**
     * Returns all of the keys in the set that start with the given prefix
     * @param prefix the prefix
     */
    public Iterable<String> iterateStrings(String prefix) {
        Stack<String> resultStack = new Stack<>();
        Node node = get(root, prefix, 0);
        collect(node, new StringBuilder(prefix), resultStack);
        return resultStack;
    }

    private void collect(Node node, StringBuilder prefix, Stack<String> results) {
        //kollar så att noden inte är null
        if (node == null) return;
        //om noden har ett värde så ska den läggas till
        if (node.value != 0) results.add(prefix.toString());
        //goes through every way (256 characters)
        for (char character = 0; character < ASCII; character++) {
            prefix.append(character);
            collect(node.next[character], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }


    /**
     * returns true if the exact key is there, otherwise false
     * @param key is a key, such as "cat", "something else", and so on...
     * @return
     */
    public boolean contains(String key){
        return get(key) != 0;
    }

    /**
     * Implementing method Iterator, Iterable
     * OBS This functions is not implemented yet
     * @return
     */
    @Override
    public Iterator<Map.Entry<String, Integer>> iterator() {
        return new MyIterator();
    }

    class MyIterator implements Iterator<Map.Entry<String, Integer>>{


        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Map.Entry<String, Integer> next() {
            return null;
        }

        @Override
        public void remove() {

        }
    }
}
