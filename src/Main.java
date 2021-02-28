/*
 * Main.java
 * ElGamal
 *
 * Created by Valerie Marissens Cueva on 29/1/2021.
 * Copyright (c) 2021.
 *
 */

import elgamal.ElGamal;
import elgamal.Euclide;
import elgamal.ModularExponentiation;
import elgamal.exceptions.EuclideException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws NoSuchProviderException, NoSuchAlgorithmException, EuclideException {
        String hexa = "FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE65381FFFFFFFFFFFFFFFF";
        BigInteger p = new BigInteger(hexa, 16);
        BigInteger g = BigInteger.valueOf(2);

        try {
            File file = new File("test.txt");

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            Euclide euclide = new Euclide(bufferedWriter);
            ModularExponentiation modularExponentiation = new ModularExponentiation(bufferedWriter);
            ElGamal elGamal = new ElGamal(bufferedWriter);


            euclide.testTenThousandTimes(p);
            modularExponentiation.testTenThousandTimes(p,g);
            elGamal.testThousandTimes(p,g);

            elGamal.testHomomorphie(p,g);

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }





    }
}
