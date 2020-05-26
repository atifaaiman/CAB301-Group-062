<<<<<<< HEAD:BillboardServer/src/common/Hash.java
package common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    /**
     * Hashes password.
     *
     * @param password the password
     * @return the string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(password.getBytes());
        return String.format("%032X", new BigInteger(1, hash));
    }

    public static void main(String[] Args) throws NoSuchAlgorithmException {
        System.out.println(hashPassword("admin"));
        System.out.println(hashPassword("admin"));
    }
}
=======
package utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    /**
     * Hashes password.
     *
     * @param password the password
     * @return the string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(password.getBytes());
        return String.format("%032X", new BigInteger(1, hash));
    }
}
>>>>>>> 54540e4fad4024e5412ffe141953d140f5015be4:BillboardServer/src/utilities/Hash.java
