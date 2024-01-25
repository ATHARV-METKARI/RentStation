package com.renstation.inventory.service;

import com.renstation.common.exception.ResourceNotFoundException;
import com.renstation.inventory.dto.PlayStationAccountRequest;
import com.renstation.inventory.entity.PlayStationAccount;
import com.renstation.inventory.repository.PlayStationAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayStationAccountService {

    private final PlayStationAccountRepository accountRepository;

    @Transactional
    public PlayStationAccount createAccount(UUID ownerId, PlayStationAccountRequest request) {
        PlayStationAccount account = PlayStationAccount.builder()
                .ownerId(ownerId)
                .psnOnlineId(request.getPsnOnlineId())
                .region(request.getRegion())
                .country(request.getCountry())
                .verificationStatus("PENDING")
                .submittedAt(LocalDateTime.now())
                .status("ACTIVE")
                .build();
        return accountRepository.save(account);
    }

    public List<PlayStationAccount> getMyAccounts(UUID ownerId) {
        return accountRepository.findByOwnerIdAndDeletedFalse(ownerId);
    }
}
