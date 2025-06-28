package com.example.projetoSpring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImagemAnuncio {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String url;
    private String publicId;
    private String pasta;

    @ManyToOne
    @JoinColumn(name = "anuncio_id")
    private Anuncio anuncio;
}
