package platform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import platform.exception.CodeNotFoundException;
import platform.service.CodeSharingService;

@Data
@RestController
@RequestMapping("/code")
@Tag(name = "Контроллер для http-запросов для Code Snippets",
        description = "Позволяет добавлять и получать сниппеты кода, выводя всё на html страницы")
public class WebInterfaceController {

    private CodeSharingService codeSharingService;

    @Autowired
    public WebInterfaceController(CodeSharingService codeSharingService) {
        this.codeSharingService = codeSharingService;
    }

    @Operation(
            summary = "Получение HTML страницы со сниппетом кода"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting html page operation was successfully completed"),
            @ApiResponse(responseCode = "400", description = "Id isn't correct", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content)
    })
    @GetMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getHtmlPageWithCode(@PathVariable Integer id) throws CodeNotFoundException {
        return codeSharingService.getPageWithCodeSnippet(id);
    }

    @Operation(
            summary = "Получение HTML страницы для отправки сниппета кода"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting html page operation was successfully completed"),
            @ApiResponse(responseCode = "400", description = "Something is wrong in your query", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content)
    })
    @GetMapping(value = "/new", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getHtmlPageWithPasteCode() {
        return codeSharingService.getHtmlPageWithPasteCode();
    }

    @Operation(
            summary = "Получение HTML страницы с последними 10 сниппетами"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting html page with codes was successfully completed"),
            @ApiResponse(responseCode = "400", description = "Something is wrong in your query", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content)
    })
    @GetMapping(value = "/latest", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getHtmlPageWithLatestCodeSnippets() {
        return codeSharingService.getHtmlPageWithLatestCodeSnippets();
    }
}
