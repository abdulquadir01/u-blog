package com.aq.blogapp.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtils {

    public static  Pageable createSortedPageable(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

//      Be cautious of this statement
//      !! CAUTION !! TBD - find a way to initialize sort with some other value than null
        Sort sort = null;

        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else if (sortDir.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        }

//      !! CAUTION !! TBD - find a way to initialize sort with some other value than null
        return PageRequest.of(pageNumber, pageSize, sort);
    }

}
