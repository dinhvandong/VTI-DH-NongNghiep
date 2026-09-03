package service;

import exception.InvalidProductException;
import exception.ProductNotFoundException;
import model.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Lớp quản lý danh sách sản phẩm và các thao tác nghiệp vụ.
 */
public class ProductManager {
    // Lưu danh sách sản phẩm bằng ArrayList
    private final List<Product> products = new ArrayList<>();

    /**
     * Kiểm tra tính hợp lệ của sản phẩm trước khi thêm mới.
     * Ném InvalidProductException nếu dữ liệu không hợp lệ hoặc trùng ID.
     */
    public void validateProduct(Product product, boolean isNew) {
        if (product == null) {
            throw new InvalidProductException("Thông tin sản phẩm không được null.");
        }
        if (product.getId() <= 0) {
            throw new InvalidProductException("ID sản phẩm phải là số nguyên dương lớn hơn 0 (ID nhận được: " + product.getId() + ").");
        }
        if (isNew && existsById(product.getId())) {
            throw new InvalidProductException("ID " + product.getId() + " đã tồn tại. Hệ thống không cho phép trùng ID!");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new InvalidProductException("Tên sản phẩm không được để trống.");
        }
        if (product.getCategory() == null || product.getCategory().trim().isEmpty()) {
            throw new InvalidProductException("Danh mục sản phẩm không được để trống.");
        }
        if (product.getPrice() < 0) {
            throw new InvalidProductException("Giá sản phẩm phải >= 0 (Giá nhận được: " + product.getPrice() + ").");
        }
        if (product.getQuantity() < 0) {
            throw new InvalidProductException("Số lượng sản phẩm phải >= 0 (Số lượng nhận được: " + product.getQuantity() + ").");
        }
    }

    /**
     * Thêm sản phẩm mới vào danh sách.
     */
    public void addProduct(Product product) {
        validateProduct(product, true);
        // Chuẩn hóa chuỗi trước khi lưu
        product.setName(product.getName().trim());
        product.setCategory(product.getCategory().trim());
        products.add(product);
    }

    /**
     * Kiểm tra ID đã tồn tại trong danh sách hay chưa.
     */
    public boolean existsById(int id) {
        for (Product p : products) {
            if (p.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tìm kiếm sản phẩm theo ID.
     * Ném ProductNotFoundException nếu không tìm thấy.
     */
    public Product findProductById(int id) {
        if (id <= 0) {
            throw new InvalidProductException("ID tìm kiếm phải là số nguyên dương (> 0).");
        }
        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new ProductNotFoundException("Không tìm thấy sản phẩm có ID = " + id);
    }

    /**
     * Tìm danh sách sản phẩm theo danh mục (category).
     * Ném ProductNotFoundException nếu không có sản phẩm nào thuộc danh mục này.
     */
    public List<Product> findProductsByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new InvalidProductException("Tên danh mục tìm kiếm không được để trống.");
        }
        String cleanCategory = category.trim();
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getCategory().equalsIgnoreCase(cleanCategory)) {
                result.add(p);
            }
        }
        if (result.isEmpty()) {
            throw new ProductNotFoundException("Không tìm thấy sản phẩm nào thuộc danh mục: '" + cleanCategory + "'");
        }
        return result;
    }

    /**
     * Lọc sản phẩm theo khoảng giá [minPrice, maxPrice].
     * Ném InvalidProductException nếu khoảng giá không hợp lệ.
     * Ném ProductNotFoundException nếu không tìm thấy sản phẩm nào trong khoảng giá.
     */
    public List<Product> filterByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0) {
            throw new InvalidProductException("Giá trị lọc không được âm (min: " + minPrice + ", max: " + maxPrice + ").");
        }
        if (minPrice > maxPrice) {
            throw new InvalidProductException("Khoảng giá không hợp lệ: minPrice (" + minPrice + ") không được lớn hơn maxPrice (" + maxPrice + ").");
        }

        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getPrice() >= minPrice && p.getPrice() <= maxPrice) {
                result.add(p);
            }
        }
        if (result.isEmpty()) {
            throw new ProductNotFoundException(String.format("Không có sản phẩm nào trong khoảng giá từ %,.2f đến %,.2f.", minPrice, maxPrice));
        }
        return result;
    }

    /**
     * Sắp xếp danh sách sản phẩm theo giá:
     * - ascending = true : Tăng dần
     * - ascending = false: Giảm dần
     */
    public List<Product> sortProductsByPrice(boolean ascending) {
        Comparator<Product> comparator = Comparator.comparingDouble(Product::getPrice);
        if (!ascending) {
            comparator = comparator.reversed();
        }
        products.sort(comparator);
        return new ArrayList<>(products);
    }

    /**
     * Xóa sản phẩm theo ID.
     */
    public boolean deleteProduct(int id) {
        Product p = findProductById(id); // Sẽ ném ProductNotFoundException nếu không thấy
        return products.remove(p);
    }

    /**
     * Cập nhật thông tin sản phẩm.
     */
    public void updateProduct(int id, String newName, double newPrice, String newCategory, int newQuantity) {
        Product p = findProductById(id);
        Product temp = new Product(id, newName, newPrice, newCategory, newQuantity);
        validateProduct(temp, false); // Validate nhưng không check trùng ID của chính nó

        p.setName(newName.trim());
        p.setPrice(newPrice);
        p.setCategory(newCategory.trim());
        p.setQuantity(newQuantity);
    }

    /**
     * Lấy toàn bộ danh sách sản phẩm hiện có.
     */
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    /**
     * In danh sách sản phẩm dưới dạng bảng đẹp mắt.
     */
    public static void printTable(List<Product> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
        System.out.println("+--------+---------------------------+-----------------+--------------+----------+");
        System.out.println("| ID     | Tên sản phẩm              | Danh mục        | Giá (VNĐ)    | Số lượng |");
        System.out.println("+--------+---------------------------+-----------------+--------------+----------+");
        for (Product p : list) {
            System.out.println(p.toTableRow());
        }
        System.out.println("+--------+---------------------------+-----------------+--------------+----------+");
        System.out.printf("Tổng số: %d sản phẩm\n", list.size());
    }
}
