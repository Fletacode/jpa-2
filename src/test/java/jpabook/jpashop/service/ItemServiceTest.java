package jpabook.jpashop.service;


import jakarta.persistence.EntityManager;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.domain.Item.Book;
import jpabook.jpashop.domain.Item.Item;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(readOnly=true)
public class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    EntityManager em;

    @Rollback(value = false)
    @Test
    public void 아이템수정() throws Exception{
        //given
        Book book = new Book();
        book.setName("하하");
        book.setPrice(1000);
        book.setAuthor("김동훈");
        book.setStockQuantity(10);
        book.setIsbn("abc12");

        itemService.save(book);

        em.flush();
        em.clear();

        List<Item> items = itemRepository.findAll();
        Book findBook = (Book) items.get(0);
        //when

        findBook.changeBook(findBook.getId(),"호호",
                findBook.getPrice(),findBook.getStockQuantity(),
                findBook.getAuthor(), findBook.getIsbn());

        itemService.save(findBook);


        Book findBook2 = (Book) itemRepository.findOne(findBook.getId());
        //then

        Assert.assertEquals(findBook2.getName(),"호호");

    }


}
