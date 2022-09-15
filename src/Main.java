import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"МОЛОКО", "КЕФИР", "БАТОН", "ХЛЕБ"};
        int[] prices = {55, 44, 33, 38};
        Basket baskets = new Basket(products, prices);
        baskets.printSale();
        int productNum;
        int amount;
        File basketUser = new File("basket.txt");
        if (basketUser.exists()) {
            System.out.println("У Вас есть корзина с прошлого сеанса");
            baskets.loadTxt();
            baskets.printCart(products, prices);
        } else {
            try {
                if (basketUser.createNewFile()) {
                    System.out.println("Создана новая корзина");
                }
            } catch (IOException e) {
                System.out.println("Каталог(путь) не найден");
                throw new RuntimeException();
            }
        }
        while (true) {
            System.out.println("Выберите товар и количество или введите 'end':");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                baskets.printCart(products, prices);
                break;
            }
            String[] userChoice = input.split(" ");
            productNum = Integer.parseInt(userChoice[0]);
            amount = Integer.parseInt(userChoice[1]);
            baskets.addToCart(productNum, amount);
            baskets.saveTxt(basketUser);
        }

    }
}