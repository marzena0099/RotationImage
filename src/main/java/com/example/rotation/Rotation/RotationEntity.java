package com.example.rotation.Rotation;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "images")
public class RotationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "original_image", nullable = false)
    private byte[] originalImage;

    @Lob
    @Column(name = "modified_image")
    private byte[] modifiedImage;

    @Column(name = "format", nullable = false)
    private String format;

//    @Column(name = "upload_date", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date uploadDate;


}
