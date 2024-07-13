package com.github.springdocumentservice.controller;



import com.github.springdocumentservice.Dto.PurchaseDto;
import com.github.springdocumentservice.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/buy")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "앨범 구매", description = "인증된 사용자가 앨범을 구매합니다.")
    public ResponseEntity<PurchaseDto> purchaseAlbum(
            @RequestParam String albumName,
            @RequestParam int quantity,
            Principal principal) {
        try {
            String email = principal.getName();
            PurchaseDto purchaseDto = purchaseService.purchaseAlbum(email, albumName, quantity);
            return ResponseEntity.ok(purchaseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/history")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "구매 내역 조회", description = "인증된 사용자의 구매 내역을 조회합니다.")
    public ResponseEntity<List<PurchaseDto>> getPurchaseHistory(Principal principal) {
        try {
            String email = principal.getName();
            List<PurchaseDto> purchaseHistory = purchaseService.getPurchaseHistory(email);
            return ResponseEntity.ok(purchaseHistory);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

