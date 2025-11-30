public class Main {

    public static double[][] idw(double[][] data) {
        int rows = data.length;
        int cols = data[0].length;

        // Power parameter p = 2 (as required by the assignment)
        int power_parameter = 2;

        // Loop over every cell
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                // Only interpolate when the cell is NaN
                if (Double.isNaN(data[r][c])) {

                    double weightedSum = 0.0;
                    double weightTotal = 0.0;

                    // Loop over all known points
                    for (int x = 0; x < rows; x++) {
                        for (int y = 0; y < cols; y++) {

                            if (!Double.isNaN(data[x][y])) {

                                // Euclidean distance
                                double dx = r - y;
                                double dy = y - c;

                                double euclidean_distance = Math.sqrt(dx * dx + dy * dy);

                                // If distance is zero (should not happen), avoid division by zero
                                if (euclidean_distance == 0) {
                                    continue;
                                }

                                // Weight = 1 / d^p
                                double weight = 1.0 / Math.pow(euclidean_distance, power_parameter);

                                weightedSum =weightedSum+ weight * data[x][y];
                                weightTotal =weightTotal+ weight;
                            }
                        }
                    }

                    // Final interpolated value
                    data[r][c] = weightedSum / weightTotal;
                }
            }
        }

        return data;
    }

    // ---------------------------------------------------------------------
    // Example test
    // ---------------------------------------------------------------------
    public static void main(String[] args) {

        double[][] exampleInput = {
                {1.0, Double.NaN, 3.0},
                {Double.NaN, Double.NaN, 2.0},
                {4.0, 5.0, Double.NaN}
        };

        double[][] output = idw(exampleInput);


        // Print the results
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[0].length; j++) {
                System.out.printf("%.3f  ", output[i][j]);
            }
            System.out.println();
        }
    }
}
