package jpabook.jpashop.api;


import jpabook.jpashop.repository.order.OrderSimpleQueryRepository.OrderSimpleQueryDto;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.OrderSimpleQueryRepository.OrderSimpleQueryRepository;
import jpabook.jpashop.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiSimpleController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;


    @GetMapping("/api/simple/v1/orders")
    public Order getOrders() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        Order order = orderRepository.findOne(1L);

        return order;
    }

    @GetMapping("/api/simple/v2/orders")
    public List<OrderDTO> getOrdersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());



        List<OrderDTO> orderDTOS = orders.stream()
                                .map(o -> new OrderDTO(o.getMember().getName(),
                                        o.getId(), o.getOrderDate(), o.getOrderStatus(),
                                        o.getDelivery().getAddress()))
                                .collect(Collectors.toList());

        return orderDTOS;
    }

    @GetMapping("/api/simple/v3/orders")
    public List<OrderDTO> getOrdersV3() {
        List<Order> orders = orderRepository.findAllOrdersMemberOrderItemsDelivery();

        List<OrderDTO> orderDTOS = orders.stream()
                .map(o -> new OrderDTO(o.getMember().getName(),
                        o.getId(), o.getOrderDate(), o.getOrderStatus(),
                        o.getDelivery().getAddress()))
                .collect(Collectors.toList());

        return orderDTOS;
    }

    @GetMapping("/api/simple/v4/orders")
    public List<OrderSimpleQueryDto> getOrdersV4() {
        List<OrderSimpleQueryDto> orders = orderSimpleQueryRepository.findAllOrderQueryDto();

        return orders;
    }


    @Data
    @AllArgsConstructor
    static class OrderDTO{
        private String memberName;
        private Long id;
        private LocalDateTime orderDate;
        private OrderStatus status;
        private Address address;

    }

}
