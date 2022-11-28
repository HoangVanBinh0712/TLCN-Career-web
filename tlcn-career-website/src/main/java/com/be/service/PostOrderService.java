package com.be.service;

import com.be.dto.PostOrderDTO;
import com.be.payload.ListResponse;
import com.be.utility.Page;
import com.be.utility.datatype.EROrderStatus;

public interface PostOrderService {
    ListResponse<PostOrderDTO> getOrders(EROrderStatus status, Long empId, Page page) ;

    Integer getCountOrder(EROrderStatus status, Long empId);
}
