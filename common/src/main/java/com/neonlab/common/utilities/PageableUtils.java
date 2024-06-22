package com.neonlab.common.utilities;

import com.neonlab.common.models.searchCriteria.PageableSearchCriteria;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.Objects;
import java.util.Optional;

@UtilityClass
public class PageableUtils {


    public static Pageable createPageable(
            final int perPage,
            final int pageNo,
            final String sortBy,
            final Direction sortDirection
            ){
        var sort = Optional.ofNullable(sortBy)
                .filter(StringUtil::isNotBlank)
                .map(s -> Objects.equals(sortDirection, Direction.ASC) ?
                            Sort.by(s).ascending() :
                        Sort.by(s).descending())
                .orElse(Sort.unsorted());
        return PageRequest.of((pageNo - 1), perPage, sort);
    }


    public static Pageable createPageable(
            final PageableSearchCriteria searchCriteria
            ){
        return createPageable(
                searchCriteria.getPerPage(),
                searchCriteria.getPageNo(),
                searchCriteria.getSortBy(),
                searchCriteria.getSortDirection()
        );
    }

}
