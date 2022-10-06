package responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import messages.AbstractMessage;

@AllArgsConstructor
@Getter
public class NickResponse extends AbstractMessage {
    private final String nick;
}
