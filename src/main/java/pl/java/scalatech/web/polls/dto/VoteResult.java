package pl.java.scalatech.web.polls.dto;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteResult {
private int totalVotes;
private Collection<OptionCount> results;

}