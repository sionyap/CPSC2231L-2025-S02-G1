
package TextAnalysis;
import java.io.*;
import java.util.*;

public class LexiconLoader {

    public static HashSet<String> loadList(File f) throws Exception {
        HashSet<String> set = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim().toLowerCase();
                if (!line.isEmpty())
                    set.add(line);
            }
        }
        return set;
    }
    public static HashMap<String,Double> loadScored(File f) throws Exception {
        HashMap<String,Double> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length >= 2) {
                    try {
                        map.put(parts[0].toLowerCase(), Double.valueOf(parts[1]));
                    } catch (NumberFormatException e) {}
                }
            }
        }
        return map;
    }
}
