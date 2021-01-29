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
import elgamal.exceptions.EuclideException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class EuclideTest {
    private Euclide euclide;

    @BeforeEach
    void setUp(){
        euclide = new Euclide();
    }

    @Test
    void extendedEuclidean(){
        BigInteger[] results = euclide.extendedEuclideanAlgorithm(BigInteger.valueOf(325), BigInteger.valueOf(145));
        assertEquals(BigInteger.valueOf(5), results[0]);
    }

    @Test
    void euclidePrime(){
        assertDoesNotThrow(() -> {
            BigInteger b = new BigInteger("179769313486231590770839156793787453197860296048756011706444423684197180216158519368947833795864925541502180565485980503646440548199239100050792877003355816639229553136239076508735759914822574862575007425302077447712589550957937778424442426617334727629299387668709205606050270810842907692932019128194467627007");
            euclide.euclide(BigInteger.valueOf(21523894), b);
        });
    }

    @Test
    void euclideNotPrime(){
        assertThrows(EuclideException.class, () -> euclide.euclide(BigInteger.valueOf(81), BigInteger.valueOf(9)));
    }

}