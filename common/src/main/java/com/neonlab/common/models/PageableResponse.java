package com.neonlab.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neonlab.common.models.searchCriteria.PageableSearchCriteria;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageableResponse<T> {
    private int perPage;
    private int pageNo;
    private String sortBy;
    private String sortDirection;
    private List<T> content;
    private Integer count;

    public PageableResponse(
            final List<T> resultList,
            final PageableSearchCriteria searchCriteria
    ){
        this.perPage = searchCriteria.getPerPage();
        this.pageNo = searchCriteria.getPageNo();
        this.sortBy = searchCriteria.getSortBy();
        this.sortDirection = searchCriteria.getSortDirection().name();
        this.content = resultList;
        this.count = resultList.size();
    }

}
