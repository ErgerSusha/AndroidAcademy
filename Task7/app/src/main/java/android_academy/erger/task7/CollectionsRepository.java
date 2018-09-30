package android_academy.erger.task7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android_academy.erger.task7.entities.Collection;

public class CollectionsRepository {

    private static final CollectionsRepository INSTANCE = new CollectionsRepository();

    private HashMap<Integer, Collection> collectionHashMap;

    public static CollectionsRepository getInstance() {
        return INSTANCE;
    }

    private CollectionsRepository() {
        this.collectionHashMap = new HashMap<>();
    }

    public void saveCollection(Collection collection) {
        collectionHashMap.put(collection.getId(), collection);
    }

    public Collection getCollection(int id) {
        return collectionHashMap.get(id);
    }

    public List<Collection> getCollections() {
        return new ArrayList<>(collectionHashMap.values());
    }
}