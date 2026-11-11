package com.nguyencongnhat.fincore.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LayeredArchitectureTest {

    private static JavaClasses importedClasses;

    @BeforeAll
    static void importApplicationClasses() {
        importedClasses =
                new ClassFileImporter()
                        .withImportOption(new ImportOption.DoNotIncludeTests())
                        .importPackages("com.nguyencongnhat.fincore");
    }

    @Test
    void domainDoesNotDependOnFrameworkOrOuterLayers() {
        noClasses()
                .that()
                .resideInAPackage("..domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "org.springframework..",
                        "jakarta.persistence..",
                        "com.fasterxml.jackson..",
                        "..api..",
                        "..application..",
                        "..infrastructure..")
                .because("domain code must remain framework and delivery mechanism agnostic")
                .check(importedClasses);
    }

    @Test
    void applicationDoesNotDependOnApiOrInfrastructure() {
        noClasses()
                .that()
                .resideInAPackage("..application..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..api..", "..infrastructure..")
                .because("application use cases must point inward")
                .check(importedClasses);
    }

    @Test
    void controllersResideInApiPackages() {
        classes()
                .that()
                .haveSimpleNameEndingWith("Controller")
                .should()
                .resideInAPackage("..api..")
                .check(importedClasses);
    }

    @Test
    void restControllersUseControllerSuffix() {
        classes()
                .that()
                .areAnnotatedWith("org.springframework.web.bind.annotation.RestController")
                .should()
                .haveSimpleNameEndingWith("Controller")
                .check(importedClasses);
    }

    @Test
    void inboundPortsUseUseCaseSuffix() {
        classes()
                .that()
                .resideInAPackage("..application.port.in..")
                .should()
                .beInterfaces()
                .andShould()
                .haveSimpleNameEndingWith("UseCase")
                .check(importedClasses);
    }
}
