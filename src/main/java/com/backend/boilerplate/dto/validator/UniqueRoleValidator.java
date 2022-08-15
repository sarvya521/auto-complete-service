package com.backend.boilerplate.dto.validator;

import com.backend.boilerplate.constant.AdminFeature;
import com.backend.boilerplate.dto.UpdateRoleDto;
import com.backend.boilerplate.repository.ModuleFeaturesRepository;
import com.backend.boilerplate.repository.RoleRepository;
import com.sp.boilerplate.commons.constant.FeatureAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class UniqueRoleValidator implements ConstraintValidator<UniqueResource, UpdateRoleDto> {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModuleFeaturesRepository moduleFeaturesRepository;

    @SuppressWarnings("squid:S1192")
    @Transactional
    @Override
    public boolean isValid(UpdateRoleDto updateRoleDto, ConstraintValidatorContext context) {
        Map<String, List<FeatureAction>> featureClaims = updateRoleDto.getFeatureClaims();
        final boolean emptyClaims = featureClaims.values().stream()
            .allMatch(featureActions -> Objects.isNull(featureActions) || featureActions.isEmpty());
        if (emptyClaims) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("1074")
                .addPropertyNode("featureClaims")
                .addConstraintViolation();
            return false;
        }

        final boolean anyRestrictedClaim = Stream.of(AdminFeature.Restricted.values())
            .anyMatch(feature -> featureClaims.containsKey(feature.getName()));
        if (anyRestrictedClaim) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("1095")
                .addPropertyNode("featureClaims")
                .addConstraintViolation();
            return false;
        }

        UUID uuid = updateRoleDto.getUuid();
        if (!roleRepository.existsByUuid(uuid)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("1009")
                .addPropertyNode("uuid")
                .addConstraintViolation();
            return false;
        }

        Optional<Integer> countFeatureClaimsOptional =
            moduleFeaturesRepository.countByNameIn(new ArrayList<>(updateRoleDto.getFeatureClaims().keySet()));
        if (countFeatureClaimsOptional.isPresent()
            && ((countFeatureClaimsOptional.get() < 1)
            || (countFeatureClaimsOptional.get() != updateRoleDto.getFeatureClaims().size()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("1075")
                .addPropertyNode("featureClaims")
                .addConstraintViolation();
            return false;
        }

        return true;
    }
}