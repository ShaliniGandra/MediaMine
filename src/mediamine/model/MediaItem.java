package mediamine.model;

/**
 * Represents a media/content item in the MediaMine platform.
 */
public class MediaItem {

    private final String mediaId;
    private final String title;
    private final String transcript;
    private final String category;
    private final String source;

    public MediaItem(
            String mediaId,
            String title,
            String transcript,
            String category,
            String source) {

        this.mediaId = mediaId;
        this.title = title;
        this.transcript = transcript;
        this.category = category;
        this.source = source;
    }

    public String getMediaId() {
        return mediaId;
    }

    public String getTitle() {
        return title;
    }

    public String getTranscript() {
        return transcript;
    }

    public String getCategory() {
        return category;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "MediaItem[" +
                mediaId + ", " +
                title + ", " +
                category + ", " +
                source + "]";
    }
}