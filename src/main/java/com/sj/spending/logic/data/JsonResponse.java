package com.sj.spending.logic.data;

public class JsonResponse<T> {

    private final T data;

    private JsonResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public static <T> JsonResponse<T> of(T data) {
        return new JsonResponse<>(data);
    }

}
