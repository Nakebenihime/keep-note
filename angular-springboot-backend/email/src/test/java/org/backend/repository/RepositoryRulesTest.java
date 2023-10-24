package org.backend.repository;

import org.backend.ArchitectureArchUnitTest;
import org.junit.Test;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods;

public class RepositoryRulesTest extends ArchitectureArchUnitTest {

    @Test
    public void repository_classes_should_be_suffixed_as_such() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().haveSimpleNameEndingWith("Repository")
                .check(javaClasses);
    }

    @Test
    public void repositories_should_reside_in_a_repository_package() {
        classes()
                .that().haveNameMatching("Repository")
                .should().resideInAPackage("..repository..")
                .because("Repositories should reside in a package '..repository..'")
                .check(javaClasses);
    }

    @Test
    public void repositories_should_only_be_accessed_by_services() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().onlyBeAccessed().byAnyPackage("..service..")
                .check(javaClasses);
    }

    @Test
    public void repository_classes_should_have_spring_repository_annotation() {
        classes()
                .that().resideInAPackage("..repository..")
                .and().haveNameMatching("Repository")
                .should().beAnnotatedWith(Repository.class)
                .check(javaClasses);
    }

    @Test
    public void repositories_must_not_thrown_SQLExceptions() {
        noMethods()
                .that()
                .areDeclaredInClassesThat()
                .haveNameMatching("Repository")
                .should().declareThrowableOfType(SQLException.class)
                .check(javaClasses);
    }

}
