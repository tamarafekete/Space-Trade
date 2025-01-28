package projekt;

import projekt.Farms.Farm;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Inventory {
    private Integer money;
    private Boolean[] planets;
    private Boolean[][] fields;
    private HashMap<String, Integer> crops;

    public Inventory(Integer money, Boolean[] planets, Boolean[][] fields, HashMap<String, Integer> crops) throws IOException {
        this.money = money;
        this.planets = planets;
        this.fields = fields;
        if (crops != null) {
            this.crops = crops;
        } else {
            this.crops = new HashMap<>();
            this.crops.put("Wheat", 0);
            this.crops.put("Potato", 0);
            this.crops.put("Aerobranch", 0);
            this.crops.put("Lumiflora", 0);
            this.crops.put("Graviroot", 0);
            this.crops.put("Vaporweed", 0);
            this.crops.put("Spikemaws", 0);
            this.crops.put("Shadestalker", 0);
        }
    }

    public Inventory(Inventory original) {
        this.money = original.money;
        this.planets = original.planets != null ? original.planets.clone() : null;
        if (original.fields != null) {
            this.fields = new Boolean[original.fields.length][];
            for (int i = 0; i < original.fields.length; i++) {
                this.fields[i] = original.fields[i].clone();
            }
        }
        if (original.crops != null) {
            this.crops = new HashMap<>();
            this.crops.putAll(original.crops);
        }
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Boolean[] getPlanets() {
        return planets;
    }

    public void setPlanets(Boolean[] planets) {
        this.planets = planets;
    }

    public void setPlanetI(Boolean planet, int i) {
        planets[i] = planet;
    }

    public void setFieldI(Boolean field, int j, Farm farm) {
        int i;
        if (farm.getName().equals("Farm1")) {
            i = 0;
        } else if (farm.getName().equals("Farm2")) {
            i = 1;
        } else if (farm.getName().equals("Farm3")) {
            i = 2;
        } else {
            i = 3;
        }
        fields[i][j] = field;
    }

    public Boolean getFieldI(int j, Farm farm) {
        int i;
        if (farm.getName().equals("Farm1")) {
            i = 0;
        } else if (farm.getName().equals("Farm2")) {
            i = 1;
        } else if (farm.getName().equals("Farm3")) {
            i = 2;
        } else {
            i = 3;
        }
        return fields[i][j];
    }

    public void setFields(Boolean[][] fields) {
        this.fields = fields;
    }

    public void setCrops(String crop, int value) {
        crops.replace(crop, value);
    }

    public void setCrops(HashMap<String, Integer> crops) {
        this.crops = crops;
    }

    public int getCrop(String crop) {
        return crops.get(crop);
    }

    public void saveToFile(File filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(money + "\n");
            String planetsStream = Arrays.stream(planets)
                    .map(Object::toString)
                    .collect(Collectors.joining(" "));
            writer.write(planetsStream + "\n");
            String fieldsStream = Arrays.stream(fields)
                    .map(field -> Arrays.stream(field)
                            .map(Object::toString)
                            .collect(Collectors.joining(" "))
                    )
                    .collect(Collectors.joining("\n"));
            writer.write(fieldsStream + '\n');
            String cropsStream = crops.entrySet().stream()
                    .map(entry -> entry.getKey() + " " + entry.getValue())
                    .collect(Collectors.joining("\n"));
            writer.write(cropsStream + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Inventory(String filePath) throws FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String> lines = reader.lines().toList();
            setMoney(Integer.parseInt(lines.get(0).trim()));

            Boolean[] planets = Stream.of(lines.get(1).split(" "))
                    .map(Boolean::parseBoolean)
                    .toArray(Boolean[]::new);
            setPlanets(planets);

            Boolean[][] fields = lines.subList(2, 6).stream()
                    .map(line -> Stream.of(line.split(" "))
                            .map(Boolean::parseBoolean)
                            .toArray(Boolean[]::new))
                    .toArray(Boolean[][]::new);
            setFields(fields);

            HashMap<String, Integer> crops = lines.subList(6, lines.size()).stream()
                    .map(line -> line.split(" ", 2))
                    .collect(Collectors.toMap(
                            parts -> parts[0],
                            parts -> Integer.parseInt(parts[1]),
                            (existing, replacement) -> existing,
                            HashMap::new
                    ));
            setCrops(crops);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(this);
    }

    private int findEmptyFieldRow() {
        for (int i = 0; i < this.fields.length; i++) {
            if (this.fields[i] == null) return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Money: " + money +
                "\nPlanets: " + Arrays.toString(planets) +
                "\nFields: " + Arrays.deepToString(fields) +
                "\nCrops: " + crops;
    }
}
