package com.neonlab.common.searchcriteria;
import com.neonlab.common.models.searchCriteria.PageableSearchCriteria;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserSearchCriteria extends PageableSearchCriteria {
    /**
     *  filter by id
     */
    private String id;
    /**
     *  Filter by name
     */
    private String name;
    /**
     *  filter by email
     */
    private String email;
    /**
     *  filter by primaryPhoneNo
     */
    private String primaryPhoneNo;
    /**
     *  filter by secondaryPhoneNo
     */
    private String secondaryPhoneNo;


    @Builder(builderMethodName="userSearchCriteriaBuilder")
    public UserSearchCriteria(
            final String id,
            final String name,
            final String email,
            final String primaryPhoneNo,
            final String secondaryPhoneNo,
            final int perPage,
            final int pageNo,
            final String sortBy,
            final Sort.Direction sortDirection
    ){
        super(perPage, pageNo, sortBy, sortDirection);
        this.id = id;
        this.name = name;
        this.email = email;
        this.primaryPhoneNo = primaryPhoneNo;
        this.secondaryPhoneNo = secondaryPhoneNo;
    }
}
