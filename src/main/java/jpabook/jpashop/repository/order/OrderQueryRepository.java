package jpabook.jpashop.repository.order;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.repository.order.query.OrderItemQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findAllByDto_optimization() {
        List<OrderQueryDto> result = findAllOrder();

        List<Long> orderIds = result.stream()
                .map(o -> o.getId())
                .collect(Collectors.toList());

        List<OrderItemQueryDto> orderItems = em.createQuery(
                "select new jpabook.jpashop.repository.order.query." +
                        " OrderItemQueryDto(oi.order.id, oi.orderPrice, oi.count) " +
                        " from OrderItem  oi" +
                        " join oi.item i" +
                        " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItemsById = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto -> OrderItemQueryDto.getId()));

        result.forEach(o -> o.setOrderItems(orderItemsById.get(o.getId())));

        return result;


    }


    public List<OrderQueryDto> findQueryOrder() {
        List<OrderQueryDto> result = findAllOrder();

        for (OrderQueryDto order : result) {
            List<OrderItemQueryDto> orderItems = findOrderItem(order.getId());

            order.setOrderItems(orderItems);
        }

        return result;

    }

    public List<OrderItemQueryDto> findOrderItem(Long orderId) {

        String query = "select new jpabook.jpashop.repository.order.query." +
                " OrderItemQueryDto(oi.order.id, oi.orderPrice, oi.count )" +
                " from OrderItem oi " +
                " join oi.item i" +
                " where oi.order.id = :orderId";

        return em.createQuery(query,OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    public List<OrderQueryDto> findAllOrder(){
        String query = "select new jpabook.jpashop.repository.order.query." +
                " OrderQueryDto(o.id, m.name, d.address, o.orderStatus, o.orderDate) " +
                " from Order o " +
                " join o.member m" +
                " join o.delivery d";


        return em.createQuery(query, OrderQueryDto.class).getResultList();
    }








}
