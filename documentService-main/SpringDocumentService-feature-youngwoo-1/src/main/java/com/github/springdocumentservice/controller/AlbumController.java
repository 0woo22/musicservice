package com.github.springdocumentservice.controller;


import com.github.springdocumentservice.Dto.AlbumDto;
import com.github.springdocumentservice.domain.Album;
import com.github.springdocumentservice.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AlbumController {



    @Autowired
    private AlbumService albumService;

    @PostMapping("/album/register")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "앨범 등록", description = "새로운 앨범을 등록합니다.")
    public ResponseEntity<?> registerAlbum(
            @RequestParam("albumName") String albumName,
            @RequestParam("price") int price,
            @RequestParam("description") String description,
            @RequestParam("quantity") int quantity,
            @RequestParam("singer") String singer,
            @RequestParam("files") List<MultipartFile> files,
            Principal principal) {
        try {
            String userEmail = principal.getName();

            AlbumDto albumDto = new AlbumDto();
            albumDto.setAlbumName(albumName);
            albumDto.setPrice(price);
            albumDto.setDescription(description);
            albumDto.setQuantity(quantity);
            albumDto.setSinger(singer);
            albumDto.setFiles(files);

            AlbumDto registeredProduct = albumService.registerProduct(userEmail, albumDto);

            return ResponseEntity.ok(registeredProduct);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("파일 저장 중 오류가 발생했습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("상품 등록 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/all")
    @Operation(summary = "모든 앨범 조회", description = "등록된 모든 앨범을 조회합니다.")
    public ResponseEntity<List<AlbumDto>> getAllAlbums() {
        List<Album> albums = albumService.getAllAlbums();
        List<AlbumDto> albumDtos = albums.stream().map(AlbumDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(albumDtos);
    }

}