/*
 * ModularExponetiationTest.java
 * ElGamal
 *
 * Created by Valerie Marissens Cueva on 29/1/2021.
 * Copyright (c) 2021.
 *
 */

package elgamal.tests;

import elgamal.ModularExponentiation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class ModularExponentiationTest {
    private ModularExponentiation m;

    @BeforeEach
    void setUp() throws IOException {
        m = new ModularExponentiation(new BufferedWriter(new FileWriter("test2.txt")));
    }

    @Test
    void expMod() {
        assertEquals(BigInteger.valueOf(16), m.expMod(BigInteger.valueOf(23), BigInteger.valueOf(5), BigInteger.valueOf(8)));
    }

    @Test
    void expModBigValue() {
        assertEquals(BigInteger.valueOf(66), m.expMod(BigInteger.valueOf(97), new BigInteger("989238492034823942399238492"), BigInteger.valueOf(47534)));
    }
}