package cpen221.mp1.searchterm;

import java.util.Comparator;

public class SearchTerm implements Comparable<SearchTerm> {

    public final String query;
    public final long weight;

    /**
     * Initializes a term with the given query string and weight.
     *
     * @param query  the query for the search, is not empty
     * @param weight the weight associated with the query string
     */
    public SearchTerm(String query, long weight) {
        this.query = query;
        this.weight = weight;
    }

    /**
     * Obtain a comparator for comparing two search terms based on weight.
     *
     * @return a comparator that compares two search terms using their weight
     */
    public static Comparator<SearchTerm> byReverseWeightOrder() {
        return new Comparator<SearchTerm>() {
            @Override
            public int compare(SearchTerm o1, SearchTerm o2) {
                long temp = o1.weight - o2.weight;
                return temp < 0 ? -1 : (temp > 0 ? 1 : 0);
            }
        };
    }

    /**
     * Obtain a comparator for lexicographic ordering.
     *
     * @return a comparator that compares two search terms lexicographically
     */
    public static Comparator<SearchTerm> byPrefixOrder() {
        return new Comparator<SearchTerm>() {
            @Override
            public int compare(SearchTerm o1, SearchTerm o2) {
                return o2.query.compareTo(o1.query);
            }
        };
    }

    /**
     * Compares the two terms in lexicographic order by query.
     */
    @Override
    public int compareTo(SearchTerm other) {
        return SearchTerm.byPrefixOrder().compare(this, other);
    }

    /**
     * Compares the two terms in weight order
     */
    public int compareWeight(SearchTerm other) {
        return SearchTerm.byReverseWeightOrder().compare(this, other);
    }

    //

    /**
     * Returns a string representation of this SearchTerm.
     *
     * @return a string representation of this term in the following format:
     * the weight, followed by a tab, followed by the query.
     */
    public String toString() {
        return String.format("%-10d\t%s", this.weight, this.query);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SearchTerm) {
            SearchTerm otherST = (SearchTerm) other;
            return (this.query.equals(otherST.query) && this.weight == otherST.weight);
        } else {
            return false;
        }
    }

}
