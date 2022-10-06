package messages;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
@Getter
public class TextMessage extends AbstractMessage{

    private String from;
    private String to;
    private String message;
    private String sendAt;

    public static TextMessage of(String from, String message){
        TextMessage m = new TextMessage();
        m.from = from;
        m.message = message;
        m.sendAt = dateToString();
        return m;
    }

    public static TextMessage of(String from, String to, String message){
        TextMessage m = new TextMessage();
        m.from = from;
        m.message = message;
        m.to = to;
        m.sendAt = dateToString();
        return m;
    }

    private static String dateToString() {
        long date = new Date().getTime();
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return f.format(date);
    }
}
