package com.avarna.customer.util;

public enum PackageEnum {

    BASE_PACKAGE("com.avarna.customer"),
    DOMAIN_PACKAGE("com.avarna.customer.domain"),
    SERVICE_PACKAGE("com.avarna.customer.service"),
    CONTROLLER_PACKAGE("com.avarna.customer.controller"),
    ENTITY_PACKAGE("com.avarna.customer.entity"),
    REPOSITORY_PACKAGE("com.avarna.customer.repository");

    private String packagePath;

    PackageEnum(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getPackagePath(){
        return this.packagePath;
    }

}
