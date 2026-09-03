package exception;

/**
 * Ngoại lệ được ném ra khi thông tin dữ liệu sản phẩm không hợp lệ
 * (ví dụ: ID trùng, giá < 0, số lượng < 0, tên/danh mục rỗng, khoảng giá không hợp lệ,...).
 */
public class InvalidProductException extends RuntimeException {
    public InvalidProductException(String message) {
        super(message);
    }
}
