/*
 * Main.java
 * ElGamal
 *
 * Created by Valerie Marissens Cueva on 29/1/2021.
 * Copyright (c) 2021.
 *
 */

import elgamal.Euclide;
import elgamal.ModularExponetiation;
import elgamal.exceptions.EuclideException;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Main {

    public static void main(String[] args) {
        String hexa = "FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE65381FFFFFFFFFFFFFFFF";
        BigInteger p = new BigInteger(hexa, 16);
        BigInteger g = BigInteger.valueOf(2);
        Euclide euclide = new Euclide();
        ModularExponetiation modularExponetiation = new ModularExponetiation();

        try {
            euclide.testTenThousandTimes(p);
            //modularExponetiation.testTenThousandTimes(p, g);
        } catch (NoSuchProviderException | NoSuchAlgorithmException | EuclideException e) {
            e.printStackTrace();
        }
    }
}
