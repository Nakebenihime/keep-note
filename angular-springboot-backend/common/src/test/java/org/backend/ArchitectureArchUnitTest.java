package org.backend;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.ImportOptions;
import com.tngtech.archunit.lang.syntax.elements.GivenClassesConjunction;
import org.junit.Before;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public abstract class ArchitectureArchUnitTest {

    protected GivenClassesConjunction controllers;
    protected GivenClassesConjunction services;
    protected GivenClassesConjunction repositories;
    protected JavaClasses javaClasses;

    @Before
    public void init() {
        this.controllers = classes().that().haveNameMatching(".*Controller");
        this.services = classes().that().haveNameMatching(".*Service");
        this.repositories = classes().that().haveNameMatching(".*Repository");
        this.javaClasses = new ClassFileImporter().importClasspath(new ImportOptions()
                .with(new ImportOption.DoNotIncludeJars())
                .with(new ImportOption.DoNotIncludeTests())
                .with(new ImportOption.DoNotIncludeArchives()));
    }
}
