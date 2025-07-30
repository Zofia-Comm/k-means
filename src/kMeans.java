import java.util.*;

public class kMeans {
    private int k;
    private int maxIterations = 100;
    private List<List<Double>> data;
    private List<String> answers;
    private List<List<Double>> centroids;
    private List<List<Integer>> clusters;

    public kMeans(int k, List<List<Double>> data, List<String> answers) {
        this.k = k;
        this.data = data;
        this.answers = answers;
        this.centroids = new ArrayList<>();
        this.clusters = new ArrayList<>();
    }

    public void run() {
        newCentroids(centroids);
        for (int iter = 1; iter <= maxIterations; iter++) {
            assignClusters(centroids);
            double totalDistance = distanceInCluster();
            System.out.println("Total distance: " + totalDistance);
            List<List<Double>> newCentroids = recalculate();
            if (noChange(newCentroids)) {
                System.out.println("Centroidy się nie zmieniły");
                break;
            }
            centroids = newCentroids;
        }
        printCentroids();
    }

    private void newCentroids(List<List<Double>> centroids) {
        Set<Integer> randomInt = new HashSet<>();

        while (randomInt.size() < k) {
            int index = (int)(Math.random()*data.size());
            if (!randomInt.contains(Optional.of(index))) {
                centroids.add(new ArrayList<>(data.get(index)));
                randomInt.add(Integer.valueOf(index));
            }
        }
    }

    private double euclidean(List<Double> a, List<Double> b) {
        double sum = 0;
        for (int i = 0; i < a.size(); i++) {
            sum += Math.pow(a.get(i) - b.get(i), 2);
        }
        return Math.sqrt(sum);
    }


    private void assignClusters(List<List<Double>> centroids) {
        clusters.clear();
        for (int i = 0; i < k; i++) {
            clusters.add(new ArrayList<>());
        }

        for (int i = 0; i < data.size(); i++) {
            List<Double> point = data.get(i);
            int nearestIndex = 0;
            double minDistance = euclidean(point, centroids.get(0));

            for (int j = 1; j < k; j++) {
                double dist = euclidean(point, centroids.get(j));
                if (dist < minDistance) {
                    minDistance = dist;
                    nearestIndex = j;
                }
            }

            clusters.get(nearestIndex).add(Integer.valueOf(i));
        }
    }



    private List<List<Double>> recalculate() {
        List<List<Double>> newCentroids = new ArrayList<>();
        for (List<Integer> cluster : clusters) {
            int sizeOfMatrix = data.get(0).size();
            List<Double> mean = new ArrayList<>();
            for (int i = 0; i < sizeOfMatrix; i++) {
                mean.add(Double.valueOf(0.0));
            }
            for (int index : cluster) {
                List<Double> point = data.get(index);
                for (int i = 0; i < sizeOfMatrix; i++) {
                    mean.set(i, Double.valueOf(mean.get(i) + point.get(i)));
                }
            }
            for (int i = 0; i < sizeOfMatrix; i++) {
                mean.set(i, Double.valueOf(mean.get(i) / cluster.size()));
            }
            newCentroids.add(mean);
        }
        return newCentroids;
    }

    private double distanceInCluster() {
        double total = 0;
        for (int i = 0; i < k; i++) {
            for (int index : clusters.get(i)) {
                total += euclidean(data.get(index), centroids.get(i));
            }
        }
        return total;
    }

    private boolean noChange(List<List<Double>> newCentroids) {
        for (int i = 0; i < k; i++) {
            if (!almostEqual(centroids.get(i), newCentroids.get(i)))
                return false;
        }
        return true;
    }

    private boolean almostEqual(List<Double> a, List<Double> b) {
        for (int i = 0; i < a.size(); i++) {
            double diff = a.get(i) - b.get(i);
            diff = ((int)diff*100);
            if (diff < 0){
                return false;
            }
        }
        return true;
    }
    public void printCentroids() {
        System.out.println("Skład grup:");
        for (int i = 0; i < clusters.size(); i++) {
            Map<String, Integer> labelCount = new HashMap<>();
            for (int index : clusters.get(i)) {
                String label = answers.get(index);
                labelCount.put(label, Integer.valueOf(labelCount.getOrDefault(label, Integer.valueOf(0)) + 1));
            }
            System.out.println("Grupa " + (i + 1) + " (" + clusters.get(i).size() + " przykładów): " + labelCount);
        }
    }
}

