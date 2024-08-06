package test.group.counters.models;

public enum Permission
{
    OPERATOR_GET_COUNTER(
            "operator:get"
    ),
    COUNTER_POST_READING(
            "counter:post_reading"
    ),
    ADMIN_CREATE(
            "admin:create"
    ),
    ADMIN_GET(
            "admin:get"
    ),
    ADMIN_UPDATE(
            "admin:update"
    ),
    ADMIN_DELETE(
            "admin:delete"
    );

    private final String permission;


    Permission(String permission)
    {
        this.permission = permission;
    }


    public String getPermission()
    {
        return permission;
    }
}
