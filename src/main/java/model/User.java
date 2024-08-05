package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;

@Getter @Setter

@Entity
public class User {
    @Id
    private String userId;

    @Column(nullable = true)
    private String password;

    private String name;

}
