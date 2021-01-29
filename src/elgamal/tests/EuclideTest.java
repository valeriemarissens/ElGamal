/*
 * EuclideTest.java
 * ElGamal
 *
 * Created by Valerie Marissens Cueva on 29/1/2021.
 * Copyright (c) 2021.
 *
 */

package elgamal.tests;

import elgamal.Euclide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EuclideTest {
    private Euclide euclide;

    @BeforeEach
    void setUp(){
        euclide = new Euclide();
    }

    @Test
    void extendedEuclidean(){
        assertEquals(5, euclide.extendedEuclideanAlgorithm(325, 145));
    }

    @Test
    void extendedEuclideanPrime(){
        assertEquals(1, euclide.extendedEuclideanAlgorithm(81, 11));
    }

}