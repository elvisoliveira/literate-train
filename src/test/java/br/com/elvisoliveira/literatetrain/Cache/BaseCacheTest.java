package br.com.elvisoliveira.literatetrain.Cache;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.com.elvisoliveira.literatetrain.Model.SingleDigit;
import br.com.elvisoliveira.literatetrain.Model.User;

public class BaseCacheTest {

    private final String USER_EMAIL = "abc@xyz.com";
    private final String USER_NAME = "John Smith";
    private final String USER_PUBLIC_KEY = "Z3l1b2dvZ3ZraGdnaDlnaDByaHMwdGVqMDhydmgwdG9zaHZvaHY=";

    private BaseCache<User> baseCache;

    // Mocks
    private ArrayList<SingleDigit> singleDigits;
    private User user;

    @Before
    public void setUp() {
        this.baseCache = new BaseCache<User>();

        this.singleDigits = new ArrayList<SingleDigit>();
        this.singleDigits.add(new SingleDigit());

        user = new User();
        user.setEmail(USER_EMAIL);
        user.setName(USER_NAME);
        user.setPublicKey(USER_PUBLIC_KEY);
        user.setSingleDigit(this.singleDigits);
    }

    @Test
    public void setNewUser_toBaseCache_withSuccess() {
        assertNull(this.baseCache.set(new User()));
    }

    @Test
    public void unsetExistingUser_inBaseCache_withSuccess() {
        this.baseCache.set(new User());
        int baseCacheSize = this.baseCache.getAll().size();
        int id = this.baseCache.getAll().stream().findFirst().get().getId();

        this.baseCache.unset(id);
        assertEquals((baseCacheSize - 1), this.baseCache.getAll().size());
    }

    @Test
    public void updateUser_inBaseCache_withSuccess() {
        String newEmail = "new@email.com";
        this.baseCache.set(this.user);
        int id = this.baseCache.getAll().stream().findFirst().get().getId();

        this.user.setEmail(newEmail);
        this.baseCache.update(this.user);

        assertEquals(newEmail, this.baseCache.get(id).getEmail());
    }

    @Test
    public void getUser_inBaseCache_withSuccess() {
        this.baseCache.set(new User());
        int id = this.baseCache.getAll().stream().findFirst().get().getId();
        assertNotNull(this.baseCache.get(id));
    }

    @Test
    public void getAllUsers_inBaseCache_withSuccess() {
        this.baseCache.set(new User());
        assertTrue(this.baseCache.getAll().size() > 0);
    }
}