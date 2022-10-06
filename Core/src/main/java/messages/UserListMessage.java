package messages;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
@Getter
public class UserListMessage extends AbstractMessage {

    private Collection<String> names;

    public static UserListMessage of(Collection<String> names){
        UserListMessage m = new UserListMessage();
        m.names = names;
        return m;
    }
}
