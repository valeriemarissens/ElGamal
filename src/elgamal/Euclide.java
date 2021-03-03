/*
 * Euclide.java
 * ElGamal
 *
 * Created by Valerie Marissens Cueva on 29/1/2021.
 * Copyright (c) 2021.
 *
 */

package elgamal;

import elgamal.exceptions.EuclideException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Implémentation de l'algorithme d'Euclide étendu et tests respectifs.
 */
public class Euclide {

    /**
     * Sert à écrire dans le fichier test.txt les résultats des tests.
     */
    private final BufferedWriter bufferedWriter;

    /**
     * Initialise le buffer.
     *
     * @param bufferedWriter qui sert à écrire.
     */
    public Euclide(BufferedWriter bufferedWriter){
        this.bufferedWriter  = bufferedWriter;
    }

    /**
     * Par l'algorithme d'Euclide étendu, retrouve les entiers u et v tels que
     * a.u + b.v = 1.
     *
     * @param a premier grand entier.
     * @param b deuxième grand entier.
     * @return un tableau de taille 2 contenant u et v de l'égalité de Bezout.
     * @throws EuclideException si pgcd(a, b) != 1.
     */
    public BigInteger[] euclide(BigInteger a, BigInteger b) throws EuclideException {
        BigInteger[] results = extendedEuclideanAlgorithm(a, b);

        if (results[0].equals(BigInteger.ONE)){
            return Arrays.copyOfRange(results, 1, 3);
        }
        else{
            throw new EuclideException("Le pgcd("+a+", "+b+") est différent de 1.");
        }
    }

    /**
     * @param a premier entier
     * @param b deuxième entier
     * @return tableau contenant pgcd(a, b), u et v.
     */
    public BigInteger[] extendedEuclideanAlgorithm(BigInteger a, BigInteger b){
        BigInteger[] u = new BigInteger[2];
        BigInteger[] v = new BigInteger[2];
        BigInteger[] r = new BigInteger[2];

        // Initialisation
        r[0] = a; r[1] = b;
        u[0] = BigInteger.valueOf(1);
        u[1] = BigInteger.valueOf(0);
        v[0] = BigInteger.valueOf(0);
        v[1] = BigInteger.valueOf(1);

        BigInteger rTemp, q, uTemp, vTemp;

        while (!r[1].equals(BigInteger.ZERO)){
            // q = r0 / r1
            q = r[0].divide(r[1]);

            // r = r0 % r1
            rTemp = r[0].remainder(r[1]);

            // u = u0 - q * u1
            uTemp = u[0].subtract(q.multiply(u[1]));

            // v = v0 - q * v1
            vTemp = v[0].subtract(q.multiply(v[1]));

            r[0] = r[1]; r[1] = rTemp;
            u[0] = u[1]; u[1] = uTemp;
            v[0] = v[1]; v[1] = vTemp;
        }

        BigInteger[] results = new BigInteger[3];
        // pgcd(a,b) :
        results[0] = r[0];

        // Coefficients u et v tq au + bv = pgcd(a,b) :
        results[1] = u[0];
        results[2] = v[0];

        return results;
    }

    /**
     * Réalise les 10 000 tests de la fonction euclide() avec le nombre p
     * et 10 000 valeurs différentes de a (tirées aléatoirement avec SecureRandom).
     *
     * @param p valeur du grand nombre premier p.
     * @throws NoSuchProviderException lancée quand un fournisseur de sécurité n'est pas
     * disponible.
     * @throws NoSuchAlgorithmException lancée quand un algorithme de cryptographie n'est
     * pas disponible.
     * @throws EuclideException lancée si le pgcd de a et p n'est pas égal à 1.
     */
    public void testTenThousandTimes(BigInteger p) throws NoSuchProviderException, NoSuchAlgorithmException, EuclideException {
        BigInteger a;
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

        System.out.println("Test de la fonction Euclide() : \n");
       try {
            bufferedWriter.write("Test de la fonction Euclide()  : \n");
            for (int i = 0; i < 10000; i++) {
                a = new BigInteger(1024, random);
                bufferedWriter.write("a = "+ a + "\t et \t");
                BigInteger[] results = euclide(a, p);
                bufferedWriter.write("a.u + p.v = " + (a.multiply(results[0])).add(p.multiply(results[1])) + "\n");

                if(i < 5){
                    System.out.println("a = "+ a);
                    System.out.println("a.u + p.v = " + (a.multiply(results[0])).add(p.multiply(results[1])) + "\n");
                }
            }
            System.out.println("L'ensemble des tests se trouvent dans le fichier test.txt \n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
