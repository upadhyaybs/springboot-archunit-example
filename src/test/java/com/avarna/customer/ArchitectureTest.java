package com.avarna.customer;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchitectureTest {

    private static final String BASE_PACKAGE = "com.avarna.customer";

    static final String DOMAIN_LAYER_PACKAGES = "com.avarna.customer.domain..";
    static final String ENTITY_LAYER_PACKAGES = "com.avarna.customer.entity..";
    static final String WEB_LAYER_PACKAGES = "com.avarna.customer.controller..";
    static final String SERVICE_LAYER_PACKAGES = "com.avarna.customer.service..";
    static final String REPOSITORY_LAYER_PACKAGES = "com.avarna.customer.repository..";
    static final String CONFIG_LAYER_PACKAGES = "com.avarna.customer.config..";
    static final String ADAPTERS_LAYER_PACKAGES = "com.avarna.customer.adapters..";
    static final String EXCEPTION_LAYER_PACKAGES = "com.avarna.customer.exception..";

    static final JavaClasses productionClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(BASE_PACKAGE);


    @Test
    void layeredArchitectureShouldBeRespected() {
        layeredArchitecture()
                .consideringOnlyDependenciesInLayers()
                .layer("Controller").definedBy(WEB_LAYER_PACKAGES)
                .layer("Service").definedBy(SERVICE_LAYER_PACKAGES)
                .layer("Repository").definedBy(REPOSITORY_LAYER_PACKAGES)

                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
                .check(productionClasses);
    }

    @Test
    void interfacesShouldNotHaveNamesEndingWithTheWordInterface() {
        classes()
                .that().areInterfaces()
                .should().haveNameNotMatching(".*Interface")
                .check(productionClasses);
    }

    @Test
    void fieldInjectionNotUseAutowiredAnnotation() {
        fields()
                .should().notBeAnnotatedWith(Autowired.class)
                .check(productionClasses);
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
                .check(productionClasses);
    }


}
