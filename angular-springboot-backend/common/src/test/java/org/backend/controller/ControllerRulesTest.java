package org.backend.controller;

import org.backend.ArchitectureArchUnitTest;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

public class ControllerRulesTest extends ArchitectureArchUnitTest {

    @Test
    public void controller_classes_should_be_suffixed_as_such() {
        classes()
                .that().resideInAPackage("..controller..")
                .and().areAnnotatedWith(Controller.class)
                .or().areAnnotatedWith(RestController.class)
                .should().haveSimpleNameEndingWith("Controller")
                .check(javaClasses);
    }

    @Test
    public void controllers_should_reside_in_a_controller_package() {
        classes()
                .that().haveNameMatching("Controller")
                .and().areAnnotatedWith(Controller.class)
                .or().areAnnotatedWith(RestController.class)
                .should().resideInAPackage("..controller..")
                .because("Controllers should reside in a package '..controller..'")
                .check(javaClasses);
    }

    @Test
    public void controller_classes_should_have_spring_controller_or_rest_controller_annotations() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().beAnnotatedWith(RestController.class)
                .orShould().beAnnotatedWith(Controller.class)
                .check(javaClasses);
    }

    @Test
    public void controller_methods_should_be_annotated_with_spring_mvc_mapping_annotations() {
        methods()
                .that().arePublic()
                .and().areDeclaredInClassesThat().resideInAPackage("..controller..")
                .and().areDeclaredInClassesThat().haveSimpleNameEndingWith("Controller")
                .and().areDeclaredInClassesThat().areAnnotatedWith(Controller.class)
                .or().areDeclaredInClassesThat().areAnnotatedWith(RestController.class)
                .should().beAnnotatedWith(RequestMapping.class)
                .orShould().beAnnotatedWith(GetMapping.class)
                .orShould().beAnnotatedWith(PostMapping.class)
                .orShould().beAnnotatedWith(PatchMapping.class)
                .orShould().beAnnotatedWith(DeleteMapping.class);
    }
}
