package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ItemValidatorV2 implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        BindingResult bindingResult = (BindingResult) errors;
        Item item = (Item) target;


        System.out.println("ItemValidatorV2.validate");
        if (item.getQuantity() != null && item.getPrice() != null){
            int resultPrice = item.getQuantity() * item.getPrice();
            if(resultPrice < 10000){
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, "잘못된 값입니다.");

            }
        }

    }
}
