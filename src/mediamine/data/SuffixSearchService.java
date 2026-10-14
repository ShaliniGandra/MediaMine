package mediamine.data;

import java.util.ArrayList;
import java.util.List;

import mediamine.m2_suffix.SuffixArray;
import mediamine.model.MediaItem;

public class SuffixSearchService {

    private final List<MediaItem> mediaItems;

    public SuffixSearchService(List<MediaItem> mediaItems) {
        this.mediaItems = mediaItems;
    }

    // ==========================================
    // BUILD SUFFIX ARRAY FOR ONE MEDIA ITEM
    // ==========================================

    public SuffixArray buildIndex(MediaItem item) {

        if (item == null || item.getTitle() == null) {
            return null;
        }

        String text = item.getTitle().toLowerCase();

        if (text.isEmpty()) {
            return null;
        }

        return new SuffixArray(text);
    }

    // ==========================================
    // SEARCH USING SUFFIX ARRAY
    // ==========================================

    public List<MediaItem> searchUsingSuffixArray(String query) {

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

            if (title.isEmpty()) {
                continue;
            }

            SuffixArray suffixArray =
                    new SuffixArray(title);

            if (suffixArray.contains(pattern)) {
                results.add(item);
            }
        }

        return results;
    }

    // ==========================================
    // SEARCH AND RETURN MATCH POSITIONS
    // ==========================================

    public List<Integer> findPositions(
            MediaItem item,
            String query) {

        List<Integer> positions =
                new ArrayList<>();

        if (item == null ||
                item.getTitle() == null ||
                query == null ||
                query.trim().isEmpty()) {

            return positions;
        }

        String title =
                item.getTitle().toLowerCase();

        String pattern =
                query.toLowerCase().trim();

        if (title.isEmpty()) {
            return positions;
        }

        SuffixArray suffixArray =
                new SuffixArray(title);

        return suffixArray.search(pattern);
    }

    // ==========================================
    // TOTAL ITEMS
    // ==========================================

    public int getTotalItems() {
        return mediaItems.size();
    }
}