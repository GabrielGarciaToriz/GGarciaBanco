
package com.digis.GGarciaBanco;

import com.digis.ggarciabanco.GGarciaBancoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ModulithDocumentationTest {
    
    @Test
    void generarDocumentacionModular() {
        var modules = ApplicationModules.of(GGarciaBancoApplication.class);

        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml()
                .writeModuleCanvases();
    }
}
