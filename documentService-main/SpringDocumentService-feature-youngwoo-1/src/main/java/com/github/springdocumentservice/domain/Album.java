package com.github.springdocumentservice.domain;



import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@Entity
@Table(name = "album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long albumId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "album_name", nullable = false, length = 255)
    private String albumName;

    @Column(name = "singer", nullable = false, length = 255)
    private String singer;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String userNickName;


    @Column(name = "image_url", length = 255, nullable = true)
    private String imageUrl;


    @ElementCollection
    @CollectionTable(name = "AlbumImage", joinColumns = @JoinColumn(name = "album_id"))
    @Column(name = "image_path")
    private List<String> imagePaths = new ArrayList<>();

    public boolean canAddImage() {
        return imagePaths.size() < 10;
    }

    public void addImage(String imagePath) {
        if (canAddImage()) {
            imagePaths.add(imagePath);
        } else {
            throw new IllegalStateException("사진은 10개까지 등록 가능합니다.");
        }
    }

    public void clearImages() {
        this.imagePaths.clear();
    }
}
