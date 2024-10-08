package com.mdp.next.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdp.next.exception.Role;
import jakarta.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @NonNull
    @NotBlank(message = "User's name cannot be blank")
    @Column(name = "full_name", nullable = false)
    private String name;

    @NonNull
    @Email(message = "User's email must follow a valid email format")
    @Column(name = "email_address", nullable = false, unique = true)
    private String email;

    @NonNull
    @NotBlank(message = "User's address cannot be blank")
    @Column(name = "address", nullable = false)
    private String address;

	@NotBlank(message =  "username cannot be blank")
	@NonNull
	@Column(nullable = false, unique = true)
	private String username;

	@NotBlank(message =  "password cannot be blank")
    @NonNull
	@Column(nullable = false)
	private String password;

    @NotBlank(message = "role cannot be blank")
    @NonNull
    @Role
    @Column(nullable = false)
    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Token> tokens = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "accountOwner", cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();

}
