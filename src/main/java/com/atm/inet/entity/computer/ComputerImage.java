package com.atm.inet.entity.computer;

import com.atm.inet.entity.BaseFile;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_computer_image")
@SuperBuilder
@IdClass(String.class)
public class ComputerImage extends BaseFile {


    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private Type type;
}
