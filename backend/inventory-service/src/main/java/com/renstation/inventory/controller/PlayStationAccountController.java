package com.renstation.inventory.controller;

import com.renstation.common.dto.StandardApiResponse;
import com.renstation.inventory.dto.PlayStationAccountRequest;
import com.renstation.inventory.entity.PlayStationAccount;
import com.renstation.inventory.service.PlayStationAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class PlayStationAccountController {

    private final PlayStationAccountService accountService;

    @PostMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<PlayStationAccount>> createAccount(
            Authentication auth, @Valid @RequestBody PlayStationAccountRequest request) {
        PlayStationAccount account = accountService.createAccount(UUID.fromString(auth.getName()), request);
        return ResponseEntity.ok(StandardApiResponse.<PlayStationAccount>builder()
                .success(true).data(account).build());
    }

    @GetMapping
    public ResponseEntity<StandardApiResponse<List<PlayStationAccount>>> getMyAccounts(Authentication auth) {
        List<PlayStationAccount> accounts = accountService.getMyAccounts(UUID.fromString(auth.getName()));
        return ResponseEntity.ok(StandardApiResponse.<List<PlayStationAccount>>builder()
                .success(true).data(accounts).build());
    }
}
