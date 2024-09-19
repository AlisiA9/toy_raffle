import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Random;

public class ToyRaffle {
    private PriorityQueue<Toy> toyQueue;

    public ToyRaffle() {
        toyQueue = new PriorityQueue<>((a, b) -> Integer.compare(b.getWeight(), a.getWeight()));
    }

    public void addToy(Toy toy) {
        toyQueue.add(toy);
    }

    public String drawToy() {
        if (toyQueue.isEmpty()) {
            return "No toys available for drawing.";
        }
        Random random = new Random();
        int totalWeight = toyQueue.stream().mapToInt(Toy::getWeight).sum();
        int randomValue = random.nextInt(totalWeight);
        
        int cumulativeWeight = 0;
        for (Toy toy : toyQueue) {
            cumulativeWeight += toy.getWeight();
            if (randomValue < cumulativeWeight) {
                return "Drawn Toy: " + toy.getName() + " (ID: " + toy.getId() + ")";
            }
        }
        return "No toy drawn.";
    }

    public void saveResults(int draws) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"))) {
            for (int i = 0; i < draws; i++) {
                writer.write(drawToy());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ToyRaffle raffle = new ToyRaffle();

        // Добавление игрушек
        raffle.addToy(new Toy("1", "Teddy Bear", 5));
        raffle.addToy(new Toy("2", "Race Car", 3));
        raffle.addToy(new Toy("3", "Doll", 2));

        // Проведение 10 розыгрышей и сохранение результата в файл
        raffle.saveResults(10);
        System.out.println("Results saved to results.txt");
    }
}