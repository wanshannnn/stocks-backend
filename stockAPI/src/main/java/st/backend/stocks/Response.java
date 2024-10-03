package st.backend.stocks;

public class Response<T> {

    private T data;
    private boolean success;
    private String errorMsg;
    private int statusCode;

    // 带参构造函数
    public Response(T data, boolean success, String errorMsg, int statusCode) {
        this.data = data;
        this.success = success;
        this.errorMsg = errorMsg;
        this.statusCode = statusCode;
    }

    // 成功响应的静态工厂方法
    public static <K> Response<K> newSuccess(K data) {
        return new Response<>(data, true, null, 200);
    }

    // 失败响应的静态工厂方法
    public static <K> Response<K> newFail(String errorMsg, int statusCode) {
        return new Response<>(null, false, errorMsg, statusCode);
    }

    // Getters and Setters
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}