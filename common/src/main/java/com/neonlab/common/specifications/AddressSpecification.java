package com.neonlab.common.specifications;

import com.neonlab.common.entities.Address;
import com.neonlab.common.entities.User;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.searchcriteria.AddressSearchCriteria;
import com.neonlab.common.services.UserService;
import com.neonlab.common.utilities.StringUtil;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import java.util.Objects;
import static com.neonlab.common.constants.AddressEntityConstants.*;
import static com.neonlab.common.constants.GlobalConstants.Symbols.PERCENTAGE;


public class AddressSpecification {

    private static UserService userService;

    public static Specification<Address> buildSearchCriteria(final AddressSearchCriteria searchCriteria) {
        var retVal = Specification.<Address>where(null);
        if (!searchCriteria.isAdmin()){
            retVal = retVal.and(filterByUserId(searchCriteria.getUserId()));
        }
        if (!StringUtil.isNullOrEmpty(searchCriteria.getId())){
            retVal = retVal.and(filterById(searchCriteria.getId()));
        }
        if(!StringUtil.isNullOrEmpty(searchCriteria.getCity())){
            retVal=retVal.and(filterByCity(searchCriteria.getCity()));
        }
        if(!StringUtil.isNullOrEmpty(searchCriteria.getState())){
            retVal=retVal.and(filterByState(searchCriteria.getState()));
        }
        if(!StringUtil.isNullOrEmpty(searchCriteria.getPincode())){
            retVal=retVal.and(filterByPincode(searchCriteria.getPincode()));
        }
        if(!StringUtil.isNullOrEmpty(searchCriteria.getLandmark())){
            retVal=retVal.and(filterByPincode(searchCriteria.getLandmark()));
        }
        if(!StringUtil.isNullOrEmpty(searchCriteria.getAddressName())){
            retVal=retVal.and(filterByName(searchCriteria.getAddressName()));
        }
        if(!StringUtil.isNullOrEmpty(searchCriteria.getAddressLine1())){
            retVal=retVal.and(filterByAddressLine1(searchCriteria.getAddressLine1()));
        }
        if(!StringUtil.isNullOrEmpty(searchCriteria.getAddressLine2())){
            retVal=retVal.and(filterByAddressLine2(searchCriteria.getAddressLine2()));
        }
        if(!StringUtil.isNullOrEmpty(searchCriteria.getUserId())){
            retVal=retVal.and(filterByUserId(searchCriteria.getUserId()));
        }
        if(Objects.nonNull(searchCriteria.getPrimaryAddress())){
            retVal=retVal.and(AddressSpecification.filterByPrimaryAddress(searchCriteria.getPrimaryAddress()));
        }
        if (!StringUtil.isNullOrEmpty(searchCriteria.getLandmark())){
            retVal = retVal.and(filterByLandmark(searchCriteria.getLandmark()));
        }
        return retVal;
    }

    private static Specification<Address> filterById(final String id){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ID), id)
        );
    }
    private static Specification<Address> filterByCity(final String city){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(CITY),withLikePattern(city))
        );
    }
    private static Specification<Address> filterByState(final String state){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(STATE),withLikePattern(state))
        );
    }

    private static Specification<Address> filterByPincode(final String pincode){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(PINCODE),pincode)
        );
    }
    private static Specification<Address> filterByLandmark(final String landmark){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(LANDMARK),withLikePattern(landmark))
        );
    }

    private static Specification<Address> filterByName(final String addressName){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(ADDRESS_NAME),withLikePattern(addressName))
        );
    }

    private static Specification<Address> filterByAddressLine1(final String addressLine1){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(ADDRESS_LINE_1),withLikePattern(addressLine1))
        );
    }
    private static Specification<Address> filterByAddressLine2(final String addressLine2){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(ADDRESS_LINE_2),withLikePattern(addressLine2))
        );
    }

    private static Specification<Address> filterByUserId(final String user_id){
        return ((root, query, criteriaBuilder) ->
        {
            Join<Address, User> addressUserJoin = root.join(USER);
            return criteriaBuilder.equal(addressUserJoin.get(ID), user_id);
        }
        );

    }

    private static Specification<Address> filterByPrimaryAddress(Boolean primaryAddress){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(PRIMARY_ADDRESS),primaryAddress)
        );
    }

    private static String withLikePattern(String str){
        return PERCENTAGE + str + PERCENTAGE;
    }
}
