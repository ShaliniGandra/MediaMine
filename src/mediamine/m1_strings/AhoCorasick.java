package mediamine.m1_strings;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Aho-Corasick multi-pattern matcher.
 * MediaMine use: scan a log line once and detect many delivery-status patterns
 * (DELIVERED, DELAYED, RETURNED, DAMAGED ...) at the same time.
 * Time: O(n + total pattern length + number of matches).
 */
public class AhoCorasick {

    /** One occurrence of a pattern in the text. */
    public static class Match {
        public final int start;
        public final String pattern;
        public Match(int start, String pattern) { this.start = start; this.pattern = pattern; }
        @Override public String toString() { return pattern + "@" + start; }
    }

    private static class Node {
        Map<Character, Node> next = new HashMap<>();
        Node fail;
        List<String> output = new ArrayList<>();
    }

    private final Node root = new Node();

    public AhoCorasick(List<String> patterns) {
        for (String p : patterns) insert(p);
        buildFailureLinks();
    }

    private void insert(String p) {
        if (p.isEmpty()) return;
        Node cur = root;
        for (char c : p.toCharArray()) cur = cur.next.computeIfAbsent(c, k -> new Node());
        cur.output.add(p);
    }

    private void buildFailureLinks() {
        Queue<Node> q = new ArrayDeque<>();
        root.fail = root;
        for (Node child : root.next.values()) {
            child.fail = root;
            q.add(child);
        }
        while (!q.isEmpty()) {
            Node u = q.poll();
            for (Map.Entry<Character, Node> e : u.next.entrySet()) {
                char c = e.getKey();
                Node v = e.getValue();
                Node f = u.fail;
                while (f != root && !f.next.containsKey(c)) f = f.fail;
                v.fail = (f.next.containsKey(c) && f.next.get(c) != v) ? f.next.get(c) : root;
                v.output.addAll(v.fail.output);     // inherit matches from suffix links
                q.add(v);
            }
        }
    }

    /** Finds all occurrences of all patterns in the text. */
    public List<Match> search(String text) {
        List<Match> matches = new ArrayList<>();
        Node cur = root;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            while (cur != root && !cur.next.containsKey(c)) cur = cur.fail;
            if (cur.next.containsKey(c)) cur = cur.next.get(c);
            for (String p : cur.output) matches.add(new Match(i - p.length() + 1, p));
        }
        return matches;
    }
}
