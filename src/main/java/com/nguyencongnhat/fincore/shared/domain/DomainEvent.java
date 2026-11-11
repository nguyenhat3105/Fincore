package com.nguyencongnhat.fincore.shared.domain;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {

    UUID eventId();

    Instant occurredAt();
}
