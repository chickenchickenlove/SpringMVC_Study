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

        // item을 검증하는 validator는 아래와 같이 만든다.
        // 파라미터로 넘어오는 class가 Item Type으로 지원이 되는지를 본다.
        // item == clazz (같은 타입이냐?)
        // item == subItem(아이템의 자식 타입도 통과한다)
        // 자식 클래스도 커버하기 떄문에 == 비교보다는 이렇게 하는게 낫다.
        return Item.class.isAssignableFrom(clazz);
//        return false;
    }

    /**
     * Object target이 넘어온다. 우리가 검증하고자 하는 클래스다.
     * 따라서 Object를 쓰기 위해서는 다운 캐스팅이 필요하다.
     *
     */

    @Override
    public void validate(Object target, Errors errors) {
        //errors == Bindingresult의 부모클래스임.
        Item item = (Item) target;


        if(!StringUtils.hasText(item.getItemName())){
            errors.rejectValue("itemName", "required");

        }

        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            errors.rejectValue("price", "range", new Object[]{1000,1000000}, null);
        }

        if(item.getQuantity() == null || item.getQuantity() >= 9999){
            errors.rejectValue("quantity","max", new Object[]{9999}, null);
        }


        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }


    }
}
