# LiterAlura - Catálogo de Livros 📚

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.3-6DB33F?style=for-the-badge&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16%2B-316192?style=for-the-badge&logo=postgresql)
![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)

## 📝 Descrição
O **LiterAlura** é um catálogo de livros interativo operado via console, desenvolvido como parte do **Alura Challenge / Oracle Next Education (ONE)**. A aplicação consome dados da API **Gutendex** para buscar informações sobre livros e autores, persistindo-os em um banco de dados relacional para consultas e filtros personalizados.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
| :--- | :--- | :--- |
| **Java** | 17+ | Linguagem principal do sistema. |
| **Spring Boot** | 4.0.3 | Framework para inversão de controle e injeção de dependências. |
| **PostgreSQL** | 16+ | Banco de dados para persistência de dados. |
| **Jackson** | 3.x | Biblioteca para desserialização de JSON. |
| **Maven** | 3.x | Gerenciador de dependências e automação de build. |

---

## 📋 Pré-requisitos
Para rodar este projeto, você precisará ter instalado em sua máquina:
* **JDK 17** ou superior.
* **Maven 3.x**.
* **PostgreSQL 16** ou superior.
* Uma IDE de sua preferência (recomendado **IntelliJ IDEA**).

---

## 🚀 Como Executar o Projeto

1. **Clonar o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/literalura.git
   ```

2. **Configurar o Banco de Dados:**
   No PostgreSQL, crie um banco de dados chamado `literalura`.

3. **Configurar as credenciais:**
   Edite o arquivo `src/main/resources/application.properties` com seus dados de acesso locais:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```

4. **Executar a aplicação:**
   Na pasta raiz do projeto, execute o comando:
   ```bash
   mvn spring-boot:run
   ```

---

## ⚙️ Funcionalidades
O sistema oferece um menu interativo com as seguintes opções:
1. **Buscar livro pelo título**: Consulta a API externa e salva o livro e o autor no banco de dados.
2. **Listar livros registrados**: Exibe todos os livros armazenados no PostgreSQL.
3. **Listar autores registrados**: Lista todos os autores salvos e suas obras correspondentes.
4. **Listar autores vivos em um determinado ano**: Filtra autores que estavam vivos em uma data específica.
5. **Listar livros em um determinado idioma**: Filtra os livros salvos por códigos como `en`, `pt`, `fr` ou `es`.

---

## 📂 Estrutura do Projeto

Abaixo, a organização dos pacotes e suas respectivas responsabilidades:

```text
com.literalura.LiterAlura
├── dto          # Records (DadosLivro, DadosAutor) para mapear o JSON da API.
├── model        # Entidades JPA (Livro, Autor) que representam as tabelas no banco.
├── repository   # Interfaces que estendem JpaRepository para consultas ao banco.
├── service      # Classes de consumo de API (HttpClient) e conversão de dados.
└── principal    # Classe Principal que orquestra o menu e a interação com o usuário.
```
