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
        Euclide euclide = new Euclide();
        ModularExponentiation modularExponentiation = new ModularExponentiation();

        BigInteger a = BigInteger.valueOf(2);
        BigInteger b = BigInteger.valueOf(5);
        BigInteger c = BigInteger.valueOf(3);

        //System.out.println(modularExponentiation.expMod(c,b,3));

        //try {
            //euclide.testTenThousandTimes(p);
            //modularExponetiation.testTenThousandTimes(p, g);
        //} catch (NoSuchProviderException | NoSuchAlgorithmException | EuclideException e) {
        //    e.printStackTrace();
        //}

        ElGamal elGamal = new ElGamal();



        BigInteger[] keys = elGamal.KeyGen(p,g);
        BigInteger bobPrivateKey = keys[0];
        BigInteger bobPublicKey = keys[1];

        //System.out.println("La clé privée de Bob est : " + bobPrivateKey);
        //System.out.println("La clé publique de Bob est : " + bobPublicKey + "\n");

        BigInteger message = BigInteger.valueOf(123456789);
        System.out.println("Le message est : " + message.intValue());

        BigInteger[] encrypt = elGamal.Encrypt(p,g, bobPublicKey, message);
        BigInteger messageChiffreC = encrypt[0];
        BigInteger messageChiffreB = encrypt[1];

        System.out.println("Le message chiffré est : \n" );
        System.out.println("C  : " + messageChiffreC.intValue());
        System.out.println("B  : " + messageChiffreB.intValue());

        message = elGamal.Decrypt(messageChiffreC,messageChiffreB, bobPrivateKey.intValue(), p);
        System.out.println("Le message déchiffré est : " + message.intValue());


        System.out.println("\n\n\n");
        euclide.testTenThousandTimes(p);

    }
}
