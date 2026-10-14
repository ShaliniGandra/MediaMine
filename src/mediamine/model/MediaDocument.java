package mediamine.model;

/** Searchable media document such as an article, transcript, or report. */
public class MediaDocument {
    private final String documentId;
    private final String title;
    private final String content;
    public MediaDocument(String documentId, String title, String content){
        this.documentId=documentId; this.title=title; this.content=content;
    }
    public String getDocumentId(){ return documentId; }
    public String getTitle(){ return title; }
    public String getContent(){ return content; }
    @Override public String toString(){ return "MediaDocument["+documentId+", "+title+", "+content.length()+" chars]"; }
}
