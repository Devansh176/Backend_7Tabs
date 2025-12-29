package prefix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prefix.entity.Prefix;
import prefix.service.DebouncedSearchService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/tab9")
public class DebouncedSearchController {

    @Autowired
    private DebouncedSearchService debouncedSearchService;

    @GetMapping("/search")
    public List<Prefix> search(@RequestParam(name = "q", defaultValue = "") String query) {
        if(query == null || query.isEmpty()) {
            return Collections.emptyList();
        }

        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return debouncedSearchService.searchTitle(query);
    }
}
