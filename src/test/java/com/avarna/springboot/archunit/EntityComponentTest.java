package com.tp.springboot.archunit;

import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

import javax.persistence.Entity;
import java.io.Serializable;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class EntityComponentTest extends ArchitectureTest{

    @Test
    public void entityClassesShouldOnlyBeAccessedByOtherEntityClassesOrTheServiceLayerOrRepositoryLayer() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().resideInAPackage(ENTITY_LAYER_PACKAGES)
                .should().onlyBeAccessed().byAnyPackage(ENTITY_LAYER_PACKAGES,SERVICE_LAYER_PACKAGES,REPOSITORY_LAYER_PACKAGES);
        rule.check(classes);
    }

    @Test
    public void entityClassesShouldOnlyDependOnEntityOrStdLibClasses() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().resideInAPackage(ENTITY_LAYER_PACKAGES)
                .should().onlyDependOnClassesThat().resideInAnyPackage(ENTITY_LAYER_PACKAGES, "java..","javax..");
        rule.check(classes);
    }

    @Test
    void entityClassesShouldBeSerializable() {
        classes()
                .that().resideInAPackage("..entity..")
                .should()
                .beAssignableTo(Serializable.class)
                .check(classes);
    }

    @Test
    void entityClassesShouldBePublic() {
        classes()
                .that().resideInAPackage("..entity..")
                .should()
                .bePublic()
                .check(classes);
    }

    @Test
    void entityClassesShouldBeAnnotatedWithEntity() {
        classes()
                .that().resideInAPackage("..entity..")
                .should().beAnnotatedWith(Entity.class)
                .check(classes);
    }

}
