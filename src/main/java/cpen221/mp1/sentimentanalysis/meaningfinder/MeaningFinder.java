package cpen221.mp1.sentimentanalysis.meaningfinder;

public class MeaningFinder {

    // Use an array of strings to build the initial model
    public MeaningFinder(String[] trainingData) {
        // TODO: Implement this method
    }

    // Add a new piece of text (one or more sentences)
    // to improve our model. Each such text helps us
    // refine the marker vectors.
    // Although you do not have to reason about this for the
    // mini-project, what is the impact of adding duplicates
    // of some texts?
    public void addTrainingData(String text) {
        // TODO: Implement this method
    }

    // Compute the cosine similarity between text1 and text2, each of
    // which is represented by an array of Strings (for instance,
    // each element in the array could be a line of sentence from a book).
    public static double cosineSimilarity(String[] text1, String[] text2) {
        // TODO: Implement this method
        return -1;
    }

    // Assuming this instance has been trained,
    // return an element from choices that is closest in
    // meaning to word.
    public String predictedSynonyn(String word, String[] choices) {
        // TODO: Implement this method
        return "";
    }

}