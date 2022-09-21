import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParseException, ParserConfigurationException, SAXException {
        Scanner scanner = new Scanner(System.in);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));
        Element loadFileJson = doc.getDocumentElement();
        Element load = (Element) loadFileJson;
        Element save = (Element) loadFileJson;
        Element log = (Element) loadFileJson;

        String[] products = {"МОЛОКО", "КЕФИР", "БАТОН", "ХЛЕБ"};
        int[] prices = {55, 44, 33, 38};
        Basket baskets = new Basket(products, prices);
        baskets.printSale();
        ClientLog clientLog = new ClientLog(prices);
        int productNum;
        int amount;
        File basketUser = new File("basket.txt");
        File fileJson = new File(load.getElementsByTagName("fileName").item(0).getTextContent());
        File txtFile = new File("log.csv");
        if (Node.ELEMENT_NODE == loadFileJson.getNodeType()) {

            String loadBasket = load.getElementsByTagName("enabled").item(0).getTextContent();
            if (loadBasket.equals("true")) {
                System.out.println("Будет загружен " + load.getElementsByTagName("format").item(0).getTextContent() + " файл");
                if (load.getElementsByTagName("fileName").item(0).getTextContent().equals("json")) {
                    if (fileJson.exists()) {
                        clientLog.loadJson();
                        System.out.println("Файл json загружен");
                    } else {
                        try {
                            if (fileJson.createNewFile()) {
                                System.out.println("Корзина json создана");
                            }
                        } catch (IOException e) {
                            System.out.println("Каталог(путь) не найден");
                            throw new RuntimeException();
                        }
                    }
                }
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
            }
            while (true) {
                System.out.println("Выберите товар и количество или введите 'end':");
                String input = scanner.nextLine();
                if (input.equals("end")) {
                    baskets.printCart(products, prices);
                    String saveBasketLog = log.getElementsByTagName("enabled").item(1).getTextContent();
                    if (saveBasketLog.equals("true")) {
                        clientLog.exportAsCSV(txtFile);
                    } else {
                        System.out.println("Сохранений в csv нет");
                    }
                    break;
                }
                String[] userChoice = input.split(" ");
                productNum = Integer.parseInt(userChoice[0]);
                amount = Integer.parseInt(userChoice[1]);
                baskets.addToCart(productNum, amount);
                clientLog.log(productNum, amount);
                baskets.saveTxt(basketUser);
                String saveBasket = save.getElementsByTagName("enabled").item(0).getTextContent();
                if (saveBasket.equals("true")) {
                    System.out.println("Будет сохранен " + save.getElementsByTagName("format").item(0).getTextContent() + " файл");
                    if (save.getElementsByTagName("format").item(0).getTextContent().equals("json")) {
                        clientLog.saveJson();
                    }
                }
            }
        }
    }
}
