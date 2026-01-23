package com.tp.springboot.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchitectureTest {

    private static final String BASE_PACKAGE = "com.tp.springboot.archunit";

    static final String DOMAIN_LAYER_PACKAGES = "com.tp.springboot.archunit.domain..";
    static final String ENTITY_LAYER_PACKAGES = "com.tp.springboot.archunit.entity..";
    static final String WEB_LAYER_PACKAGES = "com.tp.springboot.archunit.controller..";
    static final String SERVICE_LAYER_PACKAGES = "com.tp.springboot.archunit.service..";
    static final String REPOSITORY_LAYER_PACKAGES = "com.tp.springboot.archunit.repository..";
    static final String CONFIG_LAYER_PACKAGES = "com.tp.springboot.archunit.config..";
    static final String ADAPTERS_LAYER_PACKAGES = "com.tp.springboot.archunit.adapters..";
    static final String EXCEPTION_LAYER_PACKAGES = "com.tp.springboot.archunit.exception..";

    final static JavaClasses classes=new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(BASE_PACKAGE);


    @Test
    void layeredArchitectureShouldBeRespected() {
        layeredArchitecture()
                .consideringOnlyDependenciesInLayers()
                .layer("Controller").definedBy("..controller..")
                .layer("Service").definedBy("..service..")
                .layer("Repository").definedBy("..repository..")

                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
                .check(classes);
    }

    @Test
    void interfacesShouldNotHaveNamesEndingWithTheWordInterface() {
        noClasses().that().areInterfaces().should().haveNameMatching(".*Interface").check(classes);
    }

    @Test
    void fieldInjectionNotUseAutowiredAnnotation() {
        noFields()
                .should().beAnnotatedWith(Autowired.class)
                .check(classes);
    }

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        noClasses()
                .that().resideInAnyPackage(SERVICE_LAYER_PACKAGES)
                .or().resideInAnyPackage(REPOSITORY_LAYER_PACKAGES)
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(WEB_LAYER_PACKAGES)
                .because("Services and repositories should not depend on web layer")
                .check(classes);
    }


}
