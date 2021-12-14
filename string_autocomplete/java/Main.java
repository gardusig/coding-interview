import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

class Trie {
    final Map<Character, Trie> edges;
    final ArrayList<String> wordsEndingHere;

    public Trie() {
        this.edges = new HashMap<>();
        this.wordsEndingHere = new ArrayList<>();
    }

    public void addWord(final int index, final String word) {
        if (index == word.length()) {
            this.wordsEndingHere.add(word);
            return;
        }
        final char c = word.charAt(index);
        if (!this.edges.containsKey(c)) {
            this.edges.put(c, new Trie());
        }
        this.edges.get(c).addWord(index + 1, word);
    }

    public ArrayList<String> getWordsThatMatchPrefix(final int index, final String prefix) {
        if (index == prefix.length()) {
            ArrayList<String> words = new ArrayList<>();
            this.dfs(words);
            return words;
        }
        final char c = prefix.charAt(index);
        if (!this.edges.containsKey(c)) {
            return new ArrayList<>();
        }
        return this.edges.get(c).getWordsThatMatchPrefix(index + 1, prefix);
    }

    private void dfs(ArrayList<String> words) {
        for (final String word : this.wordsEndingHere) {
            words.add(word);
        }
        for (final Entry<Character, Trie> edge : edges.entrySet()) {
            edge.getValue().dfs(words);
        }
    }
}

public class Main {
    public static void main(final String[] args) {
        final Trie trie = new Trie();
        readInput(trie);
        for (final String word : trie.getWordsThatMatchPrefix(0, "")) {
            System.out.println(word);
        }
    }

    private static void readInput(final Trie trie) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            final String word = scanner.nextLine();
            trie.addWord(0, word);
        }
        scanner.close();
    }
}