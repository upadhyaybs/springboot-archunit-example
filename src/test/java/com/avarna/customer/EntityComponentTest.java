package com.avarna.customer;

import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

import jakarta.persistence.Entity;
import java.io.Serializable;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class EntityComponentTest extends ArchitectureTest{

    @Test
    public void entityClassesShouldOnlyBeAccessedByOtherEntityClassesOrTheServiceLayerOrRepositoryLayer() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().resideInAPackage(ENTITY_LAYER_PACKAGES)
                .should().onlyBeAccessed().byAnyPackage(ENTITY_LAYER_PACKAGES,SERVICE_LAYER_PACKAGES,REPOSITORY_LAYER_PACKAGES);
        rule.check(productionClasses);
    }

    @Test
    public void entityClassesShouldOnlyDependOnEntityOrStdLibClasses() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().resideInAPackage(ENTITY_LAYER_PACKAGES)
                .should().onlyDependOnClassesThat().resideInAnyPackage(ENTITY_LAYER_PACKAGES, "java..", "javax..", "jakarta..", "lombok..");
        rule.check(productionClasses);
    }

    @Test
    void entityClassesShouldBeSerializable() {
        classes()
                .that().resideInAPackage(ENTITY_LAYER_PACKAGES)
                .should()
                .beAssignableTo(Serializable.class)
                .check(productionClasses);
    }

    @Test
    void entityClassesShouldBePublic() {
        classes()
                .that().resideInAPackage(ENTITY_LAYER_PACKAGES)
                .should()
                .bePublic()
                .check(productionClasses);
    }

    @Test
    void entityClassesShouldBeAnnotatedWithEntity() {
        classes()
                .that().resideInAPackage(ENTITY_LAYER_PACKAGES)
                .should().beAnnotatedWith(Entity.class)
                .check(productionClasses);
    }

}
