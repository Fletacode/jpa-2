package jpabook.jpashop.repository.order.query;

import lombok.Data;

@Data
public class OrderItemQueryDto{
    private Long id;
    //private Item item;
    private int orderPrice;
    private int count;

    public OrderItemQueryDto(Long id, int orderPrice, int count){
        this.id = id;
        //this.item = orderItem.getItem();
        this.orderPrice = orderPrice;
        this.count = count;
    }


}