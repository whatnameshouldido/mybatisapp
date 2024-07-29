package com.studymavernspringboot.mybatisapp.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/catweb")
public class CatWebController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("")
    public String indexHome() {
        return "index";
    }

    @GetMapping("/category_list")
    public String list(Model model
            , @RequestParam Integer page, @RequestParam String name) {
        try {
            if (name == null) {
                name = "";
            }
            SearchCategoryDto searchCategoryDto = SearchCategoryDto.builder()
                    .name(name).page(page).build();
            int total = this.categoryService.countAllByNameContains(searchCategoryDto);
            searchCategoryDto.setTotal(total);
            List<ICategory> allList = this.categoryService.findAllByNameContains(searchCategoryDto);
            model.addAttribute("allList", allList);
            model.addAttribute("searchCategoryDto", searchCategoryDto);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", "오류가 발생했습니다. 관리자에게 문의하세요.");
            return "error/error_save";
        }
        return "catweb/category_list";
    }

    @PostMapping("/category_list_act")
    public String categoryNewInsert(@ModelAttribute CategoryDto dto, Model model) {
        try {
            if ( dto == null || dto.getName() == null || dto.getName().isEmpty() ) {
                model.addAttribute("error_message", "이름이 비었습니다.");
                return "error/error_bad";  // 브라우저 주소를 redirect 한다.
            }
            this.categoryService.insert(dto);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", dto.getName() + " 중복입니다.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "redirect:category_list?page=1&name=";  // 브라우저 주소를 redirect 한다.
    }

    @GetMapping("/category_list_view")    // 브라우저의 URL 주소
    public String categoryNewView(Model model, @RequestParam String name) {
        try {
            if ( name == null) {
                model.addAttribute("error_message", "ID는 1보다 커야 합니다.");
                return "error/error_bad";
            }
            ICategory find = this.categoryService.findByName(name);
            if(find == null) {
                model.addAttribute("error_message", name + "데이터가 없습니다.");
                return "error/error_find";
            }
            model.addAttribute("categoryDto", find);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", "서버 에러입니다. 관리자에게 문의 하세요.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "catweb/category_list";  // resources/templates 폴더안의 화면파일
    }

    @PostMapping("/category_list_update")
    public String categoryNewUpdate(Model model, @ModelAttribute CategoryDto categoryDto) {
        try {
            if (categoryDto == null || categoryDto.getId() <= 0 || categoryDto.getName().isEmpty()) {
                model.addAttribute("error_message", "id는 1보다 커야하고, name 이 있어야 합니다.");
                return "error/error_bad";
            }
            ICategory find = this.categoryService.findByName(categoryDto.getName());
            if ( find == null ) {
                model.addAttribute("error_message", categoryDto.getId() + " 데이터가 없습니다.");
                return "error/error_find";
            }
            this.categoryService.update(categoryDto.getName(), categoryDto);
        }catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", categoryDto.getName() + " 중복입니다.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "redirect:category_list?page=1&name=";
    }

    @GetMapping("/category_list_delete")
    public String categoryDelete(Model model, @RequestParam String name) {
        try {
            if (name == null) {
                model.addAttribute("error_message", "name 이 있어야 합니다.");
                return "error/error_bad";
            }
            ICategory find = this.categoryService.findByName(name);
            if ( find == null ) {
                model.addAttribute("error_message", name + " 데이터가 없습니다.");
                return "error/error_find";
            }
            this.categoryService.remove(name);
        }catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", "서버 에러입니다. 관리자에게 문의 하세요.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "redirect:category_list?page=1&name=";
    }

    @GetMapping("/category_list_add")
    public String categoryAdd(Model model, @RequestParam String name) {
        try {
            if (name == null || name.isEmpty()) {
                model.addAttribute("error_message", "name 이 있어야 합니다.");
                return "error/error_bad";
            }
            ICategory find = this.categoryService.findByName(name);
            if (find != null) {
                model.addAttribute("error_message", name + " 중복입니다.");
                return "error/error_save";
            }
            CategoryDto newCategory = new CategoryDto();
            newCategory.setName(name);
            this.categoryService.insert(newCategory);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", "서버 에러입니다. 관리자에게 문의 하세요.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "redirect:category_list?page=1&name=";
    }

}