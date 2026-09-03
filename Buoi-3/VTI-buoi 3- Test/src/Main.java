import exception.InvalidProductException;
import exception.ProductNotFoundException;
import model.Product;
import service.ProductManager;

import java.util.List;
import java.util.Scanner;

/**
 * Chương trình chính quản lý sản phẩm với Menu tương tác và kịch bản kiểm thử tự động.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductManager manager = new ProductManager();

    public static void main(String[] args) {
        // Khởi tạo một số dữ liệu mẫu ban đầu
        initSampleData();

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("👉 Chọn chức năng (0-8): ");
            String choice = scanner.nextLine().trim();

            System.out.println();
            switch (choice) {
                case "1":
                    handleDisplayAll();
                    break;
                case "2":
                    handleAddProduct();
                    break;
                case "3":
                    handleFindById();
                    break;
                case "4":
                    handleFindByCategory();
                    break;
                case "5":
                    handleFilterByPrice();
                    break;
                case "6":
                    handleSortByPrice();
                    break;
                case "7":
                    handleDeleteProduct();
                    break;
                case "8":
                    runDemoTests();
                    break;
                case "0":
                    System.out.println("👋 Cảm ơn bạn đã sử dụng hệ thống. Tạm biệt!");
                    running = false;
                    break;
                default:
                    System.out.println("⚠️ Lựa chọn không hợp lệ, vui lòng chọn từ 0 đến 8!");
            }
            if (running) {
                System.out.println("\nNhấn Enter để tiếp tục...");
                scanner.nextLine();
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n==================================================");
        System.out.println("       HỆ THỐNG QUẢN LÝ SẢN PHẨM (MINI STORE)     ");
        System.out.println("==================================================");
        System.out.println(" 1. 📋 Hiển thị tất cả sản phẩm");
        System.out.println(" 2. ➕ Thêm sản phẩm mới");
        System.out.println(" 3. 🔍 Tìm sản phẩm theo ID");
        System.out.println(" 4. 🏷️  Tìm sản phẩm theo Danh mục (Category)");
        System.out.println(" 5. 💰 Lọc sản phẩm theo khoảng giá");
        System.out.println(" 6. 🔄 Sắp xếp sản phẩm theo giá (Tăng / Giảm)");
        System.out.println(" 7. ❌ Xóa sản phẩm theo ID");
        System.out.println(" 8. 🧪 Chạy bộ kiểm thử tự động (Demo All Scenarios)");
        System.out.println(" 0. 🚪 Thoát");
        System.out.println("==================================================");
    }

    /**
     * Dữ liệu mẫu ban đầu
     */
    private static void initSampleData() {
        try {
            manager.addProduct(new Product(1, "Laptop Dell XPS 15", 35000000, "Laptop", 10));
            manager.addProduct(new Product(2, "MacBook Pro M3", 42000000, "Laptop", 5));
            manager.addProduct(new Product(3, "iPhone 15 Pro Max", 30000000, "Điện thoại", 15));
            manager.addProduct(new Product(4, "Samsung Galaxy S24", 22000000, "Điện thoại", 20));
            manager.addProduct(new Product(5, "Chuột Logitech MX Master", 2500000, "Phụ kiện", 30));
            manager.addProduct(new Product(6, "Bàn phím cơ Keychron K2", 1800000, "Phụ kiện", 25));
        } catch (Exception e) {
            System.err.println("Lỗi khi khởi tạo dữ liệu mẫu: " + e.getMessage());
        }
    }

    private static void handleDisplayAll() {
        System.out.println("===== DANH SÁCH TẤT CẢ SẢN PHẨM =====");
        List<Product> list = manager.getAllProducts();
        ProductManager.printTable(list);
    }

    private static void handleAddProduct() {
        System.out.println("===== THÊM SẢN PHẨM MỚI =====");
        try {
            System.out.print("Nhập ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Nhập Tên sản phẩm: ");
            String name = scanner.nextLine();

            System.out.print("Nhập Giá: ");
            double price = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Nhập Danh mục: ");
            String category = scanner.nextLine();

            System.out.print("Nhập Số lượng: ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());

            Product p = new Product(id, name, price, category, quantity);
            manager.addProduct(p);
            System.out.println("✅ Thêm sản phẩm thành công!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Lỗi: ID, Giá và Số lượng phải là số!");
        } catch (InvalidProductException e) {
            System.out.println("❌ Lỗi dữ liệu không hợp lệ: " + e.getMessage());
        }
    }

    private static void handleFindById() {
        System.out.println("===== TÌM SẢN PHẨM THEO ID =====");
        try {
            System.out.print("Nhập ID cần tìm: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Product p = manager.findProductById(id);
            System.out.println("✅ Đã tìm thấy sản phẩm:");
            ProductManager.printTable(List.of(p));
        } catch (NumberFormatException e) {
            System.out.println("❌ Lỗi: ID phải là số nguyên!");
        } catch (ProductNotFoundException | InvalidProductException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void handleFindByCategory() {
        System.out.println("===== TÌM SẢN PHẨM THEO DANH MỤC =====");
        try {
            System.out.print("Nhập tên danh mục: ");
            String category = scanner.nextLine();
            List<Product> list = manager.findProductsByCategory(category);
            System.out.println("✅ Kết quả tìm kiếm:");
            ProductManager.printTable(list);
        } catch (ProductNotFoundException | InvalidProductException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void handleFilterByPrice() {
        System.out.println("===== LỌC SẢN PHẨM THEO KHOẢNG GIÁ =====");
        try {
            System.out.print("Nhập giá tối thiểu (minPrice): ");
            double min = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Nhập giá tối đa (maxPrice): ");
            double max = Double.parseDouble(scanner.nextLine().trim());

            List<Product> list = manager.filterByPriceRange(min, max);
            System.out.printf("✅ Sản phẩm trong khoảng giá từ %,.2f đến %,.2f:\n", min, max);
            ProductManager.printTable(list);
        } catch (NumberFormatException e) {
            System.out.println("❌ Lỗi: Giá phải là số thực!");
        } catch (InvalidProductException | ProductNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void handleSortByPrice() {
        System.out.println("===== SẮP XẾP SẢN PHẨM THEO GIÁ =====");
        System.out.println("1. Tăng dần (Giá từ thấp đến cao)");
        System.out.println("2. Giảm dần (Giá từ cao xuống thấp)");
        System.out.print("Chọn cách sắp xếp (1/2): ");
        String opt = scanner.nextLine().trim();

        if (opt.equals("1")) {
            List<Product> sorted = manager.sortProductsByPrice(true);
            System.out.println("✅ Danh sách sau khi sắp xếp TĂNG DẦN theo giá:");
            ProductManager.printTable(sorted);
        } else if (opt.equals("2")) {
            List<Product> sorted = manager.sortProductsByPrice(false);
            System.out.println("✅ Danh sách sau khi sắp xếp GIẢM DẦN theo giá:");
            ProductManager.printTable(sorted);
        } else {
            System.out.println("⚠️ Lựa chọn không hợp lệ!");
        }
    }

    private static void handleDeleteProduct() {
        System.out.println("===== XÓA SẢN PHẨM =====");
        try {
            System.out.print("Nhập ID sản phẩm cần xóa: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            manager.deleteProduct(id);
            System.out.println("✅ Xóa sản phẩm ID " + id + " thành công!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Lỗi: ID phải là số nguyên!");
        } catch (ProductNotFoundException | InvalidProductException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    /**
     * Kịch bản kiểm thử tự động toàn diện theo đúng yêu cầu đề bài
     */
    public static void runDemoTests() {
        System.out.println("\n************************************************************");
        System.out.println("       BẮT ĐẦU CHẠY BỘ KIỂM THỬ TỰ ĐỘNG (DEMO TEST CASES)   ");
        System.out.println("************************************************************\n");

        ProductManager testManager = new ProductManager();

        // 1. Thêm sản phẩm hợp lệ
        System.out.println("--- Test 1: Thêm các sản phẩm hợp lệ ---");
        testManager.addProduct(new Product(101, "Tai nghe Sony WH-1000XM5", 8500000, "Âm thanh", 15));
        testManager.addProduct(new Product(102, "Loa Marshall Stanmore III", 9900000, "Âm thanh", 8));
        testManager.addProduct(new Product(103, "Màn hình Dell UltraSharp", 12000000, "Màn hình", 12));
        System.out.println("-> Thêm 3 sản phẩm thành công!");
        ProductManager.printTable(testManager.getAllProducts());

        // 2. Thử thêm sản phẩm trùng ID -> Kiểm tra InvalidProductException
        System.out.println("\n--- Test 2: Thêm sản phẩm trùng ID (ID = 101) ---");
        try {
            testManager.addProduct(new Product(101, "Tai nghe Fake", 1000000, "Âm thanh", 5));
            System.out.println("❌ THẤT BẠI: Lẽ ra phải ném InvalidProductException!");
        } catch (InvalidProductException e) {
            System.out.println("✅ THÀNH CÔNG (Bắt được ngoại lệ như mong đợi): " + e.getMessage());
        }

        // 3. Thử thêm sản phẩm có giá âm -> Kiểm tra InvalidProductException
        System.out.println("\n--- Test 3: Thêm sản phẩm có giá âm (Price = -50000) ---");
        try {
            testManager.addProduct(new Product(104, "Sản phẩm lỗi giá", -50000, "Khác", 5));
            System.out.println("❌ THẤT BẠI: Lẽ ra phải ném InvalidProductException!");
        } catch (InvalidProductException e) {
            System.out.println("✅ THÀNH CÔNG (Bắt được ngoại lệ như mong đợi): " + e.getMessage());
        }

        // 4. Tìm kiếm theo ID tồn tại
        System.out.println("\n--- Test 4: Tìm sản phẩm theo ID tồn tại (ID = 102) ---");
        Product p = testManager.findProductById(102);
        System.out.println("✅ Tìm thấy: " + p);

        // 5. Tìm kiếm theo ID không tồn tại -> Kiểm tra ProductNotFoundException
        System.out.println("\n--- Test 5: Tìm sản phẩm theo ID không tồn tại (ID = 999) ---");
        try {
            testManager.findProductById(999);
            System.out.println("❌ THẤT BẠI: Lẽ ra phải ném ProductNotFoundException!");
        } catch (ProductNotFoundException e) {
            System.out.println("✅ THÀNH CÔNG (Bắt được ngoại lệ như mong đợi): " + e.getMessage());
        }

        // 6. Tìm sản phẩm theo Category
        System.out.println("\n--- Test 6: Tìm sản phẩm theo Category ('Âm thanh') ---");
        List<Product> soundProducts = testManager.findProductsByCategory("Âm thanh");
        System.out.println("✅ Tìm thấy " + soundProducts.size() + " sản phẩm:");
        ProductManager.printTable(soundProducts);

        // 7. Tìm theo Category không tồn tại -> Kiểm tra ProductNotFoundException
        System.out.println("\n--- Test 7: Tìm Category không tồn tại ('Thời trang') ---");
        try {
            testManager.findProductsByCategory("Thời trang");
            System.out.println("❌ THẤT BẠI: Lẽ ra phải ném ProductNotFoundException!");
        } catch (ProductNotFoundException e) {
            System.out.println("✅ THÀNH CÔNG (Bắt được ngoại lệ như mong đợi): " + e.getMessage());
        }

        // 8. Lọc sản phẩm theo khoảng giá hợp lệ [8.000.000, 10.000.000]
        System.out.println("\n--- Test 8: Lọc sản phẩm giá từ 8.000.000 đến 10.000.000 ---");
        List<Product> filtered = testManager.filterByPriceRange(8000000, 10000000);
        ProductManager.printTable(filtered);

        // 9. Lọc với minPrice > maxPrice -> Kiểm tra InvalidProductException
        System.out.println("\n--- Test 9: Lọc với khoảng giá sai minPrice > maxPrice (15.000.000 > 5.000.000) ---");
        try {
            testManager.filterByPriceRange(15000000, 5000000);
            System.out.println("❌ THẤT BẠI: Lẽ ra phải ném InvalidProductException!");
        } catch (InvalidProductException e) {
            System.out.println("✅ THÀNH CÔNG (Bắt được ngoại lệ như mong đợi): " + e.getMessage());
        }

        // 10. Sắp xếp giá tăng dần
        System.out.println("\n--- Test 10: Sắp xếp theo giá TĂNG DẦN ---");
        List<Product> sortedAsc = testManager.sortProductsByPrice(true);
        ProductManager.printTable(sortedAsc);

        // 11. Sắp xếp giá giảm dần
        System.out.println("\n--- Test 11: Sắp xếp theo giá GIẢM DẦN ---");
        List<Product> sortedDesc = testManager.sortProductsByPrice(false);
        ProductManager.printTable(sortedDesc);

        System.out.println("\n************************************************************");
        System.out.println("       HOÀN THÀNH TẤT CẢ CÁC BÀI TEST TỰ ĐỘNG! ✅          ");
        System.out.println("************************************************************\n");
    }
}
