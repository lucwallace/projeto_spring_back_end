package com.example.projetoSpring.openApi;


import com.example.projetoSpring.domain.ImagemPerfil;
import com.example.projetoSpring.message.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Imagem de usuário")
public interface ImageUserOpenApiController {

    @Operation(summary = "Retorno da imagem por usuário")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(defaultValue = "PNG"), mediaType = "image/png")}),
            @ApiResponse(responseCode = "404", description = "Não Encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno"),
            @ApiResponse(responseCode = "401", description = "Não Autorizado")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @Parameter(name = "id",
    description = "ID da imagem",
    example = "4de3529d-0101-424d-bedd-030ea264d7c5",
    schema = @Schema(type = "string", description = "uuid"),
    required = true)
    ResponseEntity<byte[]> getFiles(@PathVariable String id);

    /*@Parameters({
            @Parameter(name = "file", description = "Arquivo de imagem"),
            @Parameter(name = "page", description = "Page number, starting from 0", required = true),
            @Parameter(name = "size", description = "Number of items per page", required = true)
    })*/
    ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("id_user") Long idUser);
}
