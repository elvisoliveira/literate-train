package br.com.elvisoliveira.literatetrain.Controller;

import br.com.elvisoliveira.literatetrain.Model.Keys;
import br.com.elvisoliveira.literatetrain.Service.RSAKeyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RestController
@RequestMapping(value = "/crypt")
public class CryptController {
    @GetMapping()
    public ResponseEntity<Keys> getRandomPrivateAndPublicKeys() {
        Keys feedback = new Keys();
        try {
            RSAKeyService keyPairGenerator = new RSAKeyService();
            byte[] publicKey = keyPairGenerator.getPublicKey().getEncoded();
            byte[] privateKey = keyPairGenerator.getPrivateKey().getEncoded();

            feedback.setPublicKey(Base64.getEncoder().encodeToString(publicKey));
            feedback.setPrivateKey(Base64.getEncoder().encodeToString(privateKey));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }
}