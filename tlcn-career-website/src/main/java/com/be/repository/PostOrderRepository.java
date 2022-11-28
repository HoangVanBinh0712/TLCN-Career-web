package com.be.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.be.model.Employer;
import com.be.model.Post;
import com.be.model.PostOrder;
import com.be.utility.datatype.EROrderStatus;

@Repository
public interface PostOrderRepository extends JpaRepository<PostOrder, Long> {

	PostOrder findByPostAndStatus(Post post, EROrderStatus status);

	Integer countByEmployerAndStatus(Employer employer, EROrderStatus status);

	List<PostOrder> findByEmployerAndStatus(Employer employer, EROrderStatus status, Pageable pageable);
}
