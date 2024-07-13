package com.github.springdocumentservice.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.springdocumentservice.domain.Album;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDto {


    private Long albumId;


    @NotEmpty(message = "앨범 이름은 필수입니다.")
    private String albumName;

    @NotNull(message = "가격은 필수입니다.")
    private int price;

    @NotNull(message = "유저 닉네임은 필수입니다.")
    private String userNickName;

    @NotNull(message = "가수 명은 필수입니다.")
    private String singer;


    private String description;
    private String imageUrl;
    private Integer quantity;
    private List<MultipartFile> files = new ArrayList<>();
    private List<String> imagePaths = new ArrayList<>(); // 이미지 경로 추가


    public AlbumDto(Album album) {
        this.albumId = album.getAlbumId();
        this.albumName = album.getAlbumName();
        this.price = album.getPrice();
        this.singer = album.getSinger();
        this.description = album.getDescription();
        this.imageUrl = album.getImageUrl();
        this.quantity = album.getQuantity();
        this.imagePaths = album.getImagePaths();
        this.userNickName = album.getUser().getUserNickname();

    }

}