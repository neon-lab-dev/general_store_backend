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
public class AddressSearchCriteria extends PageableSearchCriteria {
    /*
        filter by id
    */
    private String id;
    /**/

    private boolean admin=false;
    /*
        Filter by name
    */
    private String addressName;
    /*
        filter by addressLine1
    */
    private String addressLine1;
    /*
        filter by addressLine2
     */
    private String addressLine2;
    /*
        filter by landmark
    */
    private String landmark;
    /*
        filter by city
    */
    private String city;
    /*
        filter by state
    */
    private String state;
    /*
        filter by pincode
    */
    private String pincode;
    /*
        filter by userId
    */
    private String userId;
    /*
        filter by primaryAddress
    */
    private Boolean primaryAddress;

    @Builder(builderMethodName="addressSearchCriteriaBuilder")
    public AddressSearchCriteria(
            final String id,
            final String userId,
            final String addressName,
            final String addressLine1,
            final String addressLine2,
            final String landmark,
            final String city,
            final String state,
            final String pincode,
            final Boolean primaryAddress,
            final boolean admin,
            final int perPage,
            final int pageNo,
            final String sortBy,
            final Sort.Direction sortDirection
    ){
        super(perPage, pageNo, sortBy, sortDirection);
        this.addressName = addressName;
        this.id=id;
        this.addressLine1=addressLine1;
        this.addressLine2=addressLine2;
        this.city=city;
        this.pincode=pincode;
        this.landmark=landmark;
        this.state=state;
        this.userId=userId;
        this.primaryAddress=primaryAddress;
        this.admin=admin;
    }

}
