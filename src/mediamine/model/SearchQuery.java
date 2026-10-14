package mediamine.model;
public class SearchQuery {
    private final String queryId, queryText;
    public SearchQuery(String queryId,String queryText){this.queryId=queryId;this.queryText=queryText;}
    public String getQueryId(){return queryId;} public String getQueryText(){return queryText;}
    @Override public String toString(){return "SearchQuery["+queryId+", "+queryText+"]";}
}
