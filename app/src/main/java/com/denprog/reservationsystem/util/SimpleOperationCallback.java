package com.denprog.reservationsystem.util;

public interface SimpleOperationCallback <T>{
    void onLoading();
    void onFinished(T data);
    void onError(String message);
}
