import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        BufferedReader bufferedReaderData;
        List<String> answersToData = new ArrayList<>();
        List<List<Double>> fileToData = new ArrayList<>();

        try {
            bufferedReaderData = new BufferedReader(new FileReader("Z:\\NAI\\mmp4\\src\\iris.data"));
            while(bufferedReaderData.ready()){
                String [] text = bufferedReaderData.readLine().split(",");
                List<Double> tmpData = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    tmpData.add(Double.valueOf(Double.parseDouble(text[i])));
                }
                fileToData.add(tmpData);
                answersToData.add(text[4]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int k = 3;

        kMeans kmeans = new kMeans(k, fileToData, answersToData);
        kmeans.run();
    }
}
