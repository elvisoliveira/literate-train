package br.com.elvisoliveira.literatetrain.Service;

import br.com.elvisoliveira.literatetrain.Model.KeysScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
public class DecryptService {
    @Autowired
    private KeysScoped cacheKeys;

    public String decrypt(String data) {
        String decryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKeyFromEncodedString(cacheKeys.getPrivateKey()));
            decryptedData = new String(cipher.doFinal(Base64.getDecoder().decode(data.getBytes())));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return decryptedData;
    }

    private PrivateKey getPrivateKeyFromEncodedString(String base64PrivateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytesOfData = base64PrivateKey.getBytes();
        byte[] bytesOfDataDecoded = Base64.getDecoder().decode(bytesOfData);
        return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bytesOfDataDecoded));
    }
}