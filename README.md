# evinil - Vendas de Vinil
## Execução
Para execução do projeto via maven é através do comando: mvn exec:java

Obs: o projeto esta configurado para executa sobre a porta 8080

## Exemplos de url para consumo da API:
1) Consultar catalogo de disco por genero paginado:
http://localhost:8080/api/discos/byGenero/pop?page=3&size=5

2) Consultar pelo id:
http://localhost:8080/api/discos/biId/0r9z7OUt2JN6P4PI2cTDgw

3) Consultar vendas paginadas pela data:
http://localhost:8080/api/vendas/byData/?dataInicio=18/02/2019&dataFim=20/02/2019&page=0&size=5
Obs: Está utilizando o padrão de paginas começando de 0(zero)

4) Consultar vendas pelo id:
http://localhost:8080/api/vendas/byId/1

5) Registrar uma nova venda através, exemplo do body do Post:
{
	"itens": [
		{"disco":"1SBKxrKIIgGyYMl43ffjnU", "quantidade":1},
		{"disco":"3V7cJKYn9Da5gv601HuchP", "quantidade":2},
		{"disco":"5hf74KpfuwSJXVs85k3dVI", "quantidade":2},
		{"disco":"69ltPvQJmyU3OZLyDHG032", "quantidade":1}
	]
}
