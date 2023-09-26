package ra.run;

import ra.bussiness.BookBussiness;
import ra.entity.Book;

import java.util.List;
import java.util.Scanner;

public class BookManagement {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        do {
            System.out.println("*****************MENU*************");
            System.out.println(" 1. Hiển thị các sách");
            System.out.println(" 2. Thêm mới sách");
            System.out.println(" 3. Cập nhật sách");
            System.out.println(" 4. Xóa sách");
            System.out.println(" 5. Tìm sách theo tên sách");
            System.out.println(" 6. Thống kê sách theo tác giả");
            System.out.println(" 7. Sắp xếp sách theo giá tăng dần (procedure)");
            System.out.println(" 8. Thoát");
            System.out.print("Lựa chọn của bạn:");
            int choice=Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    displayListBook();
                    break;
                case 2:
                    createBook(scanner);
                    break;
                case 3:
                    updateBook(scanner);
                    break;
                case 4:
                    deleteBook(scanner);
                    break;
                case 5:
                    searchNameBook(scanner);
                    break;
                case 6:
                    statisticsAuthor();
                    break;
                case 7:
                    sortFromPriceASC();
                    break;
                case 8:
                    System.exit(0);
                    break;
                default:
                    System.err.println("Vui lòng nhập từ 1-8");
            }
        }while (true);
    }
    public static void displayListBook(){
        List<Book> listBook = BookBussiness.getAllBook();
        System.out.println("Thông tin các sách:");
        listBook.stream().forEach(book ->book.displayData());
    }
    public static void createBook(Scanner scanner){
        //Nhập dữ liệu cho 1 sách để thêm mới
        Book bookNew=new Book();
        bookNew.inputData(scanner);
        //Gọi createBook của BookBussiness để thực hiện thêm dữ liệu vào db
        boolean result =BookBussiness.createBook(bookNew);
        if(result){
            System.out.println("Thêm mới thành công");
        }else {
            System.err.println("Có lỗi xảy ra trong quá trình thực hiện, vui lòng thực hiện lại.");
        }
    }
    public static void updateBook(Scanner scanner){
        System.out.println("Nhập mã sách cần cập nhật :");
        String bookIdUpdate= scanner.nextLine();
        //Kiểm tra mã sách này có tồn tại hay không
        Book bookUpdate=BookBussiness.getBookById(bookIdUpdate);
        if(bookUpdate!=null){
            //Mã sách có tồn tại trong CSDL
            System.out.println("Nhập vào tên sách cần cập nhật :");
            bookUpdate.setBookName(scanner.nextLine());
            System.out.println("Nhập vào giá sách :");
            bookUpdate.setPrice(Float.parseFloat(scanner.nextLine()));
            System.out.println("Nhập vào tên tác giả cần cập nhật :");
            bookUpdate.setAuthor(scanner.nextLine());
            System.out.println("Nhập vào trạng thái :");
            bookUpdate.setStatus(Boolean.parseBoolean(scanner.nextLine()));
            //Thực hiện cập nhật
            boolean result=BookBussiness.updateBook(bookUpdate);
            if(result){
                System.out.println("Cập nhật thành công");
            }else{
                System.err.println("Có lỗi xảy ra trong quá trình thực hiện, vui lòng thực hiện lại.");
            }
        }else {
            //Mã sách không tồn tại trong CSDL
            System.err.println("Mã sách không tồn tại");
        }
    }
    public static void deleteBook(Scanner scanner){
        System.out.println("Nhập vào mã sách cần xóa:");
        String bookIdDelete= scanner.nextLine();
        //Kiểm tra bookIdDelete có tồn tại trong DB
        Book bookDelete=BookBussiness.getBookById(bookIdDelete);
        if(bookDelete!=null){
            boolean result=BookBussiness.deleteBook(bookIdDelete);
            if(result){
                System.out.println("Xóa thành công.");
            }else{
                System.err.println("Có lỗi xảy ra trong quá trình thực hiện, vui lòng thực hiện lại.");
            }
        }else{
            System.err.println("Mã sách không tồn tại.");
        }
    }
    public static void searchNameBook(Scanner scanner){
        System.out.println("Nhập vào tên sách cần tìm :");
        String bookNameSearch = scanner.nextLine();
        //Kiểm tra bookSearch có tồn tại trong DB
        Book bookSearch = BookBussiness.getBookByName(bookNameSearch);
        if (bookSearch!=null){
            List<Book> listBook = BookBussiness.searchBook(bookNameSearch);
            System.out.println("Thông tin sách tìm kiếm :");
            listBook.forEach(Book::displayData);
        }else{
            System.err.println("Tên sách không tồn tại.");
        }
    }
    public static void statisticsAuthor(){
        System.out.println("Thông tin thống kê sách:");
        BookBussiness.statisticsAuthor();
    }
    public static void sortFromPriceASC(){
        List<Book> listBook = BookBussiness.sortBook();
        System.out.println("Sắp xếp theo giá: ");
        listBook.forEach(Book::displayData);
    }
}
