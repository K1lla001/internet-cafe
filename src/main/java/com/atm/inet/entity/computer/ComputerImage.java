package com.atm.inet.entity.computer;

import com.atm.inet.entity.auditing.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "m_computer_image")
public class ComputerImage extends Auditable<String> {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    private String name;
    private String contentType;
    private String path;
    private Long size;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @JsonBackReference
    private Type type;

}
