package mediamine.data;

import java.util.ArrayList;
import java.util.List;

import mediamine.model.MediaItem;
import mediamine.m1_strings.AhoCorasick;
import mediamine.m1_strings.KMP;
import mediamine.m1_strings.RabinKarp;
import mediamine.m1_strings.ZFunction;

public class MediaSearchService {

    private final List<MediaItem> mediaItems;

    public MediaSearchService(List<MediaItem> mediaItems) {
        this.mediaItems = mediaItems;
    }

    // ==========================================
    // BASIC SEARCH
    // ==========================================

    public List<MediaItem> searchByTitle(String query) {

        List<MediaItem> results = new ArrayList<>();

        if (query == null || query.trim().isEmpty()) {
            return results;
        }

        String searchTerm = query.toLowerCase().trim();

        for (MediaItem item : mediaItems) {

            if (item.getTitle() != null &&
                    item.getTitle().toLowerCase().contains(searchTerm)) {

                results.add(item);
            }
        }

        return results;
    }

    // ==========================================
    // KMP SEARCH
    // ==========================================

    public List<MediaItem> searchUsingKMP(String query) {

        List<MediaItem> results = new ArrayList<>();

        if (query == null || query.trim().isEmpty()) {
            return results;
        }

        String pattern = query.toLowerCase().trim();

        for (MediaItem item : mediaItems) {

            if (item.getTitle() == null) {
                continue;
            }

            String title = item.getTitle().toLowerCase();

            List<Integer> matches =
                    KMP.search(title, pattern);

            if (!matches.isEmpty()) {
                results.add(item);
            }
        }

        return results;
    }

    // ==========================================
    // RABIN-KARP SEARCH
    // ==========================================

    public List<MediaItem> searchUsingRabinKarp(String query) {

        List<MediaItem> results = new ArrayList<>();

        if (query == null || query.trim().isEmpty()) {
            return results;
        }

        String pattern = query.toLowerCase().trim();

        for (MediaItem item : mediaItems) {

            if (item.getTitle() == null) {
                continue;
            }

            String title = item.getTitle().toLowerCase();

            List<Integer> matches =
                    RabinKarp.search(title, pattern);

            if (!matches.isEmpty()) {
                results.add(item);
            }
        }

        return results;
    }

    // ==========================================
    // Z-FUNCTION SEARCH
    // ==========================================

    public List<MediaItem> searchUsingZFunction(String query) {

        List<MediaItem> results = new ArrayList<>();

        if (query == null || query.trim().isEmpty()) {
            return results;
        }

        String pattern = query.toLowerCase().trim();

        for (MediaItem item : mediaItems) {

            if (item.getTitle() == null) {
                continue;
            }

            String title = item.getTitle().toLowerCase();

            List<Integer> matches =
                    ZFunction.search(title, pattern);

            if (!matches.isEmpty()) {
                results.add(item);
            }
        }

        return results;
    }

    // ==========================================
    // AHO-CORASICK MULTI-PATTERN SEARCH
    // ==========================================

    public List<MediaItem> searchUsingAhoCorasick(
            List<String> patterns) {

        List<MediaItem> results = new ArrayList<>();

        if (patterns == null || patterns.isEmpty()) {
            return results;
        }

        // Normalize the search patterns
        List<String> normalizedPatterns =
                new ArrayList<>();

        for (String pattern : patterns) {

            if (pattern != null &&
                    !pattern.trim().isEmpty()) {

                normalizedPatterns.add(
                        pattern.toLowerCase().trim()
                );
            }
        }

        if (normalizedPatterns.isEmpty()) {
            return results;
        }

        // Build Aho-Corasick automaton
        AhoCorasick ahoCorasick =
                new AhoCorasick(normalizedPatterns);

        // Search every media item
        for (MediaItem item : mediaItems) {

            if (item.getTitle() == null) {
                continue;
            }

            String title =
                    item.getTitle().toLowerCase();

            List<AhoCorasick.Match> matches =
                    ahoCorasick.search(title);

            if (!matches.isEmpty()) {
                results.add(item);
            }
        }

        return results;
    }

    // ==========================================
    // TOTAL ITEMS
    // ==========================================

    public int getTotalItems() {
        return mediaItems.size();
    }
}