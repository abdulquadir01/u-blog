package com.aq.blogapp.vo.response;

import com.aq.blogapp.vo.DTO.UserDTO;
import lombok.*;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UsersResponse {

    private List<UserDTO> users;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private Long totalElements;
    private boolean lastPage;

}
