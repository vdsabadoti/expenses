package fr.vds.expenses.bo;

public class ResponseService <T> {

    public String code;
    public String message;
    public T data;

    public ResponseService(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseService() {}

}
