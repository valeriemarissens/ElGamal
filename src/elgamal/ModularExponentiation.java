/*
 * ModularExponetiation.java
 * ElGamal
 *
 * Created by Valerie Marissens Cueva on 29/1/2021.
 * Copyright (c) 2021.
 *
 */

package elgamal;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModularExponentiation {

    public ModularExponentiation(){}

    /**
     *
     * @param p entier
     * @param g entier
     * @param a puissance
     * @return A = g**a mod p
     */
    public BigInteger expMod(BigInteger p, BigInteger g, int a){
        BigInteger result = BigInteger.ONE;
        g = g.mod(p);

        if(g.intValue() == 0){
            return BigInteger.ZERO;
        }

        while(a > 0){
            if((a & 1) != 0)    // Si a est impair
                result = (result.multiply(g)).mod(p);

            a = a >> 1;     // a = a/2
            g = (g.multiply(g)).mod(p);
        }
        return result;

        /*
        if (a == 1)
            return g;
        else{

            // a est pair.
            if (a % 2 == 0) {
                // pow(g**2, a/2)
                return expMod(p, g.pow(2).mod(p), a / 2);
            }

            // a est impair.
            else {
                // g * pow(g**2, (a-1)/2)
                return g.multiply(expMod(p, g.pow(2).mod(p), (a - 1) / 2)).mod(p);
            }
        }
         */
    }

    public void testTenThousandTimes(BigInteger p, BigInteger g) throws NoSuchProviderException, NoSuchAlgorithmException {
        int a;
        for (int i = 0; i < 10000; i++){
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            a = random.nextInt();

            BigInteger result = expMod(p, g, Math.abs(a));
            //if (i<10)
                System.out.println(result);

            // todo : c'est bien ce test ?
            //assertEquals(g.pow(a.intValue()).mod(p), result);
            //test => g.pow(a).mod(p);
        }
    }
}
