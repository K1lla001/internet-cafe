package com.atm.inet.entity;

import com.atm.inet.entity.auditing.Auditable;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "m_profile_picture")

public class ProfilePicture extends Auditable<String> {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    private String name;
    private String contentType;
    private String path;
    private Long size;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserCredential user;

}
