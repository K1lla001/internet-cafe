package com.atm.inet.entity;

import com.atm.inet.entity.auditing.Auditable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "m_profile_picture")

public class ProfilePicture extends BaseFile {
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserCredential user;

}
