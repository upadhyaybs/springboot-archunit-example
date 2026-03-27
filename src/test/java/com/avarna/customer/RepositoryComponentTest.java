package com.avarna.customer;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class RepositoryComponentTest extends ArchitectureTest{

    /* naming convention */
    @Test
    void repositoryClassesShouldBeNamedXRepository() {
        classes()
                .that().resideInAPackage(REPOSITORY_LAYER_PACKAGES)
                .should().haveSimpleNameEndingWith("Repository")
                .check(productionClasses);
    }

    @Test
    void repositoryClassesShouldHaveSpringRepositoryAnnotation() {
        classes()
                .that().resideInAPackage(REPOSITORY_LAYER_PACKAGES)
                .and().areInterfaces()
                .should().beAnnotatedWith(Repository.class)
                .check(productionClasses);
    }

    /* Package Dependency Checks*/
    @Test
    void repositoriesMustResideInRepositoryPackage() {
        classes().that().haveNameMatching(".*Repository").should().resideInAPackage(REPOSITORY_LAYER_PACKAGES)
                .as("Repositories should reside in a package '..repository..'")
                .check(productionClasses);
    }

    /* Class Dependency Checks*/
    @Test
    void repositoryClassesShouldOnlyBeAccessedByService() {
        classes()
                .that().resideInAPackage(REPOSITORY_LAYER_PACKAGES)
                .should().onlyBeAccessed().byAnyPackage(SERVICE_LAYER_PACKAGES, REPOSITORY_LAYER_PACKAGES)
                .check(productionClasses);
    }
}
