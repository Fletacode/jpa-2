package jpabook.jpashop.domain.Item;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("M")
@Getter
public class Movie extends Item {

    private String actor;
    private String director;

}
