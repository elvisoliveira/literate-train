package br.com.elvisoliveira.literatetrain.Cache;

import br.com.elvisoliveira.literatetrain.Model.SingleDigit;

import java.util.ArrayList;

public class SingleDigitCache extends BaseCache<SingleDigit> {
    public SingleDigit set(SingleDigit singleDigit) {
        SingleDigit checkSingleDigit = getSingleDigitByParams(singleDigit.getK(), singleDigit.getN());
        if (checkSingleDigit != null) {
            return checkSingleDigit;
        }
        Integer singleDigitId = baseCacheList.size();
        if (baseCacheList.size() > 1) {
            SingleDigit lastSingleDigit = baseCacheList.get(baseCacheList.size() - 1);
            singleDigitId = lastSingleDigit.getId() + 1;
        }
        singleDigit.setId(singleDigitId);
        singleDigit.setP();
        baseCacheList.add(singleDigit);
        if (baseCacheList.size() > 10) {
            baseCacheList = new ArrayList<>(baseCacheList.subList(baseCacheList.size() - 10, baseCacheList.size()));
        }
        return singleDigit;
    }

    private SingleDigit getSingleDigitByParams(Integer k, String n) {
        for (SingleDigit singleDigit : baseCacheList) {
            if (singleDigit.getK().equals(k) && singleDigit.getN().equals(n)) {
                return singleDigit;
            }
        }
        return null;
    }
}