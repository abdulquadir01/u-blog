package com.aq.blogapp.payload.response;

import com.aq.blogapp.payload.DTO.BlogDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class BlogsResponse {

    private List<BlogDTO> blogs;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private Long totalElements;
    private boolean lastPage;

}
