package mediamine.model;
public class Caption {
    private final String mediaId, language, text;
    public Caption(String mediaId,String language,String text){this.mediaId=mediaId;this.language=language;this.text=text;}
    public String getMediaId(){return mediaId;} public String getLanguage(){return language;} public String getText(){return text;}
    @Override public String toString(){return "Caption["+mediaId+", "+language+", "+text+"]";}
}
