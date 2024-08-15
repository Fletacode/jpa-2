package jpabook.jpashop.repository.order.query;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderQueryDto{
    private Long id;
    private String memberName;
    private Address address;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(Long id, String memberName, Address address, OrderStatus status, LocalDateTime orderDate) {
        this.id = id;
        this.memberName = memberName;
        this.address = address;
        this.status = status;
        this.orderDate = orderDate;
    }

}