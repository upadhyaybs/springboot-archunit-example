package com.tp.springboot.archunit;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ControllerComponentTest extends ArchitectureTest{

    /* naming convention */
    @Test
    void controllerClassesShouldBeNamedXController() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().haveSimpleNameEndingWith("Controller")
                .check(classes);
    }

    @Test
    void controllerClassesShouldHaveSpringRestControllerAnnotation() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().beAnnotatedWith(RestController.class)
                .check(classes);
    }

    /*    Package Dependency Checks*/
    @Test
    void controllersMustResideInControllerPackage() {
        classes().that().haveNameMatching(".*Controller").should().resideInAPackage("..controller..")
                .as("Controllers should reside in a package '..controller..'")
                .check(classes);
    }


}
