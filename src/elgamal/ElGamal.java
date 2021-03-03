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
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * Implémentation du chiffrement El Gamal.
 */
public class ElGamal {

    private final BufferedWriter bufferedWriter;
    private final ModularExponentiation modularExponentiation;
    private final Euclide euclide;

    /**
     * Initialisation du buffer et des outils d'Euclide et d'exponentiation
     * modulaire.
     *
     * @param bufferedWriter pour écrire dans test.txt.
     */
    public ElGamal(BufferedWriter bufferedWriter){
        this.bufferedWriter = bufferedWriter;
        modularExponentiation = new ModularExponentiation(bufferedWriter);
        euclide = new Euclide(bufferedWriter);
    }

    /**
     * Génération des clés.
     *
     * @param p valeur du grand nombre premier p.
     * @param g valeur du grand nombre premier g.
     * @return un tableau de taille 2 contenant la clé privée et la clé publique.
     * @throws NoSuchProviderException lancée quand un fournisseur de sécurité n'est pas
     * disponible.
     * @throws NoSuchAlgorithmException lancée quand un algorithme de cryptographie n'est
     * pas disponible.
     */
    public BigInteger[] KeyGen(BigInteger p, BigInteger g) throws NoSuchProviderException, NoSuchAlgorithmException {

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

        BigInteger privateKey = new BigInteger(1024, random);
        BigInteger publicKey = modularExponentiation.expMod(p,g,privateKey);

        BigInteger[] results = new BigInteger[2];
        results[0] = privateKey;
        results[1] = publicKey;

        return results;
    }

    /**
     * Chiffrement du message.
     *
     * @param p valeur du grand nombre premier p.
     * @param g valeur du grand nombre premier g.
     * @param publicKey clé publique.
     * @param m message à chiffrer.
     * @return un tableau de taille 2 contenant le couple chiffré C et B.
     * @throws NoSuchProviderException lancée quand un fournisseur de sécurité n'est pas
     * disponible.
     * @throws NoSuchAlgorithmException lancée quand un algorithme de cryptographie n'est
     * pas disponible.
     */
    public BigInteger[] Encrypt(BigInteger p , BigInteger g, BigInteger publicKey, BigInteger m) throws NoSuchProviderException, NoSuchAlgorithmException {

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        BigInteger r = new BigInteger(1024, random);

        BigInteger y = modularExponentiation.expMod(p, publicKey, r);

        BigInteger C = (m.multiply(y)).mod(p);
        BigInteger B = modularExponentiation.expMod(p,g,r);

        BigInteger[] results = new BigInteger[2];
        results[0] = C;
        results[1] = B;

        return results;
    }

    /**
     * Déchiffrement du chiffré c.
     *
     * @param C chiffré
     * @param B g**r mod p
     * @param secretKey clé privée
     * @param p valeur du grand nombre premier p.
     * @return le message m.
     * @throws EuclideException si le pgcd de a et p n'est pas égal à 1.
     */
    public BigInteger Decrypt(@NotNull BigInteger C, BigInteger B, BigInteger secretKey, BigInteger p) throws EuclideException {

        BigInteger D = modularExponentiation.expMod(p,B,secretKey);

        return (C.multiply(euclide.euclide(D,p)[0])).mod(p);
    }

    /**
     * Réalise 100 tests avec 100 messages différents et vérifie que les déchiffrés
     * correspondent bien au message initial.
     *
     * @param p valeur du grand nombre premier p.
     * @param g valeur du grand nombre premier p.
     * @throws NoSuchProviderException lancée quand un fournisseur de sécurité n'est pas
     * disponible.
     * @throws NoSuchAlgorithmException lancée quand un algorithme de cryptographie n'est
     * pas disponible.
     * @throws EuclideException lancée si le pgcd de a et p n'est pas égal à 1.
     */
    public void testThousandTimes(BigInteger p, BigInteger g) throws NoSuchProviderException, NoSuchAlgorithmException, EuclideException {
        BigInteger message;
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

        System.out.println("Test du chiffrement ElGamal : \n");
        try {
            bufferedWriter.write("Test du chiffrement ElGamal  : \n");
            for (int i = 0; i < 100; i++) {
                message = BigInteger.valueOf(Math.abs(random.nextInt()));
                BigInteger[] keys = KeyGen(p,g);
                BigInteger bobPrivateKey = keys[0];
                BigInteger bobPublicKey = keys[1];

                BigInteger[] encrypt = Encrypt(p,g, bobPublicKey, message);
                BigInteger messageChiffreC = encrypt[0];
                BigInteger messageChiffreB = encrypt[1];

                bufferedWriter.write("Le message est : " + message.intValue() + "\n");
                bufferedWriter.write("Le message chiffré est : C  : " + messageChiffreC.intValue() +  "   -   et B  : " + messageChiffreB.intValue() + "\n");
                message = Decrypt(messageChiffreC,messageChiffreB, bobPrivateKey, p);
                bufferedWriter.write("Le message déchiffré est : " + message.intValue() + "\n\n");

                if(i < 5){
                    System.out.println("Le message est : " + message.intValue());
                    System.out.println("Le message chiffré est :  C  = " + messageChiffreC.intValue() +  "      et B  = " + messageChiffreB.intValue());
                    message = Decrypt(messageChiffreC,messageChiffreB, bobPrivateKey, p);
                    System.out.println("Le message déchiffré est : " + message.intValue() + "\n");
                }

            }
            System.out.println("L'ensemble des tests se trouvent dans le fichier test.txt \n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Réalise le test de la propriété homomorphique du chiffrement El Gamal.
     *
     * @param p valeur du grand nombre premier p.
     * @param g valeur du grand nombre premier p.
     * @throws NoSuchProviderException lancée quand un fournisseur de sécurité n'est pas
     * disponible.
     * @throws NoSuchAlgorithmException lancée quand un algorithme de cryptographie n'est
     * pas disponible.
     * @throws EuclideException lancée si le pgcd de a et p n'est pas égal à 1.
     */
    public void testHomomorphie(BigInteger p, BigInteger g) throws NoSuchProviderException, NoSuchAlgorithmException, EuclideException {
        BigInteger message1, message2;
        BigInteger[] chiffre1, chiffre2;

        //Création des clés
        BigInteger[] keys = KeyGen(p,g);
        BigInteger bobPrivateKey = keys[0];
        BigInteger bobPublicKey = keys[1];

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        message1 = BigInteger.valueOf(Math.abs(random.nextInt()));
        message2 = BigInteger.valueOf(Math.abs(random.nextInt()));
        System.out.println("m1 = " + message1);
        System.out.println("m2 = " + message2);

        chiffre1 = Encrypt(p,g, bobPublicKey, message1);
        chiffre2 = Encrypt(p,g, bobPublicKey, message2);

        BigInteger C = (chiffre1[0].multiply(chiffre2[0])).mod(p);
        BigInteger B = (chiffre1[1].multiply(chiffre2[1])).mod(p);

        BigInteger messageTotal = Decrypt(C,B, bobPrivateKey, p);
        System.out.println("Le déchiffrement de C et B donne : " + messageTotal);

        System.out.println("Et on a : (m1 * m2).mod(p) = " + (message1.multiply(message2)).mod(p));
        System.out.println("\nLa propriété homomorphique du chiffrement d'ElGamal est vérifiée");
    }

}
