package jpabook.jpashop.repository.order.OrderSimpleQueryRepository;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderSimpleQueryDto {

    private Long id;
    private String memberName;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Address address;

    /*
    public OrderQueryDto(Order order){
        this.id = order.getId();
        this.address = order.getDelivery().getAddress();
        this.memberName = order.getMember().getName();
        this.orderDate = order.getOrderDate();
    }
     */

}
