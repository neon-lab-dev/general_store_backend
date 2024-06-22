package com.neonlab.common.specifications;
import com.neonlab.common.entities.User;
import com.neonlab.common.searchcriteria.UserSearchCriteria;
import com.neonlab.common.utilities.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import static com.neonlab.common.constants.GlobalConstants.Symbols.PERCENTAGE;
import static com.neonlab.common.constants.UserEntityConstant.*;



public class UserSpecification {

    public static Specification<User> buildSearchCriteria(final UserSearchCriteria searchCriteria){
        var retVal = Specification.<User>where(null);
        if(!StringUtil.isNullOrEmpty(searchCriteria.getId())){
            retVal = retVal.and(filterById(searchCriteria.getId()));
        }
        if(!StringUtil.isNullOrEmpty(searchCriteria.getName())){
            retVal = retVal.and(filterByNameLike(searchCriteria.getName()));
        }
        if(!StringUtil.isNullOrEmpty(searchCriteria.getEmail())){
            retVal = retVal.and(filterByEmailLike(searchCriteria.getEmail()));
        }
        if(!StringUtil.isNullOrEmpty(searchCriteria.getPrimaryPhoneNo())){
            retVal = retVal.and(filterByPrimaryPhoneNo(searchCriteria.getPrimaryPhoneNo()));
        }
        if(!StringUtil.isNullOrEmpty(searchCriteria.getSecondaryPhoneNo())){
            retVal = retVal.and(filterBySecondaryPhoneNo(searchCriteria.getSecondaryPhoneNo()));
        }
        return retVal;
    }

    private static Specification<User> filterById(final String id){
        return (((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ID),id))
        );
    }

    private static Specification<User> filterByPrimaryPhoneNo(final String primaryPhoneNo){
        return (((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(PRIMARY_PHONE_NO),primaryPhoneNo))
        );
    }

    private static Specification<User> filterBySecondaryPhoneNo(final String secondaryPhoneNo){
        return (((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(SECONDARY_PHONE_NO),secondaryPhoneNo))
        );
    }

    private static Specification<User> filterByNameLike(final String name){
        return (((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(NAME),withLikePattern(name)))
        );
    }

    private static Specification<User> filterByEmailLike(final String email){
        return (((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(EMAIL),withLikePattern(email)))
        );
    }

    private static String withLikePattern(String str){
        return PERCENTAGE + str + PERCENTAGE;
    }
}
