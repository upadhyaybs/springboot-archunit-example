package com.avarna.customer;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ServiceComponentTest extends ArchitectureTest{

    /* naming convention */
    @Test
    void serviceClassesShouldBeNamedXServiceOrXComponentOrXServiceImpl() {
        classes()
                .that().resideInAPackage(SERVICE_LAYER_PACKAGES)
                .should().haveSimpleNameEndingWith("Service")
                .orShould().haveSimpleNameEndingWith("ServiceImpl")
                .orShould().haveSimpleNameEndingWith("Component")
                .check(productionClasses);
    }

    @Test
    void serviceClassesShouldHaveSpringServiceAnnotation() {
        classes()
                .that().resideInAPackage(SERVICE_LAYER_PACKAGES)
                .and().areNotInterfaces()
                .should().beAnnotatedWith(Service.class)
                .check(productionClasses);
    }

    /* Package Dependency Checks*/
    @Test
    void serviceMustResideInServicePackage() {
        classes().that().haveNameMatching(".*Service").should().resideInAPackage(SERVICE_LAYER_PACKAGES)
                .as("Service should reside in a package '..service..'")
                .check(productionClasses);
    }

    /* Class Dependency Checks*/
    @Test
    void serviceClassesShouldOnlyBeAccessedByController() {
        classes()
                .that().resideInAPackage(SERVICE_LAYER_PACKAGES)
                .should().onlyBeAccessed().byAnyPackage(SERVICE_LAYER_PACKAGES, WEB_LAYER_PACKAGES)
                .check(productionClasses);
    }



}
