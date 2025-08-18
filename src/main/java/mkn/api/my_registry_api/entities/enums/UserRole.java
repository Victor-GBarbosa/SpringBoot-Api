package mkn.api.my_registry_api.entities.enums;

public enum UserRole {
    USER(1),
    SELLER(2),
    ADMIN(3),
    MASTER(4);

    private int id;

    public static UserRole valueOf(int id) {
        for (UserRole role : UserRole.values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        return null;
//        throw new IllegalArgumentException("Invalid UserRole id: " + id);

    }

    private UserRole(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
