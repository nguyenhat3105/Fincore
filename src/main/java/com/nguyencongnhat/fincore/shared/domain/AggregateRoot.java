package com.nguyencongnhat.fincore.shared.domain;

public interface AggregateRoot<ID extends Identifier<?>> {

    ID id();
}
