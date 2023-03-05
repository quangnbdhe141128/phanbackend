package com.example.demo.model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "account", uniqueConstraints = {@UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Role role;

    @Column(name = "is_block")
    private Boolean isBLock;

    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "card_id")
    private String cardId;
    @Column(name = "gender")
    private String gender;
    @Column(name = "point")
    private Long point;

    public Account(String username, String email, String password, Role role, Boolean isBLock, String name, String phone, String address, String cardId, String gender, Long point) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isBLock = isBLock;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.cardId = cardId;
        this.gender = gender;
        this.point = point;
    }
}
