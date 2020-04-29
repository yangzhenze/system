package com.swsk.data.util.generate.create;

/**
 * 字段信息表
 */
class FieldMeta {
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private String fieldDataType;
    /**
     * 字段长度
     */
    private int fieldLength;
    /**
     * 字段备注
     */
    private String fieldComment;

    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDataType() {
        if("bit".equalsIgnoreCase(fieldDataType)){
            return "java.lang.Boolean";
        }else if("tinyint".equalsIgnoreCase(fieldDataType)){
            return "java.lang.Byte";
        }else if("smallint".equalsIgnoreCase(fieldDataType)){
            return "java.lang.Short";
        }else if("int".equalsIgnoreCase(fieldDataType)){
            return "java.lang.Integer";
        }else if("bigint".equalsIgnoreCase(fieldDataType) || "TIMESTAMP".equalsIgnoreCase(fieldDataType)){
            return "java.lang.Long";
        }else if("float".equalsIgnoreCase(fieldDataType)) {
            return "java.lang.Float";
        }else if("decimal".equalsIgnoreCase(fieldDataType)){
            return "java.math.BigDecimal";
        }else if("numeric".equalsIgnoreCase(fieldDataType)
                || "real".equalsIgnoreCase(fieldDataType) || "money".equalsIgnoreCase(fieldDataType)
                || "smallmoney".equalsIgnoreCase(fieldDataType) || "double".equalsIgnoreCase(fieldDataType)){
            return "java.lang.Double";
        }else if("varchar".equalsIgnoreCase(fieldDataType) || "char".equalsIgnoreCase(fieldDataType)
                || "nvarchar".equalsIgnoreCase(fieldDataType) || "nchar".equalsIgnoreCase(fieldDataType)
                || "text".equalsIgnoreCase(fieldDataType) || "LONGTEXT".equalsIgnoreCase(fieldDataType)){
            return "java.lang.String";
        }else if("datetime".equalsIgnoreCase(fieldDataType)||"year".equalsIgnoreCase(fieldDataType)
                || "date".equalsIgnoreCase(fieldDataType)){
            return "java.util.Date";
        }else{

        }
        return fieldDataType;
    }
    public void setFieldDataType(String fieldDataType) {
        if("bit".equalsIgnoreCase(fieldDataType)){
            this.fieldDataType = "java.lang.Boolean";
        }else if("tinyint".equalsIgnoreCase(fieldDataType)){
            this.fieldDataType = "java.lang.Byte";
        }else if("smallint".equalsIgnoreCase(fieldDataType)){
            this.fieldDataType = "java.lang.Short";
        }else if("int".equalsIgnoreCase(fieldDataType)){
            this.fieldDataType = "java.lang.Integer";
        }else if("bigint".equalsIgnoreCase(fieldDataType)){
            this.fieldDataType = "java.lang.Long";
        }else if("float".equalsIgnoreCase(fieldDataType)){
            this.fieldDataType = "java.lang.Float";
        }else if("decimal".equalsIgnoreCase(fieldDataType)){
            this.fieldDataType = "java.math.BigDecimal";
        }else if("numeric".equalsIgnoreCase(fieldDataType)
                || "real".equalsIgnoreCase(fieldDataType) || "money".equalsIgnoreCase(fieldDataType)
                || "smallmoney".equalsIgnoreCase(fieldDataType) || "double".equalsIgnoreCase(fieldDataType)){
            this.fieldDataType = "java.lang.Double";
        }else if("varchar".equalsIgnoreCase(fieldDataType) || "char".equalsIgnoreCase(fieldDataType)
                || "nvarchar".equalsIgnoreCase(fieldDataType) || "nchar".equalsIgnoreCase(fieldDataType)
                || "text".equalsIgnoreCase(fieldDataType) || "LONGTEXT".equalsIgnoreCase(fieldDataType)){
            this.fieldDataType = "java.lang.String";
        }else if("datetime".equalsIgnoreCase(fieldDataType) ||"year".equalsIgnoreCase(fieldDataType)
                || "date".equalsIgnoreCase(fieldDataType)){
            this.fieldDataType = "java.util.Date";
        }else{
            this.fieldDataType = fieldDataType;
        }

    }

    public int getFieldLength() {
        return fieldLength;
    }
    public void setFieldLength(int fieldLength) {
        this.fieldLength = fieldLength;
    }

    public String getFieldComment() {
        return fieldComment;
    }
    public void setFieldComment(String fieldComment) {
        this.fieldComment = fieldComment;
    }

}
