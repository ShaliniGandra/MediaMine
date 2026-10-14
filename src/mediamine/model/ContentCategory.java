package mediamine.model;
public class ContentCategory {
    private final String categoryId, name;
    public ContentCategory(String categoryId,String name){this.categoryId=categoryId;this.name=name;}
    public String getCategoryId(){return categoryId;} public String getName(){return name;}
    @Override public String toString(){return "ContentCategory["+categoryId+", "+name+"]";}
}
