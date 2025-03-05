package com.example.projetoSpring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.Optional;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImagemPerfil {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private String type;
    @Column(columnDefinition="bytea")
    private byte[] imageData;
    private Timestamp date_create;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private Usuario user;

}
