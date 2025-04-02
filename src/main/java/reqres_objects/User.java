package reqres_objects;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class User {

    String name;
    String job;
    String id;
    String createdAt;
    String email;
    String password;
    String token;
}