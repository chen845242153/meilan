package com.lion.common.eventbus;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2021/6/3 3:45 下午
 */
public class MessageEvent<T> {

    public MessageEvent(T message, int type) {
        this.message = message;
        this.type = type;
    }

    private T message;
    private int type;

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
