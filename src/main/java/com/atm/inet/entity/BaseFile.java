package com.atm.inet.entity;


import com.atm.inet.entity.auditing.Auditable;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "m_base_file")
public class BaseFile extends Auditable<String> {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    private String name;
    private String contentType;
    private String path;
    private Long size;


}
