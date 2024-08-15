package jpabook.jpashop.api;




import jpabook.jpashop.domain.Address;


import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;

import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.OrderQueryRepository;
import jpabook.jpashop.repository.OrderRepository;


import jpabook.jpashop.repository.order.query.OrderQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;


    @GetMapping("/api/v1/orders")
    public List<Order> getOrder() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        for (Order order : all) {
            order.getMember().getId();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();

            orderItems.stream().forEach(oi -> {
                oi.getItem().getName();
            });
        }

        return all;

    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> getOrder2() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all.stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());

    }

    @GetMapping("/api/v3/orders")
    public List<Order> getOrder3() {
        return orderRepository.findAllOrdersMemberOrderItemsDelivery();
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> getOrder3Join() {
        List<Order> all = orderRepository.findAllOrdersFetchBatchSize();

        List<OrderDto> orderDtos = all.stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());

        return orderDtos;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> getOrder4Join() {
        List<OrderQueryDto> orders = orderQueryRepository.findQueryOrder();

        return orders;
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> getOrder5Join(){

        List<OrderQueryDto> orders = orderQueryRepository.findAllByDto_optimization();

        return orders;
    }



    @Data
    static class OrderDto{
        private String memberName;
        private Address deliveryAddress;
        private List<OrderItemDto> orderItems;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;

        public OrderDto(Order order){
            this.memberName = order.getMember().getName();
            this.deliveryAddress = order.getDelivery().getAddress();

            //order.getOrderItems().stream().forEach(oi -> {oi.getItem().getName(); } );
            this.orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem) )
                    .collect(Collectors.toList());

            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getOrderStatus();
        }
    }

    @Data
    static class OrderItemDto{
        private String itemName;
        private int quantity;

        public OrderItemDto(OrderItem orderItem){
            this.itemName = orderItem.getItem().getName();
            this.quantity = orderItem.getItem().getStockQuantity();
        }
    }




}
