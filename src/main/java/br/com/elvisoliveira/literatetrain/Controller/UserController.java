package br.com.elvisoliveira.literatetrain.Controller;

import br.com.elvisoliveira.literatetrain.Cache.UserCache;
import br.com.elvisoliveira.literatetrain.Model.KeysScoped;
import br.com.elvisoliveira.literatetrain.Model.User;
import br.com.elvisoliveira.literatetrain.Model.UserEncrypted;
import br.com.elvisoliveira.literatetrain.Service.DecryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Resource(name = "UserCache")
    UserCache cacheUser;

    @Autowired
    private KeysScoped cacheKeys;

    @Autowired
    private DecryptService cryptService;

    @PostMapping()
    public ResponseEntity<UserEncrypted> addUser(@Valid @RequestBody UserEncrypted entity) {
        cacheUser.set(entity);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<UserEncrypted> patchUser(@RequestBody Map<String, String> entity, @RequestParam Integer id) {
        UserEncrypted user = cacheUser.get(id);
        if (!validateKey(user)) {
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.FORBIDDEN);
        }
        user = updateUser(entity, user);
        cacheUser.update(user);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    private UserEncrypted updateUser(Map<String, String> entity, UserEncrypted user) {
        String[] fields = {"name", "email"};
        for (String field : fields) {
            String value = entity.get(field);
            if (value != null) {
                switch (field) {
                    case "name":
                        user.setName(value);
                        break;
                    case "email":
                        user.setEmail(value);
                        break;
                }
            }
        }
        return user;
    }

    @DeleteMapping()
    public ResponseEntity<UserEncrypted> deleteUser(@RequestParam Integer id) {
        UserEncrypted user = cacheUser.get(id);
        if (!validateKey(user)) {
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.FORBIDDEN);
        }
        cacheUser.unset(id);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ArrayList<User>> retrieveUsers(@RequestParam(required = false) Integer id) {
        ArrayList<User> feedback = new ArrayList<>();
        if (id != null) {
            UserEncrypted user = cacheUser.get(id);
            feedback.add(getUserHashMap(user));
        } else {
            for (UserEncrypted entity : cacheUser.getAll()) {
                if(entity.getPublicKey().equals(cacheKeys.getPublicKey())) {
                    feedback.add(getUserHashMap(entity));
                }
            }
        }
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }


    @GetMapping("all")
    public ResponseEntity<ArrayList<User>> retrieveUsers() {
        ArrayList<User> feedback = new ArrayList<>();
        for (UserEncrypted entity : cacheUser.getAll()) {
            feedback.add(getUserHashMap(entity));
        }
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }

    private Boolean validateKey(UserEncrypted user) {
        if (user.getPublicKey().equals(cacheKeys.getPublicKey())) {
            return true;
        }
        return false;
    }

    private User getUserHashMap(UserEncrypted entity) {
        return new User() {{
            setId(entity.getId());
            setPublicKey(entity.getPublicKey());
            setEmail(decryptIfNeeded(entity.getEmail(), entity.getPublicKey()));
            setName(decryptIfNeeded(entity.getName(), entity.getPublicKey()));
            setSingleDigit(entity.getSingleDigit());
        }};
    }

    private String decryptIfNeeded(String value, String publicKey) {
        if (publicKey.equals(cacheKeys.getPublicKey())) {
            return cryptService.decrypt(value);
        }
        return value;
    }
}