/**
 * Created by Anders on 2017-09-20.
 */
public class Driver {
    public static void main(String[] args) {
        Tries tries = new Tries();
        tries.put("Dog");
        tries.put("Cat");
        tries.put("Penguin");
        tries.put("Giraffe");
        tries.put("Dogs");
        tries.put("Doggies");

        System.out.println("Hmm, what might be the value of our dog? Oh, its " + tries.get("Dog"));
        System.out.println("And the value of our cat? " + tries.get("Cat"));
        System.out.println("And the value of our penguin? " + tries.get("Penguin"));
        System.out.println("And the value of our giraffe? " + tries.get("Giraffe"));
        System.out.println("And the value of our dogs? " + tries.get("Dogs"));

        tries.put("Dog");

        System.out.println("Did the dog value change? But yes it did, it's " + tries.get("Dog") + " now" +
                "\nAll good then...");
        System.out.println("Did the dogs value change? No it didn't, good dogs " + tries.get("Dogs"));

        System.out.println("Does something contain Cat? " + tries.contains("Cat"));
        System.out.println("How many things starts with Do? " + tries.distinct("Do"));
        System.out.println("What is the sum of all associated values of the sub-tree starting at prefix? " + tries.count("Do"));
        System.out.println("Keys with prefix " + tries.iterateStrings("Do"));


    }
}
