package br.com.elvisoliveira.literatetrain.Model;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class UserEncrypted extends User {
    public void setEmail(String email) {
        this.email = encrypt(email);
    }

    public void setName(String name) {
        this.name = encrypt(name);
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = Base64.getEncoder().encodeToString(publicKey);
    }

    private String encrypt(String data) {
        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(getPublicKey().getBytes()))));
            encryptedData = cipher.doFinal(data.getBytes());
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(encryptedData);
    }
}