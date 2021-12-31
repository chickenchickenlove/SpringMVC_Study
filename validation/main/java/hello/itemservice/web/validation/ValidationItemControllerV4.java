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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v4/items")
@RequiredArgsConstructor
public class ValidationItemControllerV4 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v4/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v4/addForm";
    }


    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute(name = "item") ItemSaveForm itemSaveForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {


        if(itemSaveForm.getQuantity() != null && itemSaveForm.getPrice() != null){
            int resultPrice = itemSaveForm.getQuantity() * itemSaveForm.getPrice();
            if(resultPrice < 10000){
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "validation/v4/addForm";
        }

        Item item = new Item();
        item.setItemName(itemSaveForm.getItemName());
        item.setPrice(itemSaveForm.getPrice());
        item.setQuantity(itemSaveForm.getQuantity());

        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }





    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/editForm";
    }


    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute(name = "item") ItemUpdateForm itemUpdateForm, BindingResult bindingResult,
                         Model model) {

        if(itemUpdateForm.getQuantity() != null && itemUpdateForm.getPrice() != null){
            int resultPrice = itemUpdateForm.getQuantity() * itemUpdateForm.getPrice();
            if(resultPrice < 10000){
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }


        if (bindingResult.hasErrors()){
            log.info("bindingResult = {}", bindingResult);

            return "validation/v4/editForm";
        }


        Item item = new Item();
        item.setItemName(itemUpdateForm.getItemName());
        item.setPrice(itemUpdateForm.getPrice());
        item.setQuantity(itemUpdateForm.getQuantity());


        model.addAttribute("item", item);

        itemRepository.update(itemId, item);
        return "redirect:/validation/v4/items/{itemId}";
    }




}

