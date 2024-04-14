package org.fexisaf.crimerecordmanagementsystem.entity;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Role {

    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    Permission.ADMIN_READ,
                    Permission.ADMIN_CREATE,
                    Permission.ADMIN_DELETE,
                    Permission.ADMIN_UPDATE,
                    Permission.LAW_ENFORCEMENT_OFFICER_READ,
                    Permission.LAW_ENFORCEMENT_OFFICER_CREATE,
                    Permission.LAW_ENFORCEMENT_OFFICER_DELETE,
                    Permission.LAW_ENFORCEMENT_OFFICER_UPDATE,
                    Permission.CLARK_READ,
                    Permission.CLARK_CREATE,
                    Permission.CLARK_UPDATE,
                    Permission.CLARK_DELETE,
                    Permission.MANAGER_READ,
                    Permission.MANAGER_CREATE,
                    Permission.MANAGER_UPDATE,
                    Permission.MANAGER_DELETE
            )
    ),

    MANAGER(
            Set.of(
                    Permission.MANAGER_READ,
                    Permission.MANAGER_CREATE,
                    Permission.MANAGER_UPDATE,
                    Permission.MANAGER_DELETE
            )
    ),

    LAW_ENFORCEMENT_OFFICER(
            Set.of(

                    Permission.LAW_ENFORCEMENT_OFFICER_READ,
                    Permission.LAW_ENFORCEMENT_OFFICER_CREATE,
                    Permission.LAW_ENFORCEMENT_OFFICER_DELETE,
                    Permission.LAW_ENFORCEMENT_OFFICER_UPDATE
            )
    ),

    CLARK(
            Set.of(

                    Permission.CLARK_READ,
                    Permission.CLARK_CREATE,
                    Permission.CLARK_UPDATE,
                    Permission.CLARK_DELETE
            )
    ),

    WARDEN(
            Collections.emptySet()
    ),

    JUDGE(
            Collections.emptySet()
    )



    ;

    @Getter
    private final Set<Permission> permissions;


    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities =
                getPermissions()
                        .stream()
                                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                                        .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
