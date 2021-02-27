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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

        System.out.println("Test de la fonction expMod() : \n");
        try {
            File file = new File("test.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Test de la fonction expMod()  : \n");
            for (int i = 0; i < 10000; i++) {
                a = Math.abs(random.nextInt());
                bufferedWriter.write("a = "+ a + "     et      ");
                bufferedWriter.write("expMod(p, g, a) = " + expMod(p, g, a) + "\n");

                if(i < 5){
                    System.out.println("a = "+ a);
                    System.out.println("expMod(p, g, a) = " + expMod(p, g, a) + "\n");
                }
            }
            System.out.println("L'ensemble des tests se trouvent dans le fichier test.txt \n");

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
