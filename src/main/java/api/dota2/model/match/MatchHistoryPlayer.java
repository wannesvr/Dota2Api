package api.dota2.model.match;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MatchHistoryPlayer {
    @JsonProperty("account_id")
    private long accountId;

    @JsonProperty("player_slot")
    private int playerSlot;

    @JsonProperty("hero_id")
    private byte heroId;

}