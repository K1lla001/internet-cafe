package com.atm.inet.entity.computer;

import com.atm.inet.entity.auditing.Auditable;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_computer")
public class Computer extends Auditable<String> {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    private String name;

    @Column(name = "pc_code", unique = true)
    private String code;

    private Boolean status;

    @OneToOne(targetEntity = ComputerSpec.class)
    @JoinColumn(name = "computer_spec_id")
    private ComputerSpec specification;

    @ManyToOne(targetEntity = Type.class)
    @JoinColumn(name = "type_id")
    private Type type;
}

