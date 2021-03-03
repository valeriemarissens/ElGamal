/*
 * ModularExponetiation.java
 * ElGamal
 *
 * Created by Valerie Marissens Cueva on 29/1/2021.
 * Copyright (c) 2021.
 *
 */

package elgamal;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * Implémentation de l'exponentiation modulaire et tests respectifs.
 */
public class ModularExponentiation {

    /**
     * Sert à écrire dans le fichier test.txt les résultats des tests.
     */
    private BufferedWriter bufferedWriter;

    /**
     * Initialise le buffer.
     *
     * @param bufferedWriter qui sert à écrire.
     */
    public ModularExponentiation(BufferedWriter bufferedWriter){
        this.bufferedWriter = bufferedWriter;
    }

    /**
     * Implémentation de l'exponentiation modulaire à l'aide de la méthode dite
     * d'exponentiation binaire.
     *
     * @param p entier
     * @param g entier
     * @param a puissance
     * @return A = g**a mod p
     */
    public BigInteger expMod(BigInteger p, BigInteger g, BigInteger a){
        BigInteger deux = BigInteger.valueOf(2);
        BigInteger result = BigInteger.ONE;
        g = g.mod(p);

        if(g.intValue() == 0){
            return BigInteger.ZERO;
        }

        while(a.compareTo(BigInteger.ZERO) > 0){    //tant que a > 0
            if(!a.and(BigInteger.ONE).equals(BigInteger.ZERO))    // Si a est impair
                result = (result.multiply(g)).mod(p);

            a = a.divide(deux);     // a = a/2
            g = (g.multiply(g)).mod(p);
        }

        return result;
    }

    /**
     * Réalise 10 000 tests de la fonction expMod() avec la valeur de g donnée (2) et
     * 10 000 valeurs différentes de a (tirées aléatoirement avec SecureRandom).
     *
     * @param p valeur du grand nombre premier p.
     * @param g valeur du grand nombre premier g.
     * @throws NoSuchProviderException lancée quand un fournisseur de sécurité n'est pas
     * disponible.
     * @throws NoSuchAlgorithmException lancée quand un algorithme de cryptographie n'est
     * pas disponible.
     */
    public void testTenThousandTimes(BigInteger p, BigInteger g) throws NoSuchProviderException, NoSuchAlgorithmException {

        BigInteger a;
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

        System.out.println("Test de la fonction expMod() : \n");
        try {
            bufferedWriter.write("Test de la fonction expMod()  : \n");
            for (int i = 0; i < 10000; i++) {
                a = new BigInteger(500, random);
                bufferedWriter.write("a = "+ a + "\t et \t");
                bufferedWriter.write("expMod(p, g, a) = " + expMod(p, g, a) + "\n");

                if(i < 5){
                    System.out.println("a = "+ a);
                    System.out.println("expMod(p, g, a) = " + expMod(p, g, a) + "\n");
                }
            }
            System.out.println("L'ensemble des tests se trouvent dans le fichier test.txt \n");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
