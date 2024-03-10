package afeka.ac.il.timersservice.boundaries;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class MessageBoundary {
    private String messageId;
    private Date publishedTimestamp;
    private  String messageType;
    private String summary;
    private ExternalReferenceBoundary externalReferences;
    private Map<String, Object> messageDetails;

    public MessageBoundary () {}

    public String getMessageId() {
        return messageId;
    }

    public MessageBoundary setMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public Date getPublishedTimestamp() {
        return publishedTimestamp;
    }

    public MessageBoundary setPublishedTimestamp(Date publishedTimestamp) {
        this.publishedTimestamp = publishedTimestamp;
        return this;
    }

    public String getMessageType() {
        return messageType;
    }

    public MessageBoundary setMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public MessageBoundary setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public ExternalReferenceBoundary getExternalReferences() {
        return externalReferences;
    }

    public MessageBoundary setExternalReferences(ExternalReferenceBoundary externalReferences) {
        this.externalReferences = externalReferences;
        return this;
    }

    public Map<String, Object> getMessageDetails() {
        return messageDetails;
    }

    public MessageBoundary setMessageDetails(Map<String, Object> messageDetails) {
        this.messageDetails = messageDetails;
        return this;
    }

}
