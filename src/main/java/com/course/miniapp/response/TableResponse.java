package com.course.miniapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TableResponse {

    @JsonProperty("tables_result")
    private TablesResult tablesResult;

    @Data
    public static class TablesResult {
        private List<Object> header;
        private List<Body> body;
    }

    @Data
    public static class Body {

        @JsonProperty("col_end")
        private Integer colEnd;

        @JsonProperty("row_end")
        private Integer rowDnd;

        @JsonProperty("row_start")
        private Integer rowStart;

        @JsonProperty("col_start")
        private Integer colStart;

        private String words;
    }
}
