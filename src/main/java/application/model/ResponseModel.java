package application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseModel<T> {

    private Meta meta;
    private T data;

    public Meta getMeta() {
        return meta;
    }

    public ResponseModel setMeta(Meta meta) {
        this.meta = meta;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseModel setData(T data) {
        this.data = data;
        return this;
    }

//    @Override
//    public String toString() {
//        return "{" +
//                "\"<meta>k__BackingField\":\"" + meta.toString() + '\"' +
//                '}';
//    }
}
