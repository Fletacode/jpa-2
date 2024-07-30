package jpabook.jpashop;

public class NotEnoughStockExcetion extends RuntimeException {
    public NotEnoughStockExcetion() {

    }

    public NotEnoughStockExcetion(String message) {
        super(message);
    }

    public NotEnoughStockExcetion(String message, Throwable cause) {
        super(message, cause);
    }
    public NotEnoughStockExcetion(Throwable cause) {
        super(cause);
    }

}
