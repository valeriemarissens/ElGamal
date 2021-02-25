/*
 * ElGamal.java
 * ElGamal
 *
 * Created by Valerie Marissens Cueva on 29/1/2021.
 * Copyright (c) 2021.
 *
 */

package elgamal;

import elgamal.exceptions.EuclideException;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class ElGamal {

    private ModularExponentiation modularExponentiation;
    private Euclide euclide;

    public ElGamal(){
        modularExponentiation = new ModularExponentiation();
        euclide = new Euclide();
    }

    public BigInteger[] KeyGen(BigInteger p, BigInteger g) throws NoSuchProviderException, NoSuchAlgorithmException {

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        int x = Math.abs(random.nextInt());
        // int x = Math.abs(random.nextInt((p.intValue() - 2)) - 2 + 1) + 2;

        BigInteger privateKey = BigInteger.valueOf(x);
        BigInteger publicKey = modularExponentiation.expMod(p,g,x);


        BigInteger[] results = new BigInteger[2];
        results[0] = privateKey;
        results[1] = publicKey;

        return results;
    }

    public BigInteger[] Encrypt(BigInteger p , BigInteger g, BigInteger publicKey, BigInteger m) throws NoSuchProviderException, NoSuchAlgorithmException {

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        //int r = Math.abs(random.nextInt((p.intValue() - 2)) - 2 + 1) + 2;
        int r = Math.abs(random.nextInt());
        BigInteger y = modularExponentiation.expMod(p, publicKey, r);


        BigInteger C = (m.multiply(y)).mod(p);
        BigInteger B = modularExponentiation.expMod(p,g, r);

        BigInteger[] results = new BigInteger[2];
        results[0] = C;
        results[1] = B;

        return results;
    }

    public BigInteger Decrypt(BigInteger C, BigInteger B, int secretKey, BigInteger p) throws EuclideException {

        BigInteger D = modularExponentiation.expMod(p,B,secretKey);
        BigInteger m = (C.multiply(euclide.euclide(D,p)[0])).mod(p);

        return m;
    }


}
