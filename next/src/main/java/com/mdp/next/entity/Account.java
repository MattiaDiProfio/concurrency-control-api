package com.mdp.next.entity;

import java.util.List;
import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ID;

    @JsonIgnore
    @OneToOne(mappedBy = "account")
    private User user;

    @NonNull
    @Column(name = "balance", nullable = false)
    private Double balance;

    @JsonIgnore
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Transaction> sent;

    @JsonIgnore
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Transaction> received;

    // field for debug purpose only
    @Column(name = "user_id", unique = true)
    private Long userID;

}
