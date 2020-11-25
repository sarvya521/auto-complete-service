package com.backend.boilerplate;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.Set;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Data
public class TestPageable {
    private final Integer defaultPageSize;
    private final Integer maxPageSize;
    private final String defaultSort;
    private final Set<String> sortParameters;

    public Pageable getPageable(final Optional<Integer> pageNoOptional, final Optional<Integer> pageSizeOptional,
                                final Optional<String> sortByOptional, final boolean isAscendingOrder) {
        int pageNo = pageNoOptional.orElse(0);
        int pageSize = pageSizeOptional.orElse(defaultPageSize);
        if (pageSize > maxPageSize) {
            pageSize = maxPageSize;
        }
        String sortBy = null;
        if (sortByOptional.isPresent()) {
            sortBy = sortByOptional.get();
        }
        if (!sortParameters.contains(sortBy)) {
            sortBy = defaultSort;
        }
        if (isAscendingOrder) {
            return PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else {
            return PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }
    }
}
