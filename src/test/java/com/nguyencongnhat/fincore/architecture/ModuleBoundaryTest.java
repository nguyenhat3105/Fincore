package com.nguyencongnhat.fincore.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ModuleBoundaryTest {

    private static final String[] BUSINESS_MODULE_PACKAGES = {
        "..identity..",
        "..wallet..",
        "..ledger..",
        "..payment..",
        "..merchant..",
        "..settlement..",
        "..reconciliation..",
        "..risk..",
        "..webhook..",
        "..audit.."
    };

    private static JavaClasses importedClasses;

    @BeforeAll
    static void importApplicationClasses() {
        importedClasses =
                new ClassFileImporter()
                        .withImportOption(new ImportOption.DoNotIncludeTests())
                        .importPackages("com.nguyencongnhat.fincore");
    }

    @Test
    void sharedKernelDoesNotDependOnBusinessModules() {
        noClasses()
                .that()
                .resideInAPackage("..shared..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(BUSINESS_MODULE_PACKAGES)
                .because("shared code must not depend on a business bounded context")
                .check(importedClasses);
    }

    @Test
    void topLevelModulesAreFreeOfCycles() {
        slices().matching("com.nguyencongnhat.fincore.(*)..")
                .should()
                .beFreeOfCycles()
                .check(importedClasses);
    }
}
