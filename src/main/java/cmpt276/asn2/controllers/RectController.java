package cmpt276.asn2.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.multipart.MultipartFile;

import cmpt276.asn2.models.RectRepository;
import cmpt276.asn2.models.Rectangle;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RectController {
    @Autowired
    private RectRepository rectRepo;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images";
    
    public void uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
    }

    @GetMapping("/rectangles")
    public String getRectangles(Model model) {
        List<Rectangle> rects=rectRepo.findAll(Sort.by(Sort.Direction.ASC, "uid"));
        model.addAttribute("rects", rects);
        return "/allRects";
    }

    @PostMapping("/rectangles/add")
    public String addRect(@RequestParam Map<String, String> newRect, HttpServletResponse response, @RequestParam("image") MultipartFile file) throws IOException{
        uploadImage(file);
        String newName=newRect.get("name");
        int newWidth=Integer.parseInt(newRect.get("width"));
        int newHeight=Integer.parseInt(newRect.get("height"));
        String newColor=newRect.get("color");
        String newImage=file.getOriginalFilename();
        rectRepo.save(new Rectangle(newName,newWidth,newHeight,newColor,newImage));
        response.setStatus(201);
        return "redirect:/rectangles";
    }

    @GetMapping("/rectangles/{id}")
    public String showEditRect(Model model, @PathVariable int id){
        Rectangle rect=rectRepo.findById(id).get();
        model.addAttribute("rect", rect);
        return "/editRect";
    }

    @PostMapping("/rectangles/edit/{id}")
    public String editRect(@RequestParam Map<String, String> rect, @PathVariable int id, HttpServletResponse response, @RequestParam("image") MultipartFile file) throws IOException{
        uploadImage(file);
        String newName=rect.get("name");
        int newWidth=Integer.parseInt(rect.get("width"));
        int newHeight=Integer.parseInt(rect.get("height"));
        String newColor=rect.get("color");
        String newImage=file.getOriginalFilename();
        Rectangle currentRect=rectRepo.findById(id).get();
        currentRect.setName(newName);
        currentRect.setWidth(newWidth);
        currentRect.setHeight(newHeight);
        currentRect.setColor(newColor);
        currentRect.setImage(newImage);
        rectRepo.save(currentRect);
        response.setStatus(201);
        return "redirect:/rectangles";
    }

    @DeleteMapping("/rectangles/delete/{id}")
    public String deleteRect(@PathVariable int id){
        Rectangle currentRect=rectRepo.findById(id).get();
        rectRepo.delete(currentRect);
        return "redirect:/rectangles";
    }
}