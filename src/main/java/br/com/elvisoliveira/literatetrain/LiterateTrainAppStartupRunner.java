package br.com.elvisoliveira.literatetrain;

import br.com.elvisoliveira.literatetrain.Cache.UserCache;
import br.com.elvisoliveira.literatetrain.Model.UserEncrypted;
import br.com.elvisoliveira.literatetrain.Service.RSAKeyService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Component
public class LiterateTrainAppStartupRunner implements ApplicationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(LiterateTrainAppStartupRunner.class);

    @Resource(name = "UserCache")
    UserCache cacheUser;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RSAKeyService keyPairGenerator = new RSAKeyService();
        byte[] publicKey = keyPairGenerator.getPublicKey().getEncoded();
        byte[] privateKey = keyPairGenerator.getPrivateKey().getEncoded();

        String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey);

        UserEncrypted baseUser = new UserEncrypted();
        baseUser.setPublicKey(publicKey);
        baseUser.setName("Elvis Oliveira");
        baseUser.setEmail("elvis.olv@gmail.com");
        cacheUser.set(baseUser);

        File file = new File("postman_data.json");
        LOG.info("Config File: {}", file.getAbsolutePath());

        HashMap<String, String> key = new HashMap<>();
        key.put("public", baseUser.getPublicKey());
        key.put("private", encodedPrivateKey);

        List<HashMap<String, String>> keys = new ArrayList<>();
        keys.add(key);

        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Writer writer = new FileWriter(file.getAbsolutePath());
        gson.toJson(keys, writer);
        writer.close();
    }
}