package br.com.elvisoliveira.literatetrain.Controller;

import br.com.elvisoliveira.literatetrain.Cache.SingleDigitCache;
import br.com.elvisoliveira.literatetrain.Model.KeysScoped;
import br.com.elvisoliveira.literatetrain.Model.SingleDigit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CalcControllerTest {
    @Mock
    private SingleDigitCache singleDigitCache;

    @Mock
    private KeysScoped cacheKeys;

    @Spy
    @InjectMocks
    private CalcController controller;

    private ArrayList<SingleDigit> singleDigitList;
    private SingleDigit singleDigit;

    private Integer k = 4;
    private String n = "9875";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        singleDigitList = new ArrayList<>();
        singleDigit = new SingleDigit() {{
            setK(k);
            setN(n);
        }};
    }

    @Test
    public void addSingleDigitShouldAddSingleDigitToCacheWithoutUser() {
        when(singleDigitCache.set(singleDigit)).thenReturn(singleDigit);
        when(cacheKeys.getPublicKey()).thenReturn(null);
        ResponseEntity<SingleDigit> cachedSingleDigit = controller.addSingleDigit(singleDigit);
        assertAddSingleDigit(cachedSingleDigit);
    }

    private void assertAddSingleDigit(ResponseEntity<SingleDigit> cachedSingleDigit) {
        assertEquals(Integer.valueOf(4), cachedSingleDigit.getBody().getK());
        assertEquals("9875", cachedSingleDigit.getBody().getN());
    }

    @Test
    public void getSingleDigitsShouldReturnListFromCache() {
        when(singleDigitCache.getAll()).thenReturn(singleDigitList);
        ResponseEntity<ArrayList<SingleDigit>> singleDigits = controller.getSingleDigits();
        assertEquals(0, singleDigits.getBody().size());
    }
}