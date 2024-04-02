package ecommerce.dto;

public class ApiResponse {

    private String message;
    private boolean success;
    public ApiResponse(String message, boolean success) {
        super();
        this.message = message;
        this.success = success;

    }

    public ApiResponse(String productFound, boolean b, ProductDto allProducts) {
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public ApiResponse() {
        super();
        // TODO Auto-generated constructor stub
    }
//		public ApiResponse(boolean b, String string) {
//			// TODO Auto-generated constructor stub
//		}
//
}