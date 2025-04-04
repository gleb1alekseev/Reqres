package reqres_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ResourceForSingleUser {
    @Expose
    int id;

    @Expose
    String name;

    @Expose
    int year;

    @Expose
    String color;

    @SerializedName("pantone_value")
    String pantoneValue;
}