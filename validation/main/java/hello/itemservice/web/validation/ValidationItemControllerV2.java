package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor //
public class ValidationItemControllerV2 {

    //@RquiredArgsConstrucotr의 도움으로 자동으로 스프링빈 주입이 된다.
    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;


    /**
     * @InitBinder가 붙으면 이 클래스 컨트롤러가 호출될 때 마다 함께 호출된다.
     * 요청이 올 때 마다 dataBinder는 새로 만들어짐.
     * 그리고 그곳에 itemValidator가 들어가있다.
     *
     */

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(itemValidator);
    }


    // 아래 코드가 @RequiredArgsConstrucotor로 해결됨.
//    @Autowired
//    public ValidationItemControllerV2(ItemRepository itemRepository, ItemValidator itemValidator) {
//        this.itemRepository = itemRepository;
//        this.itemValidator = itemValidator;
//    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }



//    @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          Model model) {

        //BindingResult가 역할을 해준다.
//        Map<String, String> errors = new HashMap<>();


        if(!StringUtils.hasText(item.getItemName())){
//            errors.put("itemName", "상품 이름은 필수입니다.");
            // ctrl + p로 메서드 알아볼 수 있음.
            // object명(모델에 담기는 객체 명) / obejct 필드명, 메세지
            // filed 단위의 에러는 FieldError()로 만들어서 bindingResult에 넣는다.
            bindingResult.addError(new FieldError("item", "itemName","상품 이름은 필수입니다. "));
        }

        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
//            errors.put("price", "가격은 1,000 ~ 1,000,000만 가능합니다.");
            bindingResult.addError(new FieldError("item", "price","가격은 1,000 ~ 1,000,000만 가능합니다."));

        }

        if(item.getQuantity() == null || item.getQuantity() >= 9999){
//            errors.put("quantity", "수량은 최대 9,999까지 허용합니다.");
            bindingResult.addError(new FieldError("item", "quantity","수량은 최대 9,999까지 허용합니다."));
        }

        // 특정 필드가 아닌 복합 룰 검증

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                // 특정 필드랑 비교할 수 없다.
                // errors.put("globalError", "가격 * 수량의 합은 10,000 이상이어야 합니다. 현재 값 = " + resultPrice);
                // 필드 에러가 아닌 글로벌 에러다. 따라서 FieldError가 아닌 ObjectError로 만든다.
                bindingResult.addError(new ObjectError("item","가격 * 수량의 합은 10,000 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        // 검증 실패하면 다시 입력 폼으로
        // 부정의 부정 = 긍정. 부정의 부정으로 하면 코딩 시 읽기가 어렵다.
        // 검증의 실패하면 View 템플릿으로 보내버린다.
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
//            model.addAttribute("errors", errors);
            return "validation/v2/addForm";
        }



        // 검증에 성공할 경우.
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        if(!StringUtils.hasText(item.getItemName())){

            // new FieldError()한 후 ctrl + p하면, rejectedValue가 있는 생성자도 볼 수 있다.
            // bindingFailure는 데이터 자체가 넘어오는게 실패했냐고 물어보는거다.
            // 우리는 이름은 값이 정상적으로 넘어온다.
            // code, args는 null로 잡아준다. code, args는 default 메세지를 message 기능을 이용해서 대체할 수 있는데 그 때 쓴다.
//            bindingResult.addError(new FieldError("item", "itemName","상품 이름은 필수입니다. "));
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(),false,null, null,"상품 이름은 필수입니다."));
        }

        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
//            bindingResult.addError(new FieldError("item", "price","가격은 1,000 ~ 1,000,000만 가능합니다."));
            bindingResult.addError(new FieldError("item", "price",item.getPrice(),false,null,null,"가격은 1,000 ~ 1,000,000만 가능합니다."));

        }

        if(item.getQuantity() == null || item.getQuantity() >= 9999){
//            bindingResult.addError(new FieldError("item", "quantity","수량은 최대 9,999까지 허용합니다."));
            bindingResult.addError(new FieldError("item", "quantity",item.getQuantity(),false,null,null,"수량은 최대 9,999까지 허용합니다."));
        }


        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                //object Error는 기존 데이터를 보관하는게 아니다. 왜냐하면 값이 넘어온게 아니라, 로직으로 확인하는 것이다.
//                bindingResult.addError(new ObjectError("item","가격 * 수량의 합은 10,000 이상이어야 합니다. 현재 값 = " + resultPrice));
                bindingResult.addError(new ObjectError("item",null,null,"가격 * 수량의 합은 10,000 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v2/addForm";
        }



        // 검증에 성공할 경우.
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        log.info("objectName = {} ", bindingResult.getObjectName());
        log.info("target = {} ", bindingResult.getTarget() );
        if(!StringUtils.hasText(item.getItemName())){

            //code를 넣을 때 String 배열로 넣어야함.
            //배열로 넣어주는 이유는 가장 앞의 오류 메세지를 찾을 수 없으면, 루프를 계속 돌아서 뒷쪽의 값을 출력해준다.
            // 결국에는 default 메세지를 하나 출력해둘 수 있겠다.
            //얘는 argrs가 필요없다.
            bindingResult.addError(new FieldError("item", "itemName",
                    item.getItemName(),false,new String[]{"required.item.itemName"},
                    null,null));
        }

        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            bindingResult.addError(new FieldError("item", "price",item.getPrice(),
                    false,new String[]{"range.item.price"},new Object[]{1000,1000000},null));

        }

        if(item.getQuantity() == null || item.getQuantity() >= 9999){
            bindingResult.addError(new FieldError("item", "quantity",item.getQuantity(),
                    false,new String[]{"max.item.quantity"},new Object[]{9999},null));
        }


        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                //object Error는 기존 데이터를 보관하는게 아니다. 왜냐하면 값이 넘어온게 아니라, 로직으로 확인하는 것이다.
//                bindingResult.addError(new ObjectError("item","가격 * 수량의 합은 10,000 이상이어야 합니다. 현재 값 = " + resultPrice));
                bindingResult.addError(new ObjectError("item",
                        new String[]{"totalPriceMin"},new Object[]{10000, resultPrice},null));
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v2/addForm";
        }



        // 검증에 성공할 경우.
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


//    @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            Model model) {

//        validator로 분리하면서 검증 로직은 모두 ItemValidator로 옮김.
        log.info("objectName = {} ", bindingResult.getObjectName());
        log.info("target = {} ", bindingResult.getTarget() );


        // 아래 검증 로직을 한 줄로 줄이면 다음과 같다.
//        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required");

        if(!StringUtils.hasText(item.getItemName())){
            //ObjectName은 생략해도 된다. 왜냐하면 BindingResult가 이 값을 알고 있음.
            //에러 코드가 기존과 좀 다르다.
            //기존 required.item.itemName의 가장 앞자리만 따서 넣어주면 된다.
            bindingResult.rejectValue("itemName", "required");

//            bindingResult.addError(new FieldError("item", "itemName",
//                    item.getItemName(),false,new String[]{"required.item.itemName"},
//                    null,null));
        }

        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){

            // argrs를 넣어줘야한다.
            bindingResult.rejectValue("price", "range", new Object[]{1000,1000000}, null);



//            bindingResult.addError(new FieldError("item", "price",item.getPrice(),
//                    false,new String[]{"range.item.price"},new Object[]{1000,1000000},null));
        }

        if(item.getQuantity() == null || item.getQuantity() >= 9999){

            bindingResult.rejectValue("quantity","max", new Object[]{9999}, null);
//            bindingResult.addError(new FieldError("item", "quantity",item.getQuantity(),
//                    false,new String[]{"max.item.quantity"},new Object[]{9999},null));
        }


        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {

                //Object name은 이미 알고 있기 때문에, error code만 넣어주면 된다.
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);

//                bindingResult.addError(new ObjectError("item",
//                        new String[]{"totalPriceMin"},new Object[]{10000, resultPrice},null));
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }



//    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        //검증기 때린다.
        //bindingResult에 error가 입력된다.
        itemValidator.validate(item,bindingResult);


        //검증 실패하면 입력폼으로 돌아간다.
        if (bindingResult.hasErrors()){
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";

        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


    /**
     * @Validated를 Item 앞에 달아주면, 이 컨트롤러가 시작될 때 Item에 대한 검증기가 항상 동작한다
     * @InitBinder로 만들어진 검증기에서 로직이 돈다. 그리고 검증이 다 된 것이 그냥 BindingResult로 들어온다.
     */

    @PostMapping("/add")
    public String addItemV6(@Validated @ModelAttribute Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        //검증 실패하면 입력폼으로 돌아간다.
        if (bindingResult.hasErrors()){
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";

        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }





    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

