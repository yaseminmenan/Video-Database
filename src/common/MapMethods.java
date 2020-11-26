package common;

import java.util.*;
import java.util.stream.Collectors;

public class MapMethods {

    private static Map<String, Double> sortByDoubleValue(final Map<String,
            Double> map, final boolean order) {
        List<Map.Entry<String, Double>> list = new LinkedList<>(map.entrySet());

        // Sorting the list based on double values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue())
                : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey,
                Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }

    private static Map<String, Integer> sortByIntegerValue(final Map<String,
            Integer> map, final boolean order) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());

        // Sorting the list based on integer values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue())
                : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey,
                Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }

    /**
     * Sort a Double map by a given order
     */
    public final Map<String, Double> sortDoubleMap(final Map<String, Double> map,
                                                   final String order) {
        if (order.equals("asc")) {
           return sortByDoubleValue(map, true);
        }
        return sortByDoubleValue(map, false);
    }

    /**
     * Sort an Integer map by a given order
     */
    public final Map<String, Integer> sortIntegerMap(final Map<String, Integer> map,
                                                     final String order) {
        if (order.equals("asc")) {
            return sortByIntegerValue(map, true);
        }
        return sortByIntegerValue(map, false);
    }

    /**
     * Return a sorted List of map keys
     */
    public final List<String> getListInteger(final Map<String, Integer> map, final int queryNumber,
                                             final String order) {
        // Sort the map
        Map<String, Integer> sortedMap = sortIntegerMap(map, order);

        List<String> nameList = new ArrayList<>();

        int i = 0;
        // Add the first queryNumber keys to list
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            if (i < queryNumber) {
                nameList.add(entry.getKey());
                i++;
            } else if (i == queryNumber) {
                break;
            }
        }
        return nameList;
    }

    /**
     * Return a sorted List of map keys
     */
    public final List<String> getListDouble(final Map<String, Double> map, final int queryNumber,
                                             final String order) {
        // Sort the map
        Map<String, Double> sortedMap = sortDoubleMap(map, order);
        List<String> nameList = new ArrayList<>();
        int i = 0;

        // Add the first queryNumber keys to list
        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
            if (i < queryNumber) {
                nameList.add(entry.getKey());
                i++;
            } else if (i == queryNumber) {
                break;
            }
        }
        return nameList;
    }

    /**
     * Return a map of video genres
     */
    public final Map<String, Integer> getGenreMap() {
        Map<String, Integer> genreMap = new HashMap<>();
        genreMap.put("Action", 0);
        genreMap.put("Adventure", 0);
        genreMap.put("Drama", 0);
        genreMap.put("Comedy", 0);
        genreMap.put("Crime", 0);
        genreMap.put("Romance", 0);
        genreMap.put("War", 0);
        genreMap.put("History", 0);
        genreMap.put("Thriller", 0);
        genreMap.put("Mystery", 0);
        genreMap.put("Family", 0);
        genreMap.put("Horror", 0);
        genreMap.put("Fantasy", 0);
        genreMap.put("Science Fiction", 0);
        genreMap.put("Action & Adventure", 0);
        genreMap.put("Sci-Fi & Fantasy", 0);
        genreMap.put("Animation", 0);
        genreMap.put("Kids", 0);
        genreMap.put("Western", 0);
        genreMap.put("TV Movie", 0);
        return genreMap;
    }
}
