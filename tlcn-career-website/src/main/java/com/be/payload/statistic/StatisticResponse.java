package com.be.payload.statistic;

import java.util.List;

import lombok.Data;

@Data
public class StatisticResponse {
    List<CountPaidPostByYear> countPaidPostByYears;
    List<SumTotalByYearMonthCurrencyStatus> sumTotalByYearMonthCurrencyStatus;
    List<SumTotalByService> sumTotalByServices;
    List<CountTotalByService> countTotalByServices;
}
