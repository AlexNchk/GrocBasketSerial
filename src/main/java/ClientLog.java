import au.com.bytecode.opencsv.CSVWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Arrays;

public class ClientLog {
    protected int[] saveSumOneProduct;

    public ClientLog(int[] prices) {
        if (saveSumOneProduct == null) {
            saveSumOneProduct = new int[prices.length];
        }
    }

    public void log(int productNum, int amount) {
        saveSumOneProduct[productNum - 1] += amount;
    }

    public void saveJson() throws IOException {
        JSONObject obj = new JSONObject();
        JSONArray objArr = new JSONArray();
        for (int q : saveSumOneProduct) {
            objArr.add(q);
        }
        obj.put("saveSumOneProduct", objArr);
        try (FileWriter file = new FileWriter("basket.json")) {
            file.write(obj.toJSONString());
            file.flush();
        }
    }

    public void loadJson() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("basket.json"));
            JSONObject basketUserLoad = (JSONObject) obj;
            JSONArray saveSumOneProduct = (JSONArray) basketUserLoad.get("saveSumOneProduct");
            for (Object baskObj : saveSumOneProduct) {
                for (int i = 0; i < this.saveSumOneProduct.length; i++) {
                    this.saveSumOneProduct[i] = Integer.parseInt(String.valueOf(baskObj));
                }
            }
        } catch (IOException | NumberFormatException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportAsCSV(File txtFile) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true))) {
            String[] saveStringLine = Arrays.toString(saveSumOneProduct).split(", ");
            writer.writeNext(saveStringLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
