package application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
public class Meta {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(value = "status_code")
    private int statusCode;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(value = "message")
    private String message;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(value = "total_record")
    private int totalRecord;

    private int offset;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int limit;

    public int getStatusCode() {
        return statusCode;
    }

    public Meta setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Meta setStatusCode(HttpStatus httpStatus) {
        this.statusCode = httpStatus.value();
        return this;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public Meta setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
        return this;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getMessage() {
        return message;
    }

    public Meta setMessage(String message) {
        this.message = message;
        return this;
    }

//    @Override
//    public String toString() {
//        return "{" +
//                "\"<status_code>k__BackingField\":\"" + statusCode + '\"' +
//                ", \"<message>k__BackingField\":\"" + message + '\"' +
//                '}';
//    }
}