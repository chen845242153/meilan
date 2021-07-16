package com.meilancycling.mema.ble.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author sorelion qq 57135591
 */
public class CommandEntity implements Serializable {
    private byte[] data;
    /**
     * 命令号
     */
    private int commandType;
    /**
     * 字命令
     */
    private int commandId;
    /**
     * 类型
     */
    private int type;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getCommandType() {
        return commandType;
    }

    public void setCommandType(int commandType) {
        this.commandType = commandType;
    }

    public int getCommandId() {
        return commandId;
    }

    public void setCommandId(int commandId) {
        this.commandId = commandId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
