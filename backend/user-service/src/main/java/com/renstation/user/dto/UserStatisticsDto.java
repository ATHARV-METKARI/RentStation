package com.renstation.user.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class UserStatisticsDto {
    private BigDecimal sellerRating;
    private Integer completedRentals;
    private Integer cancelledRentals;
    private BigDecimal totalEarnings;
    private Integer expertDisputesResolved;
}
