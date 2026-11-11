package com.nguyencongnhat.fincore.shared.domain;

import java.util.Currency;
import java.util.Objects;

/** An immutable monetary value stored in ISO 4217 minor units. */
public record Money(long amount, Currency currency) {

    public Money {
        Objects.requireNonNull(currency, "currency must not be null");
    }

    public static Money zero(Currency currency) {
        return new Money(0, currency);
    }

    public Money add(Money other) {
        requireSameCurrency(other);
        return new Money(Math.addExact(amount, other.amount), currency);
    }

    public Money subtract(Money other) {
        requireSameCurrency(other);
        return new Money(Math.subtractExact(amount, other.amount), currency);
    }

    public Money negate() {
        return new Money(Math.negateExact(amount), currency);
    }

    public boolean isPositive() {
        return amount > 0;
    }

    public boolean isNegative() {
        return amount < 0;
    }

    public boolean isZero() {
        return amount == 0;
    }

    private void requireSameCurrency(Money other) {
        Objects.requireNonNull(other, "money must not be null");
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("money currencies must match");
        }
    }
}
