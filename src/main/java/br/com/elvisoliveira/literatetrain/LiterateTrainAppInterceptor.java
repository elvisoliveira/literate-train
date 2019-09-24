package br.com.elvisoliveira.literatetrain;

import br.com.elvisoliveira.literatetrain.Model.KeysScoped;
import br.com.elvisoliveira.literatetrain.Cache.UserCache;
import br.com.elvisoliveira.literatetrain.Model.UserEncrypted;
import br.com.elvisoliveira.literatetrain.Service.RSAKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

@Component
public class LiterateTrainAppInterceptor implements HandlerInterceptor {

    private static final String BEARER = "Bearer";

    @Resource(name = "UserCache")
    UserCache cacheUser;

    @Autowired
    private KeysScoped cacheKeys;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        // Get the private key from header info (Bearer Token)
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith(BEARER)) {
            // Generate an Public key from this private key (https://stackoverflow.com/questions/8434428/get-public-key-from-private-in-java)
            String privateKey = authorization.substring(BEARER.length()).trim();
            String publicKey = RSAKeyService.convertPrivateKeyInPublic(privateKey);
            // Set keys as request scoped data
            cacheKeys.setPrivateKey(privateKey);
            cacheKeys.setPublicKey(publicKey);
            // Use the public key to validate bearer token
            if(getUserByPublicKey(cacheUser, cacheKeys) != null) {
                return true;
            }
        }
        // Calc controller bypasses authentication
        if(Arrays.asList("/", "/calc", "/error", "/user.mustache", "/crypt").contains(request.getRequestURI())) {
            return true;
        }
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }

    public static UserEncrypted getUserByPublicKey(UserCache cacheUser, KeysScoped cacheKeys) {
        for (UserEncrypted selectedUser : cacheUser.getAll()) {
            String publicKey = cacheKeys.getPublicKey();
            if (selectedUser.getPublicKey().equals(publicKey)) {
                return selectedUser;
            }
        }
        return null;
    }
}