package com.nguyencongnhat.fincore.system.api;

import com.nguyencongnhat.fincore.system.application.SystemInfoView;
import com.nguyencongnhat.fincore.system.application.port.in.SystemInfoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/system")
public class SystemInfoController {

    private final SystemInfoUseCase systemInfoUseCase;

    public SystemInfoController(SystemInfoUseCase systemInfoUseCase) {
        this.systemInfoUseCase = systemInfoUseCase;
    }

    @GetMapping("/info")
    public ResponseEntity<SystemInfoResponse> getSystemInfo() {
        SystemInfoView view = systemInfoUseCase.getSystemInfo();
        return ResponseEntity.ok(
                new SystemInfoResponse(
                        view.name(), view.version(), view.status(), view.environment()));
    }
}
