package cmpt276.asn2.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        List<Rectangle> rects = rectRepo.findAll();
        model.addAttribute("rects", rects);
        return "/allRects";
    }

    @PostMapping("/addRectangle")
    public String addRect(@RequestParam Map<String, String> newRect, HttpServletResponse response){
        String newName = newRect.get("name");
        int newWidth = Integer.parseInt(newRect.get("width"));
        int newHeight = Integer.parseInt(newRect.get("height"));
        String newColor = newRect.get("color");
        rectRepo.save(new Rectangle(newName,newWidth,newHeight,newColor));
        response.setStatus(201);
        return "redirect:/rectangles";
    }
}
