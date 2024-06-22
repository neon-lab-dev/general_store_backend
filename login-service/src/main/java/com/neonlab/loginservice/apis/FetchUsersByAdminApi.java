package com.neonlab.loginservice.apis;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.searchcriteria.UserSearchCriteria;
import com.neonlab.common.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class FetchUsersByAdminApi {
    @Autowired
    private UserService userService;

    public ApiOutput<?> fetch(UserSearchCriteria searchCriteria) {
        return new ApiOutput<>(HttpStatus.OK.value(), null, userService.fetchByAdmin(searchCriteria));
    }
}
