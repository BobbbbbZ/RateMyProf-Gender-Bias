package cpen221.mp1;

import cpen221.mp1.sentimentanalysis.SentimentAnalyzer;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import static org.junit.jupiter.api.Assertions.*;

public class Task4Tests {
    @Test
    public void testRating1() {
        try {
            SentimentAnalyzer rmp_sa = new SentimentAnalyzer("data/ratemyprofessor_data.txt");
            assertEquals(1f, rmp_sa.getPredictedRating("oh no it was so difficult"));
        }
        catch (FileNotFoundException fnf) {
            fail("Data file is not in the right place!");
        }
    }

    @Test
    public void testRating2() {
        try {
            SentimentAnalyzer rmp_sa = new SentimentAnalyzer("data/ratemyprofessor_data.txt");
            assertEquals(2f, rmp_sa.getPredictedRating("soft voice sit in the front"));
        }
        catch (FileNotFoundException fnf) {
            fail("Data file is not in the right place!");
        }
    }

    @Test
    public void testRating3() {
        try {
            SentimentAnalyzer rmp_sa = new SentimentAnalyzer("data/ratemyprofessor_data.txt");
            assertEquals(3f, rmp_sa.getPredictedRating("slept but okay"));
        }
        catch (FileNotFoundException fnf) {
            fail("Data file is not in the right place!");
        }
    }


    @Test
    public void testRating4() {
        try {
            SentimentAnalyzer rmp_sa = new SentimentAnalyzer("data/ratemyprofessor_data.txt");
            assertEquals(4f, rmp_sa.getPredictedRating("good lecturer but assigns a lot of work"));
        }
        catch (FileNotFoundException fnf) {
            fail("Data file is not in the right place!");
        }
    }

    @Test
    public void testRating5() {
        try {
            SentimentAnalyzer rmp_sa = new SentimentAnalyzer("data/ratemyprofessor_data.txt");
            assertEquals(5f, rmp_sa.getPredictedRating("very fun and kind"));
        }
        catch (FileNotFoundException fnf) {
            fail("Data file is not in the right place!");
        }
    }

    @Test
    public void testRating6() {
        try {
            SentimentAnalyzer rmp_sa = new SentimentAnalyzer("data/ratemyprofessor_data.txt");
            assertEquals(5f, rmp_sa.getPredictedRating("very fun and kind ohhhhhh"));
        }
        catch (FileNotFoundException fnf) {
            fail("Data file is not in the right place!");
        }
    }

    @Test
    public void testRating7() {
        try {
            SentimentAnalyzer rmp_sa = new SentimentAnalyzer("data/ratemyprofessor_data.txt");
            assertEquals(5f, rmp_sa.getPredictedRating("VERY FUN AND KIND OHHHHHH"));
        }
        catch (FileNotFoundException fnf) {
            fail("Data file is not in the right place!");
        }
    }
    @Test
    public void testBadFile() {
        try {
            SentimentAnalyzer rmp_sa = new SentimentAnalyzer("data/ratmyprofessor_data.txt");
        }
        catch (Exception fnf) {
            // no file should exist so ...
            // this is an alternative approach to test
            // for exceptions, although assertThrows is
            // better
            assertTrue(true);
        }
    }
}