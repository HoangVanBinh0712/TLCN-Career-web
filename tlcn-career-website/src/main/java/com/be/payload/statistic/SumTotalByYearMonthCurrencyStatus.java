package com.be.payload.statistic;

import com.be.utility.datatype.ECurrency;
import com.be.utility.datatype.EROrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SumTotalByYearMonthCurrencyStatus {

    Integer year;
    Integer month;
    String status;
    Double sum;
    String currency;

}
