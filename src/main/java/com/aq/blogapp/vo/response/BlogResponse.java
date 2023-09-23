package com.aq.blogapp.vo.response;

import com.aq.blogapp.vo.DTO.BlogDTO;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BlogResponse {

    private List<BlogDTO> blogs;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private Long totalElements;
    private boolean lastPage;

}
