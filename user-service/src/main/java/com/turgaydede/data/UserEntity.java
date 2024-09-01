package com.turgaydede.data;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
