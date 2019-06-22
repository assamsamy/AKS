package Messages;

import com.sun.istack.internal.Nullable;

public class Message {
    public final static int MESSAGE_CHECK_PROFIL = 0;
    public final static int MESSAGE_SUCESS_WRITE_CARD = 1;
    public final static int MESSAGE_FAIL_WRITE_CARD = 2;
    public final static int MESSAGE_BLANK_CARD = 3;
    public final static int MESSAGE_PROFIL_TO_WRITE = 4;
    public final static int MESSAGE_ACCESS_ALLOWED = 5;

    public final static int MESSAGE_ACCESS_REJECTED = 6;
    public final static int MESSAGE_ERROR = 7;
    public final static int MESSAGE_REFRESH = 8;
    public final static int MESSAGE_HCE = 9;
    public final static int MESSAGE_WRITE_HCE = 10;




/*
* {
     "type": 4,
     "content": "161631104710-X0ICKOE-IPO?PICIOJ",
     "from": "ID_ADMIN_CLIENT",
     "to": "scanner1",
* }
*
* */
    private int type;
    private String content;
    private String from;
    private String to;

    @Nullable
    private String card_type;
    @Nullable
    private String uid;

    public Message(String content, int type, String from, String to){
        setContent(content);
        setType(type);
        setFrom(from);
        setTo(to);
    }

    public Message(int type, String content, String from, String to, String card_type, String uid) {
        this.type = type;
        this.content = content;
        this.from = from;
        this.to = to;
        this.card_type = card_type;
        this.uid = uid;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

