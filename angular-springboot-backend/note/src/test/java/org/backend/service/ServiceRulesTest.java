package org.backend.service;

import org.backend.ArchitectureArchUnitTest;
import org.junit.Test;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ServiceRulesTest extends ArchitectureArchUnitTest {

    @Test
    public void service_classes_should_be_suffixed_as_such() {
        classes()
                .that().resideInAPackage("..service..")
                .and().areAnnotatedWith(Service.class)
                .should().haveSimpleNameEndingWith("ServiceImpl")
                .check(javaClasses);
    }

    @Test
    public void services_should_reside_in_a_service_package() {
        classes()
                .that().haveNameMatching("ServiceImpl")
                .and().areAnnotatedWith(Service.class)
                .should().resideInAPackage("..service..")
                .because("Services should reside in a package '..service..'")
                .check(javaClasses);
    }

    @Test
    public void services_should_only_be_accessed_by_controllers() {
        classes()
                .that().resideInAPackage("..service..")
                .and().areAnnotatedWith(Service.class)
                .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..")
                .check(javaClasses);
    }

    @Test
    public void service_classes_should_have_spring_service_annotation() {
        classes()
                .that().resideInAPackage("..service..")
                .and().haveNameMatching("ServiceImpl")
                .should().beAnnotatedWith(Service.class)
                .check(javaClasses);
    }
}
