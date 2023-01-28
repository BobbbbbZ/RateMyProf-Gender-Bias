package cpen221.mp1.ratemyprofessor;

import cpen221.mp1.autocompletion.gui.In;

import cpen221.mp1.ngrams.NGrams;

import java.io.FileNotFoundException;
import java.text.BreakIterator;
import java.util.*;

public class DataAnalyzer {
    private final List<String> text = new ArrayList<String>();

    /**
     * Create an object to analyze a RateMyProfessor dataset
     *
     * @param dataSourceFileName the name of the file that contains the data
     * @throws FileNotFoundException if the file does not exist or is not found
     */
    public DataAnalyzer(String dataSourceFileName) throws FileNotFoundException {
        In input = new In(dataSourceFileName);
        input.readLine();
        for (String line = input.readLine(); line != null; line = input.readLine()) {
            text.add(line);
        }
    }

    private String[] getWords(String text) {
        ArrayList<String> words = new ArrayList<>();
        BreakIterator wb = BreakIterator.getWordInstance();
        wb.setText(text);
        int start = wb.first();
        for (int end = wb.next();
             end != BreakIterator.DONE;
             start = end, end = wb.next()) {
            String word = text.substring(start, end).toLowerCase();
            word = word.replaceAll("^\\s*\\p{Punct}+\\s*", "").replaceAll("\\s*\\p{Punct}+\\s*$", "");
            word = word.replaceAll(" ", "");
            if (!word.equals("")) {
                words.add(word);
            }
        }
        String[] wordsArray = new String[words.size()];
        words.toArray(wordsArray);
        return wordsArray;
    }

    /**
     * Obtain a histogram with the number of occurrences of the
     * query term in the RMP comments, categorized as men-low (ML),
     * women-low (WL), men-medium (MM), women-medium (WM),
     * men-high (MH), and women-high (WH).
     *
     * @param query the search term, which contains between one and three words
     *              and does not contain any strange symbols
     * @return the histogram with the number of occurrences of the
     * query term in the RMP comments, categorized as men-low (ML),
     * women-low (WL), men-medium (MM), women-medium (WM),
     * men-high (MH), and women-high (WH)
     */
    public Map<String, Long> getHistogram(String query) {
        Map<String, Long> searchMap = new HashMap<String, Long>();
        int queryLength = getWords(query).length;
        String queryWords = getWords(query)[0];
        if (queryLength > 0) {
            for (int i = 1; i < queryLength; i++) {
                String[] oGram = getWords(query);
                queryWords = String.join(" ", oGram);
            }
        }

        searchMap.put(new String("MH"), 0L);
        searchMap.put(new String("MM"), 0L);
        searchMap.put(new String("ML"), 0L);
        searchMap.put(new String("WH"), 0L);
        searchMap.put(new String("WM"), 0L);
        searchMap.put(new String("WL"), 0L);

        double rate;
        String gen;
        for (String s : text) {
            gen = s.split(",")[1];
            rate = Double.parseDouble(s.split(",")[0]);
            List<String> t = List.of(new String[]{s});
            NGrams te = new NGrams(t.toArray(new String[0]));
            if (te.getQueryNGrams().get(queryLength - 1).containsKey(queryWords)) {
                if (Objects.equals(gen, "M")) {
                    if (0 <= rate && rate <= 2) {
                        searchMap.put("ML", searchMap.get("ML") + te.getQueryNGrams().get(queryLength - 1).get(queryWords));
                    } else if (2 < rate && rate <= 3.5) {
                        searchMap.put("MM", searchMap.get("MM") + te.getQueryNGrams().get(queryLength - 1).get(queryWords));
                    } else if (3.5 < rate && rate <= 5) {
                        searchMap.put("MH", searchMap.get("MH") + te.getQueryNGrams().get(queryLength - 1).get(queryWords));
                    }
                } else if (Objects.equals(gen, "W")) {
                    if (0 <= rate && rate <= 2) {
                        searchMap.put("WL", searchMap.get("WL") + te.getQueryNGrams().get(queryLength - 1).get(queryWords));
                    } else if (2 < rate && rate <= 3.5) {
                        searchMap.put("WM", searchMap.get("WM") + te.getQueryNGrams().get(queryLength - 1).get(queryWords));
                    } else if (3.5 < rate && rate <= 5) {
                        searchMap.put("WH", searchMap.get("WH") + te.getQueryNGrams().get(queryLength - 1).get(queryWords));
                    }
                }
            }
        }
        return searchMap;
    }

    /**
     * Display the histogram data as a chart
     * @param histogram with entries for men-low (ML),
     * women-low (WL), men-medium (MM), women-medium (WM),
     * men-high (MH), and women-high (WH)
     */
    //   public void showHistogramChart(Map<String, Long> histogram) {
    //  This is an optional component but is
    //  instructive in that graphing may not be that hard!
    //  See the histogram package.
    //   }

}
