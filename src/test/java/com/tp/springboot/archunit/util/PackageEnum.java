package com.tp.springboot.archunit.util;

public enum PackageEnum {

    BASE_PACKAGE("com.tp.springboot.archunit"),
    DOMAIN_PACKAGE("com.tp.springboot.archunit.domain"),
    SERVICE_PACKAGE("com.tp.springboot.archunit.service"),
    CONTROLLER_PACKAGE("com.tp.springboot.archunit.controller"),
    ENTITY_PACKAGE("com.tp.springboot.archunit.entity"),
    REPOSITORY_PACKAGE("com.tp.springboot.archunit.repository");

    private String packagePath;

    PackageEnum(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getPackagePath(){
        return this.packagePath;
    }

}
