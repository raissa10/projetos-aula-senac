package principal;

import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        int opcao = 0;

        do {

            System.out.println("\n");
            System.out.println("Menu Principal, Informe a opção desejada:");
            System.out.println("1 - Produto");
            System.out.println("2 - Cliente");
            System.out.println("3 - Vendedor");
            System.out.println("4 - Venda");
            System.out.println("0 - Sair");
            opcao = entrada.nextInt();

            if (opcao == 1) {

                do {

                    System.out.println("\n");
                    System.out.println("Menu Produtos, selecione a opção desejada:");
                    System.out.println("1 - Cadastrar Produto");
                    System.out.println("2 - Alterar Produto");
                    System.out.println("3 - Excluir Produto");
                    System.out.println("0 - Sair");
                    opcao = entrada.nextInt();

                    Produto p = new Produto(0.00);

                    if (opcao == 1) {

                        System.out.println("Informe o Código do Produto");
                        p.setCodigo(entrada.nextInt());

                        System.out.println("Informe a Descrição do Produto");
                        entrada.nextLine();
                        p.setDescricao(entrada.nextLine());

                        System.out.println("Informe o Preço do Produto");
                        p.setPreco(entrada.nextDouble());

                        p.gravarProduto();

                    } else if (opcao == 2) {

                        int codigo = 0;

                        System.out.println("\n");
                        System.out.println("Digite o código do Produto que deseja alterar");
                        codigo = entrada.nextInt();

                        p.setCodigo(codigo);

                        System.out.println("Informe a Descrição do Produto");
                        entrada.nextLine();
                        p.setDescricao(entrada.nextLine());

                        System.out.println("Informe o Preço do Produto");
                        p.setPreco(entrada.nextDouble());

                        p.alterarProduto();

                    } else if (opcao == 3) {

                        int codigo = 0;

                        System.out.println("\n");
                        System.out.println("Digite o código do Produto que deseja excluir");
                        codigo = entrada.nextInt();

                        p.setCodigo(codigo);

                        p.deletarProduto();
                    }
                } while (opcao != 0);

            } else if (opcao == 2) {

                do {

                    System.out.println("\n");
                    System.out.println("Menu Clientes, selecione a opção desejada:");
                    System.out.println("1 - Cadastrar Cliente");
                    System.out.println("2 - Alterar Cliente");
                    System.out.println("3 - Excluir Cliente");
                    System.out.println("0 - Sair");
                    opcao = entrada.nextInt();

                    Cliente c = new Cliente();

                    if (opcao == 1) {

                        System.out.println("Informe o Código do Cliente");
                        c.setCodigo(entrada.nextInt());

                        System.out.println("Informe o nome do Cliente");
                        entrada.nextLine();
                        c.setNome(entrada.nextLine());

                        System.out.println("Informe o endereço do Cliente");
                        entrada.nextLine();
                        c.setEndereco(entrada.nextLine());

                        System.out.println("Informe o CPF do Cliente");
                        entrada.nextLine();
                        c.setCpf(entrada.nextLine());

                        //c.gravarCliente(null, null, null, null);
                    } else if (opcao == 2) {

                        int codigo = 0;

                        System.out.println("\n");
                        System.out.println("Digite o código do Produto que deseja alterar");
                        codigo = entrada.nextInt();

                        c.setCodigo(codigo);

                        System.out.println("Informe o nome do Cliente");
                        entrada.nextLine();
                        c.setNome(entrada.nextLine());

                        System.out.println("Informe o endereço do Cliente");
                        c.setEndereco(entrada.nextLine());

                        System.out.println("Informe o CPF do Cliente");
                        c.setCpf(entrada.nextLine());

                        c.alterarCliente();

                    } else if (opcao == 3) {

                        int codigo = 0;

                        System.out.println("\n");
                        System.out.println("Digite o código do Cliente que deseja excluir");
                        codigo = entrada.nextInt();

                        c.setCodigo(codigo);

                        c.deletarCliente();

                    }
                } while (opcao != 0);

            } else if (opcao == 3) {

                do {

                    System.out.println("\n");
                    System.out.println("Menu Vendedores, selecione a opção desejada:");
                    System.out.println("1 - Cadastrar Vendedor");
                    System.out.println("2 - Alterar Vendedor");
                    System.out.println("3 - Excluir Vendedor");
                    System.out.println("0 - Sair");
                    opcao = entrada.nextInt();

                    Vendedor v = new Vendedor();

                    if (opcao == 1) {

                        System.out.println("Informe o Código do Vendedor");
                        v.setCodigo(entrada.nextInt());

                        System.out.println("Informe o nome do Vendedor");
                        entrada.nextLine();
                        v.setNome(entrada.nextLine());

                        System.out.println("Informe o endereço do Vendedor");
                        v.setEndereco(entrada.nextLine());

                        System.out.println("Informe o CPF do Vendedor");
                        v.setCpf(entrada.nextLine());

                        System.out.println("Informe o usuário do Vendedor");
                        v.setUsuario(entrada.nextLine());

                        System.out.println("Informe a senha do Vendedor");
                        v.setSenha(entrada.nextLine());

                        v.gravarVendedor();

                    } else if (opcao == 2) {

                        int codigo = 0;

                        System.out.println("\n");
                        System.out.println("Digite o código do Vendedor que deseja alterar");
                        codigo = entrada.nextInt();

                        v.setCodigo(codigo);

                        System.out.println("Informe o nome do Vendedor");
                        entrada.nextLine();
                        v.setNome(entrada.nextLine());

                        System.out.println("Informe o endereço do Vendedor");
                        entrada.nextLine();
                        v.setEndereco(entrada.nextLine());

                        System.out.println("Informe o CPF do Vendedor");
                        entrada.nextLine();
                        v.setCpf(entrada.nextLine());

                        System.out.println("Informe o usuário do Vendedor");
                        entrada.nextLine();
                        v.setUsuario(entrada.nextLine());

                        System.out.println("Informe a senha do Vendedor");
                        entrada.nextLine();
                        v.setSenha(entrada.nextLine());

                        v.alterarVendedor();

                    } else if (opcao == 3) {

                        int codigo = 0;

                        System.out.println("\n");
                        System.out.println("Digite o código do Vendedor que deseja excluir");
                        codigo = entrada.nextInt();

                        v.setCodigo(codigo);

                        v.deletarVendedor();

                    }
                } while (opcao != 0);

            } else if (opcao == 4) {

                Date data = new Date();

                do {

                    System.out.println("Informe a opção desejada");
                    System.out.println("1 - Cadastrar Venda");
                    System.out.println("2 - Listar Vendas");
                    System.out.println("0 - Sair");
                    opcao = entrada.nextInt();

                    if (opcao == 1) {

                        Venda v = new Venda();
                        VendaProduto vp = new VendaProduto();

                        int codProd = 0;

                        System.out.println("Informe o código da venda");
                        v.setCodigo(entrada.nextInt());

                        System.out.println("Informe o Cliente (código)");
                        v.setCliente(entrada.nextInt());

                        System.out.println("Informe o Vendedor (código)");
                        v.setVendedor(entrada.nextInt());

                        v.setData(String.valueOf(data.getTime()));

                        v.gravarVenda();

                        do {

                            System.out.println("Informe o código do Produto (0 - Sair)");
                            codProd = entrada.nextInt();

                            if (codProd > 0) {

                                vp.setProduto(codProd);

                                System.out.println("Informe a quantidade do produto");
                                vp.setQuantidade(entrada.nextInt());

                                System.out.println("Informe o preço do produto");
                                vp.setPreco(entrada.nextDouble());

                                vp.gravarProdutoDaVenda(v.getCodigo());

                            }

                        } while (codProd != 0);

                    } else if (opcao == 2) {

                        Venda v = new Venda();

                        System.out.println("Informe o código da venda");
                        v.setCodigo(entrada.nextInt());

                        v.listarVenda();

                        VendaProduto vp = new VendaProduto();
                        vp.listarVenda(v.getCodigo());

                    }

                } while (opcao != 0);

            }
        } while (opcao != 0);
    }
}
