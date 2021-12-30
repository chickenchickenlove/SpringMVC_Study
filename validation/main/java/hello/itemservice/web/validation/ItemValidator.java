package hello.itemservice.web.validation;


import hello.itemservice.domain.item.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class ItemValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {


        Item item = (Item) target;
        BindingResult bindingResult = (BindingResult) errors;

        log.info("object Name : {} ", bindingResult.getObjectName());
        log.info("target Name : {} ", bindingResult.getTarget());



        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.rejectValue("itemName","required", "필수 값입니다.");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.rejectValue("price","range", new Object[]{1000,10000},"잘못된 값입니다.");
        }

        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            bindingResult.rejectValue("quantity","max",new Object[]{9999} ,"잘못된 값입니다.");
        }


        // 글로벌 검증.
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice},"잘못된 값입니다.");
            }
        }


    }
}
