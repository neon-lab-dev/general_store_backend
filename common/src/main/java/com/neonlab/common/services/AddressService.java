package com.neonlab.common.services;
import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.config.ConfigurationKeys;
import com.neonlab.common.dto.AddressDto;
import com.neonlab.common.entities.Address;
import com.neonlab.common.entities.User;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.models.PageableResponse;
import com.neonlab.common.repositories.AddressRepository;
import com.neonlab.common.searchcriteria.AddressSearchCriteria;
import com.neonlab.common.specifications.AddressSpecification;
import com.neonlab.common.utilities.ObjectMapperUtils;
import com.neonlab.common.utilities.PageableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.neonlab.common.config.ConfigurationKeys.POSITIVE_PINCODE_AVAILABLE;
import static com.neonlab.common.config.ConfigurationKeys.POSITIVE_PINCODE_LIST;


@Service
@RequiredArgsConstructor
@Slf4j
@Loggable
public class AddressService {

    private static final String DELETE_MSG="Address(s) has been deleted";

    @Autowired
    private AddressRepository addressRepository;

    private final UserService userService;

    private final SystemConfigService systemConfigService;

    public AddressDto addAddress(AddressDto addressDto) throws ServerException, InvalidInputException {
        Address address = new Address();
        User user = userService.getLoggedInUser();
        if(user.getAddresses().isEmpty()){
            addressDto.setPrimaryAddress(true);
        }
        else{
            if(addressDto.isPrimaryAddress()) {
                makeEveryPrimaryAddressFalse(user);
            }
        }
        ObjectMapperUtils.map(addressDto,address);
        address.setUser(user);
        user.getAddresses().add(address);
        addressRepository.save(address);
        return ObjectMapperUtils.map(address, AddressDto.class);
    }

    public String delete(List<String> ids) throws ServerException {
        for(String id: ids){
            var addressMaybe = addressRepository.findById(id);
            if (addressMaybe.isPresent()){
                if(addressMaybe.get().isPrimaryAddress()){
                    throw new ServerException("Before deleting primary address make another address as primary address.");
                }
                addressRepository.delete(addressMaybe.get());
            } else {
                log.warn("Address not found with id "+ id);
            }
        }
        return DELETE_MSG;
    }

    public void canAddressBeAdded(User user, AddressDto addressDto) throws InvalidInputException {
        if (user.getAddresses().size() >= 3){
            throw new InvalidInputException("You can add only 3 addresses to add new address delete existing address.");
        }
        var positivePincodeList = getPositivePincodeList();
        if (!CollectionUtils.isEmpty(positivePincodeList) && !positivePincodeList.contains(addressDto.getPincode())){
            throw new InvalidInputException("Sorry we are currently not available in the pincode "+ addressDto.getPincode());
        }
    }

    private List<String> getPositivePincodeList() throws InvalidInputException {
        var isPositivePincodeAvailable = Boolean.parseBoolean(systemConfigService.getSystemConfig(POSITIVE_PINCODE_AVAILABLE).getValue());
        if (isPositivePincodeAvailable){
            var pincodesString = systemConfigService.getSystemConfig(POSITIVE_PINCODE_LIST).getValue();
            return new ArrayList<>(Arrays.stream(pincodesString.split(",")).map(p -> p.trim()).toList());
        }
        return new ArrayList<>();
    }

    public boolean isSameAddressExist(AddressDto addressDto, User user) {
        for(Address address: user.getAddresses()){
            if(addressDto.getAddressLine1().equalsIgnoreCase(address.getAddressLine1())
                    && addressDto.isAddressLine2Equal(address.getAddressLine2())
                    && addressDto.getPincode().equalsIgnoreCase(address.getPinCode())
                    && addressDto.getCity().equalsIgnoreCase(address.getCity())
                    && addressDto.getState().equalsIgnoreCase(address.getState()))
            {
                return true;
            }
        }
        return false;
    }

    public AddressDto update(AddressDto addressDto) throws InvalidInputException, ServerException {
        var address = fetchById(addressDto.getId());
        if(addressDto.isPrimaryAddress()) makeEveryPrimaryAddressFalse(userService.getLoggedInUser());
        ObjectMapperUtils.map(addressDto, address);
        address = addressRepository.save(address);
        addressDto = ObjectMapperUtils.map(address, AddressDto.class);
        return addressDto;
    }

    public Address fetchById(String addressId) throws InvalidInputException {
        return addressRepository.findById(addressId).
                orElseThrow(()->new InvalidInputException("Address not found with id "+addressId));
    }

    public PageableResponse<AddressDto> fetch(AddressSearchCriteria searchCriteria) throws InvalidInputException {
        var pageable = PageableUtils.createPageable(searchCriteria);
        if(!searchCriteria.isAdmin()){
            String userId = userService.getLoggedInUser().getId();
            searchCriteria.setUserId(userId);
        }
        Page<Address> addresses = addressRepository.findAll(
                AddressSpecification.buildSearchCriteria(searchCriteria),
                pageable
        );
        var resultList = addresses.getContent().stream()
                .map(address -> {
                    try {
                        AddressDto retVal = null;
                        retVal = ObjectMapperUtils.map(address, AddressDto.class);
                        return retVal;
                    } catch (ServerException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
        return new PageableResponse<>(resultList, searchCriteria);
    }

    public void makeEveryPrimaryAddressFalse(User user){
        for(Address address : user.getAddresses()){
            if(address.isPrimaryAddress())  address.setPrimaryAddress(false);
        }
    }
}
