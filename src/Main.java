import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"МОЛОКО", "КЕФИР", "БАТОН", "ХЛЕБ"};
        int[] prices = {55, 44, 33, 38};
        Collection<Basket> baskets = new ArrayList<>();
        Basket.setSumOneProduct(products.length);
        System.out.println("Список возможных товаров для покупок:");
        for (int i = 0; i < products.length; i++) {
            baskets.add(new Basket(products[i], prices[i]));
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт");
        }
        int productNum;
        int amount;
        File basketUser = new File("basket.txt");
        if (basketUser.exists()) {
            System.out.println("У Вас есть корзина с прошлого сеанса");
            Basket.loadTxt();
        }
        else {
            try{
                if (basketUser.createNewFile()){
                    System.out.println("Создана новая корзина");
                }
            } catch (IOException e){
                System.out.println("Каталог(путь) не найден");
                throw new RuntimeException();
            }
        }
        while (true) {
            System.out.println("Выберите товар и количество или введите 'end':");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                Basket.printCart(products, prices);
                break;
            }
            String[] userChoice = input.split(" ");
            productNum = Integer.parseInt(userChoice[0]);
            amount = Integer.parseInt(userChoice[1]);
            Basket.addToCart(productNum, amount);
            Basket.saveTxt(basketUser);
        }

    }
}
