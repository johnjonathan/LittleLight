package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Currency_ {

    @Expose
    private Long value;
    @Expose
    private Long itemHash;

    /**
     * @return The value
     */
    public Long getValue() {
        return value;
    }

    /**
     * @param value The value
     */
    public void setValue(Long value) {
        this.value = value;
    }

    /**
     * @return The itemHash
     */
    public Long getItemHash() {
        return itemHash;
    }

    /**
     * @param itemHash The itemHash
     */
    public void setItemHash(Long itemHash) {
        this.itemHash = itemHash;
    }

}
