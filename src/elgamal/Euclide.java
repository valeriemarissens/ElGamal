/*
 * Euclide.java
 * ElGamal
 *
 * Created by Valerie Marissens Cueva on 29/1/2021.
 * Copyright (c) 2021.
 *
 */

package elgamal;

import sun.security.provider.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Euclide {

    public Euclide(){}

    /**
     * @param a premier entier
     * @param b deuxième entier
     * @return pgcd(a, b)
     */
    public int extendedEuclideanAlgorithm(int a, int b){ // todo : renvoie u et v
        int[] u = new int[2];
        int[] v = new int[2];
        int[] r = new int[2];

        // Initialisation
        r[0] = a; r[1] = b;
        u[0] = 1;
        v[1] = 1;

        int rTemp, q, uTemp, vTemp;

        while (r[1] > 0){
            q = r[0] / r[1];
            rTemp = r[0] % r[1];
            uTemp = u[0] - q*u[1];
            vTemp = v[0] - q*v[1];

            r[0] = r[1]; r[1] = rTemp;
            u[0] = u[1]; u[1] = uTemp;
            v[0] = v[1]; v[1] = vTemp;
        }

        return r[0];
    }

    public void testThousandTimes(int p){ // todo mettre dans fichier test ?
        int a = 0;//todo changer!
        for (int i = 0; i < 1000; i++){
            SecureRandom random = new SecureRandom();
            /*a = random.engineGenerateSeed();*/
            assertEquals(1, extendedEuclideanAlgorithm(a, p));
        }
    }
}
