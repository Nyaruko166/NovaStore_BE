package com.sd64.novastore.model;

import com.sd64.novastore.enums.ImageType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "Image")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "CreateDate")
    private Date createDate;

    @Column(name = "UpdateDate")
    private Date updateDate;

    @Column(name = "Status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ProductDetailId", referencedColumnName = "Id")
    private ProductDetail productDetail;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "ImageType")
//    private ImageType imageType = ImageType.FILE;
}