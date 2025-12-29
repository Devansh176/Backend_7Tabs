package prefix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prefix.entity.Prefix;
import prefix.repository.PrefixRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DebouncedSearchService {

    @Autowired
    private PrefixRepository prefixRepository;

    @Transactional
    public List<Prefix> searchTitle(String query) {
        return prefixRepository.findByTitle(query);
    }
}
