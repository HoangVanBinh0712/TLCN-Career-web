package com.be.payload.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountPaidPostByYear {
    int year;

    int month;

    long count;
}
