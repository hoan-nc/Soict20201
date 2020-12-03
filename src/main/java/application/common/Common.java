package application.common;

import application.model.Meta;
import application.model.MetaDataStatus;
import application.model.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Calendar;
import java.util.List;

public class  Common {

    public static Authentication getAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    public static String getUsernName() {
        return getAuth().getName();
    }

    public static Integer getUserId() {
        return 1;
    }

    public static Calendar getCurrentDate() {
        return Calendar.getInstance();
    }


    public static ResponseModel setMetaData(ResponseModel responseModel, List data) {
        Meta meta = new Meta();
        if (data.isEmpty()) {
            meta.setTotalRecord(0);
            setMetaData(responseModel, meta, MetaDataStatus.NO_DATA);
        } else {
            meta.setTotalRecord(data.size());
            setMetaData(responseModel, meta, MetaDataStatus.SUCCESS);
        }
        responseModel.setData(data);
        return responseModel;
    }

    public static ResponseModel setMetaData(ResponseModel responseModel, Object obj) {
        Meta meta = new Meta();
        if (obj != null) setMetaData(responseModel, meta, MetaDataStatus.SUCCESS);
        else setMetaData(responseModel, meta, MetaDataStatus.NO_DATA);
        responseModel.setData(obj);
        return responseModel;
    }

    public static ResponseModel setMetaData(ResponseModel responseModel, MetaDataStatus metaDataStatus, List data) {
        Meta meta = new Meta();
        if (data.isEmpty()) {
            meta.setTotalRecord(0);
            setMetaData(responseModel, meta, MetaDataStatus.NO_DATA);
        } else {
            meta.setTotalRecord(data.size());
            setMetaData(responseModel, meta, metaDataStatus);
        }
        responseModel.setData(data);
        return responseModel;
    }

    public static ResponseModel<Object> setMetaData(List data) {
        ResponseModel responseModel = new ResponseModel();
        return setMetaData(responseModel, data);
    }

    public static ResponseModel setMetaData(Boolean result) {
        ResponseModel response = new ResponseModel();
        Meta meta = new Meta();
        try {
            if (result) {
                setMetaData(response, meta, MetaDataStatus.SUCCESS);
            } else {
                setMetaData(response, meta, MetaDataStatus.FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            setMetaData(response, meta, MetaDataStatus.FAILED);
        }
        return response;
    }

    public static ResponseModel setMetaData(Object obj) {
        ResponseModel responseModel = new ResponseModel();
        Meta meta = new Meta();
        if (obj instanceof String) {
            String result = (String) obj;
            result = result.substring(0, result.length() - 1);
            try {
                String message = "";
                if (result.equals("[SUCCESS]")) {
                    return setMetaData(responseModel, meta, MetaDataStatus.SUCCESS);
                } else {
                    message += "This item is using. Please delete all child in " + result + " before delete this item.";
                    meta.setMessage(message);
                    meta.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
                    responseModel.setMeta(meta);
                    return responseModel;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (obj instanceof Integer) {
            Integer result = (Integer) obj;
            if (result >= 1) {
                return setMetaData(responseModel, meta, MetaDataStatus.SUCCESS);
            } else {
                return setMetaData(responseModel, meta, MetaDataStatus.FAILED);
            }
        } else {
            responseModel.setData(obj);
            return setMetaData(responseModel, meta, MetaDataStatus.SUCCESS);
        }
        return setMetaData(responseModel, meta, MetaDataStatus.FAILED);
    }

    public static ResponseModel setMetaData(ResponseModel responseModel, Meta meta, MetaDataStatus metadataStatus) {
        if (responseModel == null) responseModel = new ResponseModel();
        if (meta == null) meta = new Meta();
        if (StringUtils.isEmpty(meta.getMessage())) meta.setMessage(metadataStatus.getMessage());
        return responseModel.setMeta(meta.setStatusCode(metadataStatus.getStatusCode()));
    }

}
