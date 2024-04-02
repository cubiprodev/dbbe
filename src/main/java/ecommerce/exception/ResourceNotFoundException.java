package ecommerce.exception;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException{
    private String resourceName;
    private String fieldName;
    private long fieldValue;
    private String filedVal;
    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue)); // Post not found with id : 1
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public ResourceNotFoundException(String resourceName, String fieldName, String fieldVal) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldVal)); // Post not found with id : 1
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.filedVal = fieldVal;
    }
    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldVal() {
        return filedVal;
    }

    public long getFieldValue() {
        return fieldValue;
    }

}