import edu.princeton.cs.introcs.In;

import java.net.URL;

public class CanvasDriver {

    public static void main(String[] args) {
        URL url = ClassLoader.getSystemResource("98-0.txt");

        if (url != null) {
            System.out.println("Reading from: " + url);
        } else {
            System.out.println("Couldn't find file: kap1.txt");
        }

        In input = new In(url);

        Tries tries = new Tries();

        int numberOfWordsInTries = 0;

        while (!input.isEmpty()) {
            String line = input.readLine().trim();
            String[] words = line.split("(\\. )|:|’|‘|“|”|,|;|!|\\?|( - )|--|(\' )| ");
            String lastOfLine = words[words.length - 1];

            if (lastOfLine.endsWith(".")) {
                words[words.length - 1] = lastOfLine.substring(0, lastOfLine.length() - 1);
            }

            for (String word : words) {
                String word2 = word.replaceAll("\"|\\(|\\)", "");

                if (word2.isEmpty()) {
                    continue;
                }

                System.out.println(word2);

                // Add the word to the trie
                tries.put(word2);
                numberOfWordsInTries++;

            }
        }
        //Perform analysis

        //Test that displays the value of a key
        System.out.println("What value does --> was <-- have? " + tries.get("was"));
        //Test to display number of words
        System.out.println("Number of words in tries " + numberOfWordsInTries);
        //Test that iterates through tries and finds all keys that starts with Do
        System.out.println("Keys with prefix Do: " + tries.iterateStrings("Do"));

    }
}