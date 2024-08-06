package test.group.counters.models;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    OPERATOR(
          Set.of(
                  Permission.OPERATOR_GET_COUNTER
          )
    ),
    ADMIN(
            Set.of(
                    Permission.ADMIN_CREATE,
                    Permission.ADMIN_GET,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_DELETE
            )
    ),
    METER(
            Set.of(
                    Permission.COUNTER_POST_READING
            )
    );

    private final Set<Permission> permissions;


    Role(Set<Permission> permissions)
    {
        this.permissions = permissions;
    }


    public Set<Permission> getPermissions()
    {
        return permissions;
    }


    public List<SimpleGrantedAuthority> getAuthorities()
    {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
