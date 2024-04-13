package org.fexisaf.crimerecordmanagementsystem.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.fexisaf.crimerecordmanagementsystem.model.PunishmentModel;

// Custom validation constraint for fine amount when punishment type is "fine"
public class FineAmountValidator implements ConstraintValidator<FineAmountRequired, PunishmentModel> {

    @Override
    public void initialize(FineAmountRequired constraintAnnotation) {
    }

    @Override
    public boolean isValid(PunishmentModel punishmentModel, ConstraintValidatorContext context) {
        if ("fine".equalsIgnoreCase(punishmentModel.getPunishmentType())) {
            if (punishmentModel.getFineAmount() == null || punishmentModel.getFineAmount() <= 0) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Fine amount is required when punishment type is 'fine'")
                        .addPropertyNode("fineAmount")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}