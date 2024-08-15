package jpabook.jpashop.service;


import jakarta.persistence.EntityManager;
import jpabook.jpashop.NotEnoughStockExcetion;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Item.Book;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Fail.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = getMember();

        Book book = getBook();

        int orderCnt = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCnt);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        Book getBook = em.find(Book.class, book.getId());

        Assert.assertEquals("주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getOrderStatus());
        Assert.assertEquals("주문시 아이템 종류수가 같아야한다",getOrder.getOrderItems().size(), 1);
        Assert.assertEquals("주문 가격은 가격 * 수량이다", getOrder.getTotalPrice(), 1000*2);
        Assert.assertEquals("주문 수량이 줄어야합니다.", 8,getBook.getStockQuantity());
    }



    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = getMember();

        Book book = getBook();

        int orderCnt = 2;


        Long orderId = orderService.order(member.getId(), book.getId(), orderCnt);


        Order getOrder = orderRepository.findOne(orderId);
        
        //when

        orderService.cancelOrder(orderId);

        Order getOrder2 = orderRepository.findOne(orderId);
        Book getBook = em.find(Book.class, book.getId());

        
        //then
        Assert.assertEquals("주문 상태가 취소 상태여야합니다",getOrder.getOrderStatus() , OrderStatus.CANCEL);
        Assert.assertEquals("주문 수량이 다시 차야합니다", 10,getBook.getStockQuantity());

    }
    
    @Test(expected = NotEnoughStockExcetion.class)
    public void 상품수량초과() throws Exception{
        //given
        Member member = getMember();
        Book book = getBook();
        
        //when
        orderService.order(member.getId(), book.getId(), 11);



        //then
        fail("수량 보다 많이 주문하면 예외가 나와야한다.");
    }


    private Book getBook() {
        Book book = new Book();
        book.setName("jpa 너무 조아");
        book.setPrice(1000);
        book.setStockQuantity(10);
        em.persist(book);
        return book;
    }

    private Member getMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

}
