package com.nguyencongnhat.fincore.shared.domain;

import java.io.Serializable;

public interface Identifier<T extends Serializable> {

    T value();
}
