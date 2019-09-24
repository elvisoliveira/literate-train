package br.com.elvisoliveira.literatetrain.Controller;

import br.com.elvisoliveira.literatetrain.Model.KeysScoped;
import br.com.elvisoliveira.literatetrain.Cache.SingleDigitCache;
import br.com.elvisoliveira.literatetrain.Cache.UserCache;
import br.com.elvisoliveira.literatetrain.LiterateTrainAppInterceptor;
import br.com.elvisoliveira.literatetrain.Model.SingleDigit;
import br.com.elvisoliveira.literatetrain.Model.UserEncrypted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/calc")
public class CalcController {
    @Resource(name = "SingleDigitCache")
    SingleDigitCache singleDigitCache;

    @Autowired
    private KeysScoped cacheKeys;

    @Resource(name = "UserCache")
    UserCache cacheUser;

    @PostMapping()
    public ResponseEntity<SingleDigit> addSingleDigit(@Valid @RequestBody SingleDigit entity) {
        SingleDigit singleDigit = singleDigitCache.set(entity);
        if (cacheKeys.getPublicKey() != null) {
            UserEncrypted user = LiterateTrainAppInterceptor.getUserByPublicKey(cacheUser, cacheKeys);
            user.addSingleDigit(singleDigit);
        }
        return new ResponseEntity<>(singleDigit, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ArrayList<SingleDigit>> getSingleDigits() {
        return new ResponseEntity<>(singleDigitCache.getAll(), new HttpHeaders(), HttpStatus.OK);
    }
}