package mediamine.m2_suffix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Suffix Tree built with Ukkonen's algorithm (O(n) construction).
 * MediaMine use: fast substring queries over shipment descriptions.
 * A unique terminator character '\u0001' is appended internally; input must not contain it.
 */
public class SuffixTree {
    private static final char TERMINATOR = '\u0001';

    private static class End { int value; End(int v) { value = v; } }

    private static class Node {
        Map<Character, Node> children = new HashMap<>();
        Node suffixLink;
        int start;
        End end;
        int suffixIndex = -1;
        Node(int start, End end) { this.start = start; this.end = end; }
        int edgeLength() { return end.value - start + 1; }
    }

    private final char[] text;
    private final Node root = new Node(-1, new End(-1));

    // Ukkonen state
    private Node activeNode = root;
    private int activeEdge = -1;
    private int activeLength = 0;
    private int remainder = 0;
    private final End leafEnd = new End(-1);

    public SuffixTree(String input) {
        this.text = (input + TERMINATOR).toCharArray();
        root.suffixLink = root;
        for (int i = 0; i < text.length; i++) extend(i);
        assignSuffixIndices(root, 0);
    }

    private void extend(int pos) {
        leafEnd.value = pos;
        remainder++;
        Node lastNew = null;

        while (remainder > 0) {
            if (activeLength == 0) activeEdge = pos;
            Node next = activeNode.children.get(text[activeEdge]);

            if (next == null) {
                activeNode.children.put(text[activeEdge], new Node(pos, leafEnd));
                if (lastNew != null) { lastNew.suffixLink = activeNode; lastNew = null; }
            } else {
                int len = next.edgeLength();
                if (activeLength >= len) {              // walk down
                    activeEdge += len;
                    activeLength -= len;
                    activeNode = next;
                    continue;
                }
                if (text[next.start + activeLength] == text[pos]) {   // rule 3: already present
                    if (lastNew != null && activeNode != root) { lastNew.suffixLink = activeNode; lastNew = null; }
                    activeLength++;
                    break;
                }
                // rule 2: split the edge
                Node split = new Node(next.start, new End(next.start + activeLength - 1));
                activeNode.children.put(text[activeEdge], split);
                split.children.put(text[pos], new Node(pos, leafEnd));
                next.start += activeLength;
                split.children.put(text[next.start], next);
                if (lastNew != null) lastNew.suffixLink = split;
                lastNew = split;
                split.suffixLink = root;
            }

            remainder--;
            if (activeNode == root && activeLength > 0) {
                activeLength--;
                activeEdge = pos - remainder + 1;
            } else if (activeNode != root) {
                activeNode = activeNode.suffixLink != null ? activeNode.suffixLink : root;
            }
        }
    }

    private void assignSuffixIndices(Node n, int depth) {
        if (n.children.isEmpty()) {
            n.suffixIndex = text.length - depth;
            return;
        }
        for (Node c : n.children.values()) {
            assignSuffixIndices(c, depth + c.edgeLength());
        }
    }

    /** Walks the pattern from the root; returns the node whose subtree holds all matches, or null. */
    private Node locate(String pattern) {
        Node cur = root;
        int i = 0;
        while (i < pattern.length()) {
            Node next = cur.children.get(pattern.charAt(i));
            if (next == null) return null;
            int len = next.edgeLength();
            for (int k = 0; k < len && i < pattern.length(); k++, i++) {
                if (text[next.start + k] != pattern.charAt(i)) return null;
            }
            cur = next;
        }
        return cur;
    }

    public boolean contains(String pattern) {
        if (pattern.isEmpty()) return true;
        return locate(pattern) != null;
    }

    /** Sorted start positions of all occurrences of pattern. */
    public List<Integer> findAll(String pattern) {
        List<Integer> res = new ArrayList<>();
        if (pattern.isEmpty()) return res;
        Node n = locate(pattern);
        if (n != null) collectLeaves(n, res);
        Collections.sort(res);
        return res;
    }

    private void collectLeaves(Node n, List<Integer> out) {
        if (n.children.isEmpty()) { out.add(n.suffixIndex); return; }
        for (Node c : n.children.values()) collectLeaves(c, out);
    }

    /** Longest substring occurring at least twice = deepest internal node. */
    public String longestRepeatedSubstring() {
        int[] best = new int[] {0, 0};                 // {depth, endPositionExclusive}
        deepest(root, 0, best);
        return new String(text, best[1] - best[0], best[0]);
    }

    private void deepest(Node n, int depth, int[] best) {
        if (n.children.isEmpty()) return;
        if (n != root && depth > best[0]) {
            best[0] = depth;
            best[1] = n.end.value + 1;                 // node label ends here, depth chars long
        }
        for (Node c : n.children.values()) deepest(c, depth + c.edgeLength(), best);
    }
}
