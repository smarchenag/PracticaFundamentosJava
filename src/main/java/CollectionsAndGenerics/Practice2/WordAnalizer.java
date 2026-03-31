package CollectionsAndGenerics.Practice2;

import java.util.*;

public class WordAnalizer {
    private String texto;

    public WordAnalizer(String texto) {
        this.texto = texto;
    }

    /*public List<String> processWords () {
        String textoLimpio = texto.replaceAll("[^a-zA-ZáéíóúñÁÉÍÓÚÑ]","");
        return List.of(textoLimpio.split("\\s+"));
    }*/

    public List<String> processWords () {
        String[] words = texto.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].toLowerCase();
            words[i] = words[i].replaceAll("[^0-9a-zA-ZáéíóúñÁÉÍÓÚÑ]","");
        }
        return Arrays.asList(words);
    }

    public Map<String,Integer> countFrequency () {
        List<String> words = processWords();
        Map<String,Integer> map = new HashMap<>();
        for (String word : words) {
//            Integer count = map.getOrDefault(word,0);
//            map.put(word,++count);
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else  {
                map.put(word, 1);
            }
        }
        return map;
    }

    public List<Map.Entry<String,Integer>> topN (int n) {
        Map<String,Integer> map = countFrequency();
        return map.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())).limit(n).toList();
    }

    public Set<String> uniqueWords () {
        Set<String> words = new HashSet<>();
        countFrequency().entrySet().stream().forEach(entry -> {
            if (entry.getValue() == 1) {
                words.add(entry.getKey());
            }
        });
        return words;
    }

    public Map<Integer,List<String>> groupByFrequency () {
        Map<String,Integer> frecuencies =  countFrequency();
        Map<Integer,List<String>> result = new HashMap<>();

        for (Map.Entry<String,Integer> entry : frecuencies.entrySet()) {
            String word = entry.getKey();
            Integer freq = entry.getValue();
            List<String> words = result.computeIfAbsent(freq, k -> new ArrayList<>());
            words.add(word);
            result.put(freq, words);
        }
        return result;
    }
}
