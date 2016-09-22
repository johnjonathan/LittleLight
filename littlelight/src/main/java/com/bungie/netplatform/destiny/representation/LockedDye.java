package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class LockedDye {

    @Expose
    private Long channelHash;
    @Expose
    private Long dyeHash;

    /**
     * @return The channelHash
     */
    public Long getChannelHash() {
        return channelHash;
    }

    /**
     * @param channelHash The channelHash
     */
    public void setChannelHash(Long channelHash) {
        this.channelHash = channelHash;
    }

    /**
     * @return The dyeHash
     */
    public Long getDyeHash() {
        return dyeHash;
    }

    /**
     * @param dyeHash The dyeHash
     */
    public void setDyeHash(Long dyeHash) {
        this.dyeHash = dyeHash;
    }

}
