package com.github.springdocumentservice.service;


import com.github.springdocumentservice.Dto.AlbumDto;
import com.github.springdocumentservice.domain.Album;
import com.github.springdocumentservice.domain.User;
import com.github.springdocumentservice.repository.AlbumRepository;
import com.github.springdocumentservice.repository.User.UserRepository;
import com.github.springdocumentservice.util.FileStorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    @Value("${UPLOAD_DIR}")
    private String uploadDir;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageUtil fileStorageUtil;

    @Autowired
    private AlbumRepository albumRepository;


    public AlbumDto registerProduct(String email, AlbumDto albumDto) throws IOException {
        validateAlbumInfo(albumDto);
        User user = userRepository.findByEmail2(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<MultipartFile> files = albumDto.getFiles() != null ? albumDto.getFiles() : new ArrayList<>();

        List<String> imagePaths = files.stream()
                .map(file -> {
                    try {
                        return fileStorageUtil.storeFile(file);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to store file: " + file.getOriginalFilename(), e);
                    }
                })
                .collect(Collectors.toList());


        // 상품 정보 설정
        Album album = new Album();
        album.setAlbumName(albumDto.getAlbumName());
        album.setPrice(albumDto.getPrice());
        album.setDescription(albumDto.getDescription());
        album.setPrice(albumDto.getPrice());
        album.setQuantity(albumDto.getQuantity());
        album.setSinger(albumDto.getSinger());
        album.setUser(user);
        album.setUserNickName(user.getUserNickname());





        // 첫 번째 이미지 경로를 imageUrl에 설정
        if (!imagePaths.isEmpty()) {
            album.setImageUrl(imagePaths.get(0)); // 첫 번째 이미지 경로를 imageUrl에 설정
        }

        imagePaths.forEach(album::addImage);  // 각 이미지 경로를 Product에 추가
        // 상품 등록
        Album registeredAlbum = albumRepository.save(album);
        // 등록된 상품 정보를 DTO로 변환하여 반환
        return new AlbumDto(registeredAlbum);
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    private void  validateAlbumInfo(AlbumDto albumDto) {
        // 필요한 모든 상품 정보가 입력되었는지 확인하는 로직 추가
        // 예: productName, price, startDate, endDate, description 등
        // 필수 정보가 빠진 경우 예외를 던지도록 구현
        if (albumDto.getAlbumName() == null || albumDto.getAlbumName().isEmpty()) {
            throw new IllegalArgumentException("앨범 명을 기입해주세요");
        }
        if (albumDto.getPrice() <= 0) {
            throw new IllegalArgumentException("가격은 0원 이상이어야 합니다.");
        }

        if (albumDto.getDescription() == null || albumDto.getDescription().isEmpty()) {
            throw new IllegalArgumentException("앨범 설명을 기입해주세요");
        }
    }
}