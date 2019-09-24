package br.com.elvisoliveira.literatetrain.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class SingleDigitTest {
    @Test
    public void testGetPWithExerciseExample_shouldReturnExerciseResult() {
        SingleDigit singleDigit = new SingleDigit();
        singleDigit.setK(4);
        singleDigit.setN("9875");
        singleDigit.setP();
        assertEquals((Integer) 8, singleDigit.getP());
    }
}