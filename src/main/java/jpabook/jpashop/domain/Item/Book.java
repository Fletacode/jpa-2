package jpabook.jpashop.domain.Item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B")
@Getter @Setter
public class Book extends Item {

    private String author;
    private String isbn;

    public void changeBook(Long id, String name, int price, int stockQuantity, String author, String isbn   ) {


        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setStockQuantity(stockQuantity);
        this.setIsbn(isbn);
        this.setAuthor(author);


    }


}
