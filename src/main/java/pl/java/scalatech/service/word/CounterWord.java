package pl.java.scalatech.service.word;

import static java.lang.Long.compare;

import lombok.Data;

@Data
public class CounterWord implements Comparable<CounterWord> {
    private final String word;
    private final long repeat;
    private String translateWord;
    @Override
    public int compareTo(CounterWord o) {
        return compare(this.repeat, o.getRepeat());
    }
}
