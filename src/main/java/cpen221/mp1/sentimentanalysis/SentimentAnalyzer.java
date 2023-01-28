package cpen221.mp1.sentimentanalysis;

import cpen221.mp1.ngrams.NGrams;
import cpen221.mp1.autocompletion.gui.In;

import java.io.FileNotFoundException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SentimentAnalyzer {
    /**
     * the bag of words for each rate
     * a map where its key is the rate and its value is a map where
     * its key is every single word appear in the comment of this rate
     * and its value is the count it appears
     */
    private final Map<Float, Map<String, Long>> Bag = new HashMap<>();
    /**
     * the bag of words for the whole file
     * a map where its key is every single word
     * and its value is the count that single word appears
     */
    private Map<String, Long> allCommentNgram = new HashMap<>();
    /**
     * a map where its key is the rate
     * and the value is the count that the rate appears
     */
    private final Map<Float, Long> rateMap = new HashMap<>();

    /**
     * Get words from the text and put them into arraylist
     *
     * @param text origin text provided, is not null or the empty string
     * @return a list of words
     */
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
     * Get training data with operate with data from file
     *
     * @param filename the name of file, is not null and is not empty string
     */
    public SentimentAnalyzer(String filename) throws FileNotFoundException {
        // filename is a String
        In input = new In(filename);
        input.readLine();
        float rate;
        List<String> allComment = new ArrayList<String>();
        Map<Float, List<String>> RateComment = new HashMap<Float, List<String>>();
        for (String line = input.readLine(); line != null; line = input.readLine()) {
            String comment;
            comment = line.split(",")[2];
            rate = Float.parseFloat(line.split(",")[0]);
            if (!rateMap.containsKey(rate)) {
                rateMap.put(rate, 1L);
            } else {
                rateMap.put(rate, rateMap.get(rate) + 1);
            }
            allComment.add(comment);
            if (!RateComment.containsKey(rate)) {
                RateComment.put(rate, new ArrayList<String>());
                RateComment.get(rate).add(comment);
            } else {
                RateComment.get(rate).add(comment);
            }
        }
        for (Float r : RateComment.keySet()) {
            NGrams bag = new NGrams(RateComment.get(r).toArray(new String[0]));
            Map<String, Long> rateBag = bag.getBagOfWords();
            Bag.put(r, rateBag);
        }
        NGrams ac = new NGrams(allComment.toArray(new String[0]));
        allCommentNgram = ac.getBagOfWords();
    }

    /**
     * Predict the rate of given reviewText
     *
     * @param reviewText the context of the review,
     *                   is not null, is not empty string
     *                   and all words contained should be
     *                   proper words (avoid strange symbols)
     * @return the predicting rate of the reviewText
     */
    public float getPredictedRating(String reviewText) {
        int queryLength = getWords(reviewText).length;
        String reviewComment = getWords(reviewText)[0];
        if (queryLength > 0) {
            for (int i = 1; i < queryLength; i++) {
                String[] oGram = getWords(reviewText);
                reviewComment = String.join(" ", oGram);
            }
        }
        List<String> review = List.of(new String[]{reviewComment});
        NGrams re = new NGrams(review.toArray(new String[0]));
        Map<String, Long> reviewWords = re.getBagOfWords();
        float rate = 0.0F;
        float pRate = 0.0F;
        float pBagOfWords = 1.0F;
        long allWordsCount = 0L;
        long allRatingCount = 0L;
        for (Long r : rateMap.values()) {
            allRatingCount += r;
        }
        for (Long c : allCommentNgram.values()) {
            allWordsCount += c;
        }
        for (String t : reviewWords.keySet()) {
            if (!allCommentNgram.containsKey(t)) {
                pBagOfWords *= 1 / ((float) (allWordsCount + 1));
            } else {
                pBagOfWords *= (allCommentNgram.get(t) + 1) / ((float) (allWordsCount) + 1);
            }
        }

        for (Float r : rateMap.keySet()) {
            long wordsInRate = 0L;
            for (Long c : Bag.get(r).values()) {
                wordsInRate += c;
            }
            float pBagOfWordRating = 1.0F;
            for (String t : reviewWords.keySet()) {
                if (!Bag.get(r).containsKey(t)) {
                    pBagOfWordRating *= 1 / ((float) (wordsInRate + 1));
                } else {
                    pBagOfWordRating *= (Bag.get(r).get(t) + 1) / ((float) (wordsInRate + 1));
                }
            }
            float pRating = rateMap.get(r) / ((float) allRatingCount);
            float pRatingBagOfWords = pBagOfWordRating * pRating / pBagOfWords;
            if (pRate < pRatingBagOfWords) {
                pRate = pRatingBagOfWords;
                rate = r;
            }
        }
        return rate;
    }
}
