package br.com.elvisoliveira.literatetrain.Service;

import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class RSAKeyService {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public RSAKeyService() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public static String convertPrivateKeyInPublic(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateCrtKey privateCrtKey = (RSAPrivateCrtKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(token)));
        byte[] publicKeyBytes = keyFactory.generatePublic(new RSAPublicKeySpec(privateCrtKey.getModulus(), privateCrtKey.getPublicExponent())).getEncoded();
        return Base64.getEncoder().encodeToString(publicKeyBytes);
    }
}