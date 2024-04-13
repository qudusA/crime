package org.fexisaf.crimerecordmanagementsystem.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_CREATE("admin:create"),

    MANAGER_READ("manager:read"),
    MANAGER_UPDATE("manager:update"),
    MANAGER_DELETE("manager:delete"),
    MANAGER_CREATE("manager:create"),

    LAW_ENFORCEMENT_OFFICER_READ("police:read"),
    LAW_ENFORCEMENT_OFFICER_UPDATE("police:update"),
    LAW_ENFORCEMENT_OFFICER_DELETE("police:delete"),
    LAW_ENFORCEMENT_OFFICER_CREATE("police:create"),

    CLARK_READ("clark:read"),
    CLARK_UPDATE("clark:update"),
    CLARK_DELETE("clark:delete"),
    CLARK_CREATE("clark:create")


    ;
    @Getter
    private final String permission;
}
