package com.aq.blogapp.payload.response;

import com.aq.blogapp.payload.DTO.BlogDTO;
import com.aq.blogapp.payload.DTO.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class UsersResponse {

    private List<UserDTO> users;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private Long totalElements;
    private boolean lastPage;

}
