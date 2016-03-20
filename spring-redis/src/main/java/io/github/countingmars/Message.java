package io.github.countingmars;

/**
 * Created by Mars on 3/20/16.
 */
public class Message extends ToStringSupport {
    private String sender;
    private String receiver;
    private String body;

    public Message(String id) {
        this.sender = "sender-" + id;
        this.receiver = "receiver-" + id;
        this.body = "body-" + id;
    }

    public Message(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
