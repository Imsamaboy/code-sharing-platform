package platform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.dto.CodeDto;
import platform.exception.CodeNotFoundException;
import platform.service.CodeSharingService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@Data
@RequestMapping("/api/code")
@Tag(name = "Контроллер для http-запросов для Code Snippets",
     description = "Позволяет добавлять и получать сниппеты кода")
public class CodeApiController {

    private final CodeSharingService codeSharingService;

    @Autowired
    public CodeApiController(CodeSharingService codeSharingService) {
        this.codeSharingService = codeSharingService;
    }

    @Operation(
            summary = "Добавление нового сниппета кода"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Code added to db"),
            @ApiResponse(responseCode = "400", description = "Request Body is incorrect", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content)
    })
    @Parameter(name = "CodeDto", hidden = true)
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewCode(@Valid @RequestBody CodeDto codeDto) {
        return new ResponseEntity<>(codeSharingService.addCodeSnippet(codeDto, LocalDateTime.now()), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение сниппета по Id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting code operation was successfully completed"),
            @ApiResponse(responseCode = "400", description = "Code not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content)
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCodeById(@PathVariable Integer id) throws CodeNotFoundException {
        return new ResponseEntity<>(codeSharingService.getCodeSnippet(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение последних 10 сниппетов"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting codes operation was successfully completed"),
            @ApiResponse(responseCode = "400", description = "Something is wrong in your query", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content)
    })
    @GetMapping(value = "/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  getLatestCodeSnippets() {
        return new ResponseEntity<>(codeSharingService.getLatestCodeSnippet(), HttpStatus.OK);
    }
}
