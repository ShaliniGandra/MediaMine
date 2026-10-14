package mediamine.model;
public class MediaEvent {
    private final String eventId, mediaId, type; private final long epochMillis;
    public MediaEvent(String eventId,String mediaId,String type,long epochMillis){this.eventId=eventId;this.mediaId=mediaId;this.type=type;this.epochMillis=epochMillis;}
    public String getEventId(){return eventId;} public String getMediaId(){return mediaId;} public String getType(){return type;} public long getEpochMillis(){return epochMillis;}
    @Override public String toString(){return "MediaEvent["+eventId+", "+mediaId+", "+type+"]";}
}
