package com.github.wannesvr.core.model.league;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LeagueList {
    @JsonProperty("leagues")
    private List<LeagueDetail> leagues;
}
