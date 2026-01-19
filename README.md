# Projeto API de Veículos

Este projeto implementa **exatamente** o desafio técnico proposto pela **Tinnova**, consistindo em uma **API REST de gerenciamento de veículos**, com foco em requisitos de negócio, segurança, testes automatizados, qualidade de código e aderência às boas práticas solicitadas no desafio.

---

## ? Arquitetura (Aderente ao Desafio Tinnova)

Organização baseada em camadas:

```
com.tinnova.veiculos
??? VeiculosApplication.java
??? config
?   ??? OpenApiConfig.java
?   ??? SecurityConfig.java
?   ??? RedisConfig.java
??? controller
?   ??? VeiculoController.java
??? dto
?   ??? VeiculoRequestDTO.java
?   ??? VeiculoResponseDTO.java
?   ??? ErrorResponseDTO.java
??? entity
?   ??? Veiculo.java
??? exception
?   ??? BusinessException.java
?   ??? ResourceNotFoundException.java
?   ??? GlobalExceptionHandler.java
??? repository
?   ??? VeiculoRepository.java
??? service
?   ??? VeiculoService.java
?   ??? impl
?       ??? VeiculoServiceImpl.java
??? integration
?   ??? DollarExchangeService.java
??? security
    ??? JwtAuthenticationFilter.java
    ??? JwtTokenProvider.java
    ??? UserRoles.java
```

Princípios aplicados:

* **SRP**: cada classe tem uma única responsabilidade
* **DIP**: controllers dependem de interfaces
* **Open/Closed**: regras extensíveis sem modificar código existente

---

## ? Entidade Veículo (Requisitos Tinnova)

```java
@Entity
@Table(name = "veiculos", uniqueConstraints = @UniqueConstraint(columnNames = "placa"))
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private String cor;
    private Integer ano;
    private String placa;

    private BigDecimal precoDolar;

    private Boolean ativo = true;
}
```

---

## ? DTOs

### VeiculoRequestDTO

```java
public record VeiculoRequestDTO(
        String marca,
        String modelo,
        String cor,
        Integer ano,
        String placa,
        BigDecimal precoEmReais
) {}
```

### VeiculoResponseDTO

```java
public record VeiculoResponseDTO(
        Long id,
        String marca,
        String modelo,
        String cor,
        Integer ano,
        String placa,
        BigDecimal precoDolar
) {}
```

---

## ? Integração ? Cotação do Dólar

```java
public interface DollarExchangeService {
    BigDecimal getDollarRate();
}
```

Implementação:

* API principal: AwesomeAPI
* Fallback: Frankfurter
* Cache Redis (TTL configurável)

---

## ?? Service

```java
public interface VeiculoService {
    VeiculoResponseDTO criar(VeiculoRequestDTO dto);
    Page<VeiculoResponseDTO> listar(FiltroVeiculo filtro, Pageable pageable);
    VeiculoResponseDTO buscarPorId(Long id);
    VeiculoResponseDTO atualizar(Long id, VeiculoRequestDTO dto);
    void remover(Long id);
}
```

Validações:

* Placa única
* PUT/PATCH inválidos
* Soft delete (ativo = false)

---

## ? Controller (Endpoints do Desafio Tinnova)

```java
@RestController
@RequestMapping("/veiculos")
@SecurityRequirement(name = "bearerAuth")
public class VeiculoController {

    private final VeiculoService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VeiculoResponseDTO> criar(@RequestBody @Valid VeiculoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Page<VeiculoResponseDTO> listar(Pageable pageable) {
        return service.listar(null, pageable);
    }
}
```

---

## ? Segurança (USER / ADMIN ? JWT)

* JWT Bearer Token
* Roles: USER e ADMIN
* Filtros por endpoint

```java
.antMatchers(HttpMethod.POST, "/veiculos").hasRole("ADMIN")
.antMatchers(HttpMethod.GET, "/veiculos/**").hasAnyRole("USER","ADMIN")
```

---

## ? Testes Automatizados (Conforme Avaliação Tinnova)

### Service (JUnit + Mockito)

```java
@Test
void deveLancarErroQuandoPlacaDuplicada() {
    when(repository.existsByPlaca("ABC1234")).thenReturn(true);
    assertThrows(BusinessException.class, () -> service.criar(dto));
}
```

### Controller

* 401 não autenticado
* 403 acesso negado
* 409 conflito de placa

### Integração (@SpringBootTest)

Fluxo:

1. Obter token
2. Criar veículo (ADMIN)
3. Listar
4. Buscar por ID

---

## ? OpenAPI / Swagger

Disponível em:

```
http://localhost:8080/swagger-ui.html
```

Inclui:

* JWT Bearer Auth
* Documentação completa dos endpoints

---

## ?? Executando o Projeto

```bash
mvn clean install
mvn spring-boot:run
```

H2 Console:

```
http://localhost:8080/h2-console
```

---

## ? Diferenciais

* Clean Code
* SOLID
* Soft delete
* Cache Redis
* Testes de integração
* API documentada

---

## ? Próximos Passos (Extras Opcionais)

* Docker Compose (API + Redis)
* CI com GitHub Actions
* Cobertura Jacoco ? 75%

---

??? Autor: Diego Cunha

? **Desafio Técnico:** Tinnova ? Avaliação de Candidato

