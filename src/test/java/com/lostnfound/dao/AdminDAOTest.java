package com.lostnfound.dao;

import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.Assert.*;

public class AdminDAOTest {

    @Test
    public void hashPasswordProducesBcryptHash() {
        String hash = AdminDAO.hashPassword("secret");
        assertTrue("Hash should start with BCrypt prefix", hash.startsWith("$2a$"));
    }

    @Test
    public void hashPasswordVerifiesCorrectly() {
        String plain = "myP@ssword123";
        String hash = AdminDAO.hashPassword(plain);
        assertTrue("BCrypt.checkpw should accept the original password",
                BCrypt.checkpw(plain, hash));
    }

    @Test
    public void hashPasswordRejectsWrongPassword() {
        String hash = AdminDAO.hashPassword("correct");
        assertFalse("BCrypt.checkpw should reject a wrong password",
                BCrypt.checkpw("wrong", hash));
    }

    @Test
    public void twoHashesForSameInputDiffer() {
        // BCrypt generates a fresh random salt each time
        String h1 = AdminDAO.hashPassword("same");
        String h2 = AdminDAO.hashPassword("same");
        assertNotEquals("Two BCrypt hashes of the same input must differ (random salt)", h1, h2);
    }
}
