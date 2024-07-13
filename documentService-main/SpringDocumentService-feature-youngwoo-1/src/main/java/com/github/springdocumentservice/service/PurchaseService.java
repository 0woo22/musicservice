package com.github.springdocumentservice.service;




import com.github.springdocumentservice.Dto.PurchaseDto;
import com.github.springdocumentservice.domain.Album;
import com.github.springdocumentservice.domain.Purchase;
import com.github.springdocumentservice.domain.User;
import com.github.springdocumentservice.repository.AlbumRepository;
import com.github.springdocumentservice.repository.PurchaseRepository;
import com.github.springdocumentservice.repository.User.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Transactional
    public PurchaseDto purchaseAlbum(String email, String albumName, int quantity) {
        User user = userRepository.findByEmail2(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Album album = albumRepository.findByAlbumName(albumName)
                .orElseThrow(() -> new RuntimeException("해당 이름의 앨범이 판매중이지 않습니다 . "));

        if (album.getQuantity() < quantity) {
            throw new RuntimeException("재고 수가 부족합니다.");
        }


        album.setQuantity(album.getQuantity() - quantity);
        albumRepository.save(album);


        Purchase purchase = Purchase.builder()
                .albumName(album.getAlbumName())
                .singer(album.getSinger())
                .quantity(quantity)
                .price(album.getPrice() * quantity)
                .userNickname(user.getUserNickname())
                .purchaseTime(LocalDateTime.now())
                .build();

        Purchase savedPurchase = purchaseRepository.save(purchase);
        return new PurchaseDto(savedPurchase);
    }


    @Transactional
    public List<PurchaseDto> getPurchaseHistory(String email) {
        User user = userRepository.findByEmail2(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Purchase> purchases = purchaseRepository.findByUserNickname(user.getUserNickname());
        return purchases.stream()
                .map(PurchaseDto::new)
                .collect(Collectors.toList());
    }
}