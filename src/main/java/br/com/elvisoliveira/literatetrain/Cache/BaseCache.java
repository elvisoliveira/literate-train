package br.com.elvisoliveira.literatetrain.Cache;

import br.com.elvisoliveira.literatetrain.Model.ModelInterface;
import br.com.elvisoliveira.literatetrain.Model.SingleDigit;

import java.util.ArrayList;

public class BaseCache<T extends ModelInterface> {
    protected ArrayList<T> baseCacheList = new ArrayList<>();

    public SingleDigit set(T object) {
        object.setId(baseCacheList.size());
        baseCacheList.add(object);
        return null;
    }

    public void unset(int id) {
        baseCacheList.remove(id);
    }

    public void update(T object) {
        baseCacheList.set(object.getId(), object);
    }

    public T get(int id) {
        return baseCacheList.get(id);
    }

    public ArrayList<T> getAll() {
        return baseCacheList;
    }
}