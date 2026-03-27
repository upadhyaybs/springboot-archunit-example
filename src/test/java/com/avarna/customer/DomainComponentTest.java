package com.avarna.customer;

import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class DomainComponentTest extends ArchitectureTest{

    @Test
    public void domainClassesShouldOnlyBeAccessedByOtherDomainClassesOrTheApplicationLayer() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().resideInAPackage(DOMAIN_LAYER_PACKAGES)
                .should().onlyBeAccessed().byAnyPackage(DOMAIN_LAYER_PACKAGES, WEB_LAYER_PACKAGES,SERVICE_LAYER_PACKAGES);
        rule.check(productionClasses);
    }

    @Test
    public void domainClassesShouldOnlyDependOnDomainOrStdLibClasses() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().resideInAPackage(DOMAIN_LAYER_PACKAGES)
                .should().onlyDependOnClassesThat().resideInAnyPackage(DOMAIN_LAYER_PACKAGES, "java..", "javax..", "jakarta..", "lombok..", "org.springframework..", "com.fasterxml..");
        rule.check(productionClasses);
    }

    @Test
    void domainClassesShouldBeSerializable() {
        classes()
                .that().resideInAPackage(DOMAIN_LAYER_PACKAGES)
                .should()
                .beAssignableTo(Serializable.class)
                .check(productionClasses);
    }

    @Test
    void domainClassesShouldBePublic() {
        classes()
                .that().resideInAPackage(DOMAIN_LAYER_PACKAGES)
                .should()
                .bePublic()
                .check(productionClasses);
    }



}
