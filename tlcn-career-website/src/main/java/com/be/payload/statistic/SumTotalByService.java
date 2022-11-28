package com.be.payload.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SumTotalByService {
    String name;
    Double sum;
    String currency;
}
