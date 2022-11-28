package com.be.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.javatuples.Triplet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.be.model.Post;
import com.be.payload.statistic.CountPaidPostByYear;
import com.be.repository.custom.PostSearchCustomRepository;
import com.be.utility.datatype.EROrderStatus;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostSearchCustomRepository {

    @Query(value = "select new com.be.payload.statistic.CountPaidPostByYear(year(p.paidDate), month(p.paidDate) ,count(*)) from PostOrder p where p.status = :status group by year(p.paidDate), month(p.paidDate)")
    List<CountPaidPostByYear> getCountPaidOrderGroupByMonth(@Param("status") EROrderStatus status);

    @Query(value = "select year(paid_date) as year ,month(paid_date)  as month, status,sum(total) as revenue,currency from post_order group by  year(paid_date) ,month(paid_date),currency,status ", nativeQuery = true)
    List<Tuple> getSumTotalByYearMonthCurrencyStatus();

    @Query(value = "select service.name as name ,sum(total) as revenue ,post_order.currency as currency from post_order , service where post_order.service_id = service.id and status = 'PAID' group by service_id,post_order.currency ", nativeQuery = true)
    List<Tuple> getSumTotalByService();

    @Query(value = "select service.name as name  ,count(post_order.id) as count ,post_order.currency as currency from post_order , service where post_order.service_id = service.id and status = 'PAID' group by service_id,post_order.currency ;", nativeQuery = true)
    List<Tuple> getCountTotalByService();
}
