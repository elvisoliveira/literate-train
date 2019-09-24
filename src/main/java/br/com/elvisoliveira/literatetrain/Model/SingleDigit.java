package br.com.elvisoliveira.literatetrain.Model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SingleDigit implements ModelInterface {
    private Integer P;
    private Integer singleDigitId;

    @NotBlank(message = "Required field: n")
    private String n;

    @NotNull(message = "Required field: k")
    private Integer k;

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public Integer getP() {
        return P;
    }

    public void setP() {
        this.P = calcSingleDigit();
    }

    private Integer calcSingleDigit() {
        String baseNumber = new String(new char[getK()]).replace("\0", getN()).replaceAll("[^0-9.]", "");
        while (baseNumber.length() > 1) {
            baseNumber = calcBaseNumber(baseNumber);
        }
        return Integer.parseInt(baseNumber);
    }

    private String calcBaseNumber(String baseNumber) {
        Integer sum = 0;
        for (char number: baseNumber.toCharArray()) {
            Integer n = Integer.parseInt(String.valueOf(number));
            sum += n;
        }
        return sum.toString();
    }

    @Override
    public void setId(Integer id) {
        this.singleDigitId = id;
    }

    @Override
    public Integer getId() {
        return singleDigitId;
    }
}