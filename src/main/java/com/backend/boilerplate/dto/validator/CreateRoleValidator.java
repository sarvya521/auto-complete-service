package com.backend.boilerplate.dto.validator;

import com.backend.boilerplate.constant.AdminFeature;
import com.backend.boilerplate.constant.FeatureAction;
import com.backend.boilerplate.constant.Role;
import com.backend.boilerplate.dao.ModuleFeaturesRepository;
import com.backend.boilerplate.dto.CreateRoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;


/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Component
public class CreateRoleValidator implements ConstraintValidator<ValidCreateResource, CreateRoleDto> {

    @Autowired
    private ModuleFeaturesRepository moduleFeaturesRepository;

    @SuppressWarnings({"squid:S1192", "squid:S3655"})
    @Transactional
    @Override
    public boolean isValid(CreateRoleDto createRoleDto, ConstraintValidatorContext context) {
        Map<String, List<FeatureAction>> featureClaims = createRoleDto.getFeatureClaims();
        final boolean emptyClaims = featureClaims.values().stream()
            .allMatch(featureActions -> Objects.isNull(featureActions) || featureActions.isEmpty());
        if (emptyClaims) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("1074")
                .addPropertyNode("featureClaims")
                .addConstraintViolation();
            return false;
        }

        String roleName = createRoleDto.getName();
        if (Role.fromName(roleName).isPresent()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("1105")
                .addPropertyNode("name")
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

        Optional<Integer> countFeatureClaimsOptional =
            moduleFeaturesRepository.countByNameIn(new ArrayList<>(featureClaims.keySet()));
        if (countFeatureClaimsOptional.isPresent()
            && ((countFeatureClaimsOptional.get() < 1)
            || (countFeatureClaimsOptional.get() != featureClaims.size()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("1075")
                .addPropertyNode("featureClaims")
                .addConstraintViolation();
            return false;
        }

        return true;
    }
}