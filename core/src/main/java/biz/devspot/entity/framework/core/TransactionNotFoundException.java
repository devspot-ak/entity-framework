package biz.devspot.entity.framework.core;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException() {
        super("An open transaction could not be found");
    }

}
