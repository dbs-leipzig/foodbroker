package org.biiig.foodbroker.pile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by peet on 25.11.14.
 */
public abstract class AbstractMasterDataPile implements MasterDataPile {
    private int currentIndex = 0;
    private final int lastIndex;
    private final List<Integer> ids = new ArrayList<>();

    public AbstractMasterDataPile(int size){
        lastIndex = size-1;
        currentIndex = lastIndex;
        for(int i = 0; i<size;i++){
            ids.add(i);
        }
        Collections.shuffle(ids);
    }

    protected int nextId(){
        if (currentIndex == lastIndex){
            currentIndex = 0;
        }
        else{
            currentIndex++;
        }
        return ids.get(currentIndex);
    }
}
