import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File binFile = new File(("basket.bin"));
        Scanner scanner = new Scanner(System.in);
        String[] products = {"МОЛОКО", "КЕФИР", "БАТОН", "ХЛЕБ"};
        int[] prices = {55, 44, 33, 38};
        Basket baskets = new Basket(products, prices);
        baskets.printSale();
        int productNum;
        int amount;
        if (Files.exists(Path.of(String.valueOf(binFile)))) {
            System.out.println("У Вас есть корзина с прошлого сеанса");
            baskets = Basket.loadFromBinFile(binFile);
            baskets.printCart();
        }
        while (true) {
            System.out.println("Выберите товар и количество или введите 'end':");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                baskets.printCart();
                baskets.saveBin(new File(String.valueOf(binFile)));
                break;
            }
            String[] userChoice = input.split(" ");
            productNum = Integer.parseInt(userChoice[0]);
            amount = Integer.parseInt(userChoice[1]);
            baskets.addToCart(productNum, amount);
        }

    }
}
