package com.nguyencongnhat.fincore.shared.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Currency;
import org.junit.jupiter.api.Test;

class MoneyTest {

    private static final Currency USD = Currency.getInstance("USD");
    private static final Currency VND = Currency.getInstance("VND");

    @Test
    void addsAmountsWithSameCurrency() {
        assertThat(new Money(125, USD).add(new Money(75, USD))).isEqualTo(new Money(200, USD));
    }

    @Test
    void subtractsAmountsWithSameCurrency() {
        assertThat(new Money(125, USD).subtract(new Money(75, USD))).isEqualTo(new Money(50, USD));
    }

    @Test
    void rejectsDifferentCurrencies() {
        Money dollars = new Money(100, USD);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> dollars.add(new Money(100, VND)))
                .withMessage("money currencies must match");
    }

    @Test
    void rejectsNullCurrency() {
        assertThatNullPointerException()
                .isThrownBy(() -> new Money(100, null))
                .withMessage("currency must not be null");
    }

    @Test
    void createsZeroAmount() {
        assertThat(Money.zero(USD)).isEqualTo(new Money(0, USD));
        assertThat(Money.zero(USD).isZero()).isTrue();
    }

    @Test
    void exposesAmountSign() {
        assertThat(new Money(1, USD).isPositive()).isTrue();
        assertThat(new Money(-1, USD).isNegative()).isTrue();
        assertThat(new Money(0, USD).isPositive()).isFalse();
    }

    @Test
    void detectsArithmeticOverflow() {
        assertThatThrownBy(() -> new Money(Long.MAX_VALUE, USD).add(new Money(1, USD)))
                .isInstanceOf(ArithmeticException.class);
    }
}
