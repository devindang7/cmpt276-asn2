package cmpt276.asn2.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cmpt276.asn2.models.RectRepository;
import cmpt276.asn2.models.Rectangle;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RectController {
    @Autowired
    private RectRepository rectRepo;
    @GetMapping("/rectangles")
    public String getRectangles(Model model) {
        List<Rectangle> rects = rectRepo.findAll(Sort.by(Sort.Direction.ASC, "uid"));
        model.addAttribute("rects", rects);
        return "/allRects";
    }

    @PostMapping("/rectangles/add")
    public String addRect(@RequestParam Map<String, String> newRect, HttpServletResponse response){
        String newName = newRect.get("name");
        int newWidth = Integer.parseInt(newRect.get("width"));
        int newHeight = Integer.parseInt(newRect.get("height"));
        String newColor = newRect.get("color");
        rectRepo.save(new Rectangle(newName,newWidth,newHeight,newColor));
        response.setStatus(201);
        return "redirect:/rectangles";
    }

    @GetMapping("/rectangles/{id}")
    public String showEditRect(Model model, @PathVariable int id){
        Rectangle rect = rectRepo.findById(id).get();
        model.addAttribute("rect", rect);
        return "/editRect";
    }

    @PostMapping("/rectangles/edit/{id}")
    public String editRect(@RequestParam Map<String, String> rect, @PathVariable int id, HttpServletResponse response){
        String newName = rect.get("name");
        int newWidth = Integer.parseInt(rect.get("width"));
        int newHeight = Integer.parseInt(rect.get("height"));
        String newColor = rect.get("color");
        Rectangle currentRect = rectRepo.findById(id).get();
        currentRect.setName(newName);
        currentRect.setWidth(newWidth);
        currentRect.setHeight(newHeight);
        currentRect.setColor(newColor);
        rectRepo.save(currentRect);
        response.setStatus(201);
        return "redirect:/rectangles";
    }

    @DeleteMapping("/rectangles/delete/{id}")
    public String deleteRect(){
        return "redirect:/rectangles";
    }
}
