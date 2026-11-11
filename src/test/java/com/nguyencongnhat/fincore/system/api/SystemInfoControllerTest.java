package com.nguyencongnhat.fincore.system.api;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nguyencongnhat.fincore.shared.config.SecurityConfig;
import com.nguyencongnhat.fincore.system.application.SystemInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(SystemInfoController.class)
@Import({SystemInfoService.class, SecurityConfig.class})
class SystemInfoControllerTest {

    @Autowired private MockMvc mockMvc;

    @Test
    void returnsSystemInformation() throws Exception {
        mockMvc.perform(get("/api/v1/system/info"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("fincore"))
                .andExpect(jsonPath("$.version").value("0.1.0"))
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.environment").value("test"));
    }

    @Test
    @WithMockUser
    void doesNotExposeSensitiveConfiguration() throws Exception {
        mockMvc.perform(get("/api/v1/system/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(hasKey("password"))))
                .andExpect(jsonPath("$", not(hasKey("databaseUrl"))));
    }
}
