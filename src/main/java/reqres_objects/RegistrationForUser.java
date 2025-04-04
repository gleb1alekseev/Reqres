package reqres_objects;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class RegistrationForUser {

    @Expose
    int id;

    @Expose
    String token;
}