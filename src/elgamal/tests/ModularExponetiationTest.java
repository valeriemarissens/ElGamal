/*
 * ModularExponetiationTest.java
 * ElGamal
 *
 * Created by Valerie Marissens Cueva on 29/1/2021.
 * Copyright (c) 2021.
 *
 */

package elgamal.tests;

import elgamal.ModularExponetiation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class ModularExponetiationTest {
    private ModularExponetiation m;

    @BeforeEach
    void setUp() {
        m = new ModularExponetiation();
    }

    @Test
    void expMod() {
        assertEquals(BigInteger.valueOf(16), m.expMod(BigInteger.valueOf(23), BigInteger.valueOf(5), 8));
    }

    @Test
    void expModBigValue() {
        assertEquals(BigInteger.valueOf(66), m.expMod(BigInteger.valueOf(97), new BigInteger("989238492034823942399238492"), 47534));
    }
}