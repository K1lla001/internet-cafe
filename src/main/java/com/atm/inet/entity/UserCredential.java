package com.atm.inet.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "m_user_credential")
public class UserCredential {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @NotNull(message = "Email Can Not Be Empty")
    @Column(name = "email", unique = true)
    private String email;

    @NotNull(message = "Password Can Not Be Empty")
    private String password;
    private Boolean isActive;


    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
