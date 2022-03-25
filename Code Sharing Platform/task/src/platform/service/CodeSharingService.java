package platform.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import platform.dto.CodeDto;
import platform.entity.Code;
import platform.exception.CodeNotFoundException;
import platform.repository.CodeRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class CodeSharingService {

    private final CodeRepository repository;

    @Autowired
    public CodeSharingService(CodeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Map<String, String> addCodeSnippet(CodeDto codeDto, LocalDateTime dateTime) {
        Code code = new Code(codeDto.getCode(), dateTime);
        repository.save(code);
        log.info(String.format("Code Snippet %d was created", code.getId()));
        return Map.of("id", String.valueOf(code.getId()));
    }


    public Map<String, String> getCodeSnippet(Integer id) throws CodeNotFoundException {
        log.info(String.format("Getting Code Snippet from Code DB %d", id));
        Code code = repository.getCodeById(id).orElseThrow(() -> new CodeNotFoundException(id));
        return Map.of(
                "code", code.getCode(),
                "date", code.getDate()
        );
    }

    public List<Code> getLatestCodeSnippet() {
        log.info("Getting Latest 10 Code Snippets from Code DB");
        return repository.getLatestCodes();
    }

    public ModelAndView getPageWithCodeSnippet(Integer id) throws CodeNotFoundException {
        log.info(String.format("Getting Code Snippet from Code DB %d", id));
        Code code = repository.getCodeById(id).orElseThrow(() -> new CodeNotFoundException(id));
        ModelAndView modelAndView = new ModelAndView("code");
        modelAndView.addObject("dateOnHtml", code.getDate())
                    .addObject("codeOnHtml", code.getCode());
        return modelAndView;
    }

    public ModelAndView getHtmlPageWithLatestCodeSnippets() {
        log.info("Getting Latest 10 Code Snippets from Code DB");
        List<Code> sortedCodeSnippets = repository.getLatestCodes();
        ModelAndView modelAndView = new ModelAndView("latestCodeSnippets");
        modelAndView.addObject("codes", sortedCodeSnippets);
        return modelAndView;
    }

    public ModelAndView getHtmlPageWithPasteCode() {
        return new ModelAndView("pasteCode");
    }
}
