package br.com.roberto.v1.rest;

import br.com.roberto.dto.ContatoDto;
import br.com.roberto.entity.Contato;
import br.com.roberto.exceptions.InfraEstruturaException;
import br.com.roberto.exceptions.IntegracaoException;
import br.com.roberto.exceptions.NegocioException;
import br.com.roberto.service.ContatoService;
import br.com.roberto.v1.model.ContatoModel;
import br.com.roberto.v1.openapi.ContatoRestOpenApi;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("v1/contatos")
public class ContatoRest implements ContatoRestOpenApi {

    private Logger logger = Logger.getLogger(ContatoRest.class.getName());

    @Inject
    private ContatoService contatoService;

    /**
     * Consulta todos os contatos
     * @return Lista de Contatos
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContatos(){
        List<ContatoDto> contatosResponse = null;
        contatosResponse = new ArrayList<>();

        try {
            for (ContatoDto contatoDto : contatoService.getContatos()) {
                contatosResponse.add(contatoDto);
            }
        } catch (NegocioException e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.fromStatusCode(NegocioException.CODIGO)).build();
        }catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.fromStatusCode(InfraEstruturaException.CODIGO)).build();
        }
        return Response.ok(contatosResponse).build();
    }
}
