package jpabook.jpashop.repository.order.OrderSimpleQueryRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;


    public List<OrderSimpleQueryDto> findAllOrderQueryDto(){
        String jpql = "select new jpabook.jpashop.repository.order.OrderSimpleQueryRepository.OrderSimpleQueryDto(" +
                "o.id,o.member.name,o.orderDate," +
                "o.orderStatus,o.delivery.address) " +
                "from Order o" +
                "  join  o.member m " +
                "join  o.delivery d";

        return em.createQuery(jpql, OrderSimpleQueryDto.class).getResultList();
    }

}
