package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.SaveCheck;
import hello.itemservice.domain.item.UpdateCheck;
import hello.itemservice.web.validation.form.ItemSaveForm;
import hello.itemservice.web.validation.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
public class ValidationItemControllerV3 {

    private final ItemRepository itemRepository;
//    private final ItemValidatorV2 itemValidatorV2;
//
//
//    @InitBinder
//    public void init_(WebDataBinder dataBinder) {
//        dataBinder.addValidators(itemValidatorV2);
//
//    }



    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v3/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "validation/v3/addForm";
        }

        // ?????? ??????
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV2(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (item.getQuantity() != null && item.getPrice() != null){
            int resultPrice = item.getQuantity() * item.getPrice();
            if(resultPrice < 10000){
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "validation/v3/addForm";
        }

        // ?????? ??????
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }


//    @PostMapping("/add")
    public String addItemV3(@Validated(SaveCheck.class) @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "validation/v3/addForm";
        }

        // ?????? ??????
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

    @PostMapping("/add")
    public String addItemV4(@Valid @ModelAttribute ItemSaveForm itemSaveForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        log.info("bindingResult = {}", bindingResult);


        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "validation/v3/addForm";
        }

        Item item = new Item();
        item.setItemName(itemSaveForm.getItemName());
        item.setPrice(itemSaveForm.getPrice());
        item.setQuantity(itemSaveForm.getQuantity());


        model.addAttribute("item", item);


        // ?????? ??????
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }





    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/editForm";
    }


//    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }

//    @PostMapping("/{itemId}/edit")
    public String editV2(@PathVariable Long itemId, @Validated(UpdateCheck.class) @ModelAttribute Item item, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            log.info("bindingResult = {}", bindingResult);

            return "validation/v3/editForm";
        }


        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }


    @PostMapping("/{itemId}/edit")
    public String editV3(@PathVariable Long itemId, @Valid @ModelAttribute ItemUpdateForm itemUpdateForm, BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()){
            log.info("bindingResult = {}", bindingResult);

            return "validation/v3/editForm";
        }


        Item item = new Item();
        item.setItemName(itemUpdateForm.getItemName());
        item.setPrice(itemUpdateForm.getPrice());
        item.setQuantity(itemUpdateForm.getQuantity());


        model.addAttribute("item", item);

        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }




}

