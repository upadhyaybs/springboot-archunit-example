package com.tp.springboot.archunit;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class RepositoryComponentTest extends ArchitectureTest{

    /* naming convention */
    @Test
    void repositoryClassesShouldBeNamedXRepository() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().haveSimpleNameEndingWith("Repository")
                .check(classes);
    }

    @Test
    void repositoryClassesShouldHaveSpringRepositoryAnnotation() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().beAnnotatedWith(Repository.class)
                .check(classes);
    }

    /* Package Dependency Checks*/
    @Test
    void repositoriesMustResideInRepositoryPackage() {
        classes().that().haveNameMatching(".*Repository").should().resideInAPackage("..repository..")
                .as("Repositories should reside in a package '..repository..'")
                .check(classes);
    }

    /* Class Dependency Checks*/
    @Test
    void repositoryClassesShouldOnlyBeAccessedByService() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().onlyBeAccessed().byAnyPackage("..service..", "..repository..")
                .check(classes);
    }
}
