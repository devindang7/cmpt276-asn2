package cmpt276.asn2.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RectRepository extends JpaRepository<Rectangle, Integer>{
    List<Rectangle> findByName(String name);
    List<Rectangle> findByWidthAndHeight(int width, int height);
    List<Rectangle> findByColor(String color);
    List<Rectangle> findByUid(int uid);
}
