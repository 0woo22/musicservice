package com.github.springdocumentservice.Dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.springdocumentservice.domain.Purchase;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDto {

    private Long purchaseId;
    private String albumName;
    private String singer;
    private int quantity;
    private double price;
    private String userNickname;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime purchaseTime;

    public PurchaseDto(Purchase purchase) {
        this.purchaseId = purchase.getPurchaseId();
        this.albumName = purchase.getAlbumName();
        this.singer = purchase.getSinger();
        this.quantity = purchase.getQuantity();
        this.price = purchase.getPrice();
        this.userNickname = purchase.getUserNickname();
        this.purchaseTime = purchase.getPurchaseTime();
    }
}
