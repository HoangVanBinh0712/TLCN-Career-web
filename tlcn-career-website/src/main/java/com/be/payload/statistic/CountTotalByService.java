package com.be.payload.statistic;


import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountTotalByService {
    String name;
    Long count;
    String currency;
}
