import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {

    String name;
    LocalDate date;
    int time;
    int difficulty;
    int progress;
    String importance;
    String building;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d-M-yy");

    public Task(String line) {
        String[] parts = line.split(";");
        if (parts.length < 7) { // Asegúrate de que hay al menos 7 campos
            throw new IllegalArgumentException("Invalid line format: " + line);
        }
        name = parts[0].trim();
        date = LocalDate.parse(parts[1].trim(), DATE_FORMATTER);
        time = Integer.parseInt(parts[2].trim());
        difficulty = Integer.parseInt(parts[3].trim());
        progress = Integer.parseInt(parts[4].trim());
        importance = parts[5].trim();
        building = parts[6].trim();
    }


    public Task(String building, String importance, int progress, int difficulty, int time, LocalDate date, String name) {
        this.building = building;
        this.importance = importance;
        this.progress = progress;
        this.difficulty = difficulty;
        this.time = time;
        this.date = date;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String toString() {
        return name + ";" + date + ";" + time + ";" + difficulty + ";" + progress + ";" + importance;
    }
}
