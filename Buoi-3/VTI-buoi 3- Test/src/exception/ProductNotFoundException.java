package exception;

/**
 * Ngoại lệ được ném ra khi không tìm thấy sản phẩm trong hệ thống.
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
