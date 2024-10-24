package com.nnk.springboot.domain;


import com.nnk.springboot.record.UserRecord;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Size(max = 125)
    private String username;
    @Size(max = 125)
    private String password;
    @Size(max = 125)
    private String fullname;
    @Size(max = 125)
    private String role;

    public User(UserRecord.Api.UserRequest userRequest) {
        this.username = userRequest.username();
        this.password = userRequest.password();
        this.fullname = userRequest.fullname();
    }
}
