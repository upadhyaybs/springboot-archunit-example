package com.avarna.customer;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ControllerComponentTest extends ArchitectureTest{

    /* naming convention */
    @Test
    void controllerClassesShouldBeNamedXController() {
        classes()
                .that().resideInAPackage(WEB_LAYER_PACKAGES)
                .should().haveSimpleNameEndingWith("Controller")
                .check(productionClasses);
    }

    @Test
    void controllerClassesShouldHaveSpringRestControllerAnnotation() {
        classes()
                .that().resideInAPackage(WEB_LAYER_PACKAGES)
                .should().beAnnotatedWith(RestController.class)
                .check(productionClasses);
    }

    /*    Package Dependency Checks*/
    @Test
    void controllersMustResideInControllerPackage() {
        classes().that().haveNameMatching(".*Controller").should().resideInAPackage(WEB_LAYER_PACKAGES)
                .as("Controllers should reside in a package '..controller..'")
                .check(productionClasses);
    }


}
