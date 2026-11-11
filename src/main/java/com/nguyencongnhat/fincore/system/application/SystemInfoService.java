package com.nguyencongnhat.fincore.system.application;

import com.nguyencongnhat.fincore.system.application.port.in.SystemInfoUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class SystemInfoService implements SystemInfoUseCase {

    private final String applicationName;
    private final String applicationVersion;
    private final Environment environment;

    public SystemInfoService(
            @Value("${spring.application.name}") String applicationName,
            @Value("${info.app.version}") String applicationVersion,
            Environment environment) {
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
        this.environment = environment;
    }

    @Override
    public SystemInfoView getSystemInfo() {
        String activeEnvironment =
                environment.getActiveProfiles().length == 0
                        ? environment.getDefaultProfiles()[0]
                        : environment.getActiveProfiles()[0];
        return new SystemInfoView(applicationName, applicationVersion, "UP", activeEnvironment);
    }
}
