package mkn.api.my_registry_api.entities.enums;

public enum OrderStatus {
    CART(0),
    PENDING_PAYMENT(1),
    PAID(2),
    SHIPPED(3),
    DELIVERED(4),
    COMPLETED(5),
    CANCELLED(6);

    private final int code;

    OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OrderStatus fromCode(int code) {
        for (OrderStatus v : values()) {
            if (v.code == code) return v;
        }
        throw new IllegalArgumentException("Código inválido OrderStatus: " + code);
    }
}
