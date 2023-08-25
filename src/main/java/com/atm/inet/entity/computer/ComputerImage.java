package com.atm.inet.entity.computer;

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
@Table(name = "m_computer_image")
public class ComputerImage extends Auditable<String> {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    private String id;
    private String name;
    private String contentType;
    private String path;
    private Long size;

    @ManyToOne
    @JoinColumn(name = "computer_id", referencedColumnName = "id")
    private Computer computer;
}
