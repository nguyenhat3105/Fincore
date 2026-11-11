package com.nguyencongnhat.fincore.shared.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nguyencongnhat.fincore.system.api.SystemInfoController;
import com.nguyencongnhat.fincore.system.application.SystemInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(SystemInfoController.class)
@Import({SystemInfoService.class, SecurityConfig.class})
class SecurityConfigurationTest {

    @Autowired private MockMvc mockMvc;

    @Test
    void systemInfoIsPublic() throws Exception {
        mockMvc.perform(get("/api/v1/system/info")).andExpect(status().isOk());
    }

    @Test
    void protectedEndpointRejectsAnonymousUser() throws Exception {
        mockMvc.perform(get("/api/v1/private-resource")).andExpect(status().isForbidden());
    }
}
