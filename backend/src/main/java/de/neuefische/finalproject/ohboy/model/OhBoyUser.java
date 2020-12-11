package de.neuefische.finalproject.ohboy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
@Builder
public class OhBoyUser {

    @Id
    private String id;
    private String name;
    private boolean facebookUser;
    private String facebookToken;
    private boolean facebookLoggedOut;

    public OhBoyUser(String id, String name, boolean facebookUser) {
        this.id = id;
        this.name = name;
        this.facebookUser = facebookUser;
    }
}
