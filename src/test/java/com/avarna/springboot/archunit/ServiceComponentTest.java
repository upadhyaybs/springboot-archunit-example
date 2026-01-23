package com.tp.springboot.archunit;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ServiceComponentTest extends ArchitectureTest{

    /* naming convention */
    @Test
    void serviceClassesShouldBeNamedXServiceOrXComponentOrXServiceImpl() {
        classes()
                .that().resideInAPackage("..service..")
                .should().haveSimpleNameEndingWith("Service")
                .orShould().haveSimpleNameEndingWith("ServiceImpl")
                .orShould().haveSimpleNameEndingWith("Component")
                .check(classes);
    }

    @Test
    void serviceClassesShouldHaveSpringServiceAnnotation() {
        classes()
                .that().resideInAPackage("..service..")
                .should().beAnnotatedWith(Service.class)
                .check(classes);
    }

    /* Package Dependency Checks*/
    @Test
    void serviceMustResideInServicePackage() {
        classes().that().haveNameMatching(".*Service").should().resideInAPackage("..service..")
                .as("Service should reside in a package '..service..'")
                .check(classes);
    }

    /* Class Dependency Checks*/
    @Test
    void serviceClassesShouldOnlyBeAccessedByController() {
        classes()
                .that().resideInAPackage("..service..")
                .should().onlyBeAccessed().byAnyPackage("..service..", "..controller..")
                .check(classes);
    }



}
