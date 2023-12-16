import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Livraria {
    public static ArrayList<Filial> arrayFiliais = new ArrayList<>();
    public static ArrayList<Produto> arrayProdutos = new ArrayList<>();
    public static void main (String[] args) throws Exception{
        FileInputStream arquivo = new FileInputStream("src/livros.txt");
        Scanner entradaTexto = new Scanner(arquivo);
        Scanner input = new Scanner(System.in);
        String [] SL;
        String linha;

        while(entradaTexto.hasNextLine() == true){
            linha = entradaTexto.nextLine();
            SL = linha.split(",");

            if(SL[0].contains("#FL")){
                Filial filial = new Filial((SL[0]), SL[1], SL[2], SL[3]);
                arrayFiliais.add(filial);
            } else {
                Produto produto = new Produto(Integer.parseInt(SL[0]), SL[1], Integer.parseInt(SL[2]), SL[3], SL[4], Double.parseDouble(SL[5]), Integer.parseInt(SL[6]));
                for(int i=arrayFiliais.size() -1; i<arrayFiliais.size();i++){
                    arrayFiliais.get(i).arrayLivros.add(produto); // adiciona o livro no array de filiais, na ultima filial adicionada
                }
            }
        }

        int opcao = 999;
        while(opcao != 0){
            System.out.println();
            System.out.println("Digite a opção desejada: ");
            System.out.println("1 - Cadastrar novo livro.");
            System.out.println("2 - Listar filiais.");
            System.out.println("3 - Buscar livros por nome.");
            System.out.println("4 - Buscar livros por categoria.");
            System.out.println("5 - Buscar livros por preço.");
            System.out.println("6 - Buscar livros por código em todas filiais.");
            System.out.println("7 - Busca por valor no estoque.");
            System.out.println("8 - Listar estoque da filial.");
            System.out.println("9 - Atualizar arquivo de estoque.");
            System.out.println("0 - Encerrar.");

            opcao = input.nextInt();

            if(opcao == 0){
                System.out.println("Salvar alterações em arquivo? (1 - Sim | 2 -  Não)");
                int salvar = 0;
                salvar = input.nextInt();
                if(salvar == 1){
                    atualizarArquivo(arrayFiliais);
                }
            }

            switch (opcao){
                case 1:
                    novoProduto();
                    break;
                case 2:
                    listarFilial();
                    break;
                case 3:
                    buscarNome();
                    break;
                case 4:
                    buscarArea();
                    break;
                case 5:
                    buscarPreco();
                    break;
                case 6:
                    buscarCodigo();
                    break;
                case 7:
                    buscarValorEstoque();
                    break;
                case 8:
                    listarFilial();
                    break;
                case 9:
                    atualizarArquivo(arrayFiliais);
                    break;
                default:
                    System.out.println("Digite uma opção válida!");
            }

        }
    }

    public static void atualizarArquivo(ArrayList<Filial> arrayFiliais){
        String nomeArquivo = "src/livros.txt";

        try {
            FileWriter gravaArquivo = new FileWriter(nomeArquivo);
            BufferedWriter escreveArquivo = new BufferedWriter(gravaArquivo);
            for (int i=0; i < arrayFiliais.size(); i++) {
                // Escreve as informações da filial no arquivo
                escreveArquivo.write(arrayFiliais.get(i).getCodigo() + "," + arrayFiliais.get(i).getNome() + "," + arrayFiliais.get(i).getEndereco() + "," + arrayFiliais.get(i).getContato());
                escreveArquivo.newLine();
                for(int j=0; j < arrayFiliais.get(i).arrayLivros.size(); j++) {
                    escreveArquivo.write(arrayFiliais.get(i).arrayLivros.get(j).getCodigo() + "," + arrayFiliais.get(i).arrayLivros.get(j).getTitulo() +
                            "," + arrayFiliais.get(i).arrayLivros.get(j).getAno() + "," + arrayFiliais.get(i).arrayLivros.get(j).getArea() +
                            "," + arrayFiliais.get(i).arrayLivros.get(j).getEditora() + "," + arrayFiliais.get(i).arrayLivros.get(j).getValor() +
                            ',' + arrayFiliais.get(i).arrayLivros.get(j).getQtdEstoque());
                    escreveArquivo.newLine();
                }

            }

            escreveArquivo.close();
            System.out.println("As informações foram gravadas no arquivo com sucesso.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao gravar as informações no arquivo.");
            e.printStackTrace();
        }
    }

    public static void novoProduto (){
        Scanner input = new Scanner(System.in);
        input.useDelimiter("\n");
        String titulo, editora, area, codFilial;
        int codigo, ano, qtdEstoque;
        double valor;
        boolean livroJaCadastrado = false;

        System.out.println("");
        System.out.println("Digite o codigo do livro: ");
        codigo = input.nextInt();
        System.out.println("Digite o título do livro: ");
        titulo = input.next();

        for(int i=0; i < arrayFiliais.size(); i++) {
            for (int j = 0; j < arrayFiliais.get(i).arrayLivros.size(); j++){
                if (arrayFiliais.get(i).arrayLivros.get(j).getCodigo() == codigo || arrayFiliais.get(i).arrayLivros.get(j).getTitulo().equals(titulo) ) {
                    System.out.println("Livro já cadastrado no sistema, na filial " + arrayFiliais.get(i).getCodigo());
                    System.out.println("Atribuindo valores já registrados a estre livro...");
                    codigo = arrayFiliais.get(i).arrayLivros.get(j).getCodigo();
                    titulo = arrayFiliais.get(i).arrayLivros.get(j).getTitulo();
                    editora = arrayFiliais.get(i).arrayLivros.get(j).getEditora();
                    area = arrayFiliais.get(i).arrayLivros.get(j).getArea();
                    ano = arrayFiliais.get(i).arrayLivros.get(j).getAno();
                    System.out.println("Valores atribuidos...");
                    System.out.println("Digite o valor do livro:");
                    valor = input.nextDouble();
                    System.out.println("Digite a quantidade em estoque: ");
                    qtdEstoque = input.nextInt();
                    System.out.println("Digite o código da filial:");
                    codFilial = input.next();

                    for(int k=0; k<arrayFiliais.size(); k++){
                        if(arrayFiliais.get(k).getCodigo().equals(codFilial)){
                            Produto produto = new Produto(codigo,titulo,ano,area,editora,valor,qtdEstoque);
                            arrayFiliais.get(k).arrayLivros.add(produto);
                            arrayProdutos.add(produto);
                        }
                    }

                    livroJaCadastrado = true;
                    break;
                }
            }
            if(livroJaCadastrado){
                break;
            }
        }
        if(!livroJaCadastrado) {
            System.out.println("Digite a editora do livro: ");
            editora = input.next();
            System.out.println("Digite a area(categoria) do livro: ");
            area = input.next();
            System.out.println("Digite o ano do livro: ");
            ano = input.nextInt();
            System.out.println("Digite a quantidade em estoque: ");
            qtdEstoque = input.nextInt();
            System.out.println("Digite o valor do livro: ");
            valor = input.nextDouble();
            System.out.println("Digite o código da filial:");
            codFilial = input.next();

            for(int k=0; k<arrayFiliais.size(); k++){
                if(arrayFiliais.get(k).getCodigo().equals(codFilial)){
                    Produto produto = new Produto(codigo,titulo,ano,area,editora,valor,qtdEstoque);
                    arrayFiliais.get(k).arrayLivros.add(produto);
                    arrayProdutos.add(produto);
                }
            }
        }
    }

    public static void listarFilial(){
        Scanner input = new Scanner(System.in);
        String codFilial;

        System.out.println("Digite o código da filial para listar estoque:");
        codFilial = input.nextLine();

        for(int i = 0; i < arrayFiliais.size(); i++){
            if(arrayFiliais.get(i).getCodigo().equals(codFilial)){
                arrayFiliais.get(i).listagemEstoque(codFilial);
            }
        }


    }

    public static void buscarCodigo(){
        Scanner input = new Scanner(System.in);
        int codigo;

        System.out.println("Digite o código do livro:");
        codigo = input.nextInt();
        Filial f = new Filial();

        f.buscarLivroCodigo(arrayFiliais, codigo);

    }

    public static void buscarNome(){
        Scanner input = new Scanner(System.in);

        System.out.println("Digite o nome do livro que quer buscar: ");
        input.useDelimiter("\n");
        String nome = input.next();
        System.out.println("Digite o código da filial:");
        String codFilial = input.next();

        for(int i = 0; i<arrayFiliais.size();i++){
            if(codFilial.equals(arrayFiliais.get(i).getCodigo())){
                for(int j=0; j<arrayFiliais.get(i).arrayLivros.size();j++){
                    if(nome.equals(arrayFiliais.get(i).arrayLivros.get(j).getTitulo())){
                        System.out.println("Informações do livro:");
                        System.out.println("Nome: " + arrayFiliais.get(i).arrayLivros.get(j).getTitulo());
                        System.out.println("Editora: " + arrayFiliais.get(i).arrayLivros.get(j).getEditora());
                        System.out.println("Area: " + arrayFiliais.get(i).arrayLivros.get(j).getArea());
                        System.out.println("Código: " + arrayFiliais.get(i).arrayLivros.get(j).getCodigo());
                        System.out.println("Ano: " + arrayFiliais.get(i).arrayLivros.get(j).getAno());
                        System.out.println("Valor: " + arrayFiliais.get(i).arrayLivros.get(j).getValor());
                        System.out.println("Quantidade em estoque: " + arrayFiliais.get(i).arrayLivros.get(j).getQtdEstoque());
                        System.out.println("Valor em estoque: " + arrayFiliais.get(i).arrayLivros.get(j).getValorEstoque());
                        System.out.println(" ");
                        break;
                    }
                }
            }
        }


    }
    public static void buscarArea(){
        Scanner input = new Scanner(System.in);

        System.out.println("Digite a area do livro que quer buscar: ");
        input.useDelimiter("\n");
        String area = input.next();
        System.out.println("Digite o código da filial:");
        String codFilial = input.next();

        for(int i = 0; i<arrayFiliais.size();i++){
            if(codFilial.equals(arrayFiliais.get(i).getCodigo())){
                for(int j=0; j<arrayFiliais.get(i).arrayLivros.size();j++){
                    if(area.equals(arrayFiliais.get(i).arrayLivros.get(j).getArea())){
                        System.out.println("Informações do livro:");
                        System.out.println("Nome: " + arrayFiliais.get(i).arrayLivros.get(j).getTitulo());
                        System.out.println("Editora: " + arrayFiliais.get(i).arrayLivros.get(j).getEditora());
                        System.out.println("Area: " + arrayFiliais.get(i).arrayLivros.get(j).getArea());
                        System.out.println("Código: " + arrayFiliais.get(i).arrayLivros.get(j).getCodigo());
                        System.out.println("Ano: " + arrayFiliais.get(i).arrayLivros.get(j).getAno());
                        System.out.println("Valor: " + arrayFiliais.get(i).arrayLivros.get(j).getValor());
                        System.out.println("Quantidade em estoque: " + arrayFiliais.get(i).arrayLivros.get(j).getQtdEstoque());
                        System.out.println("Valor em estoque: " + arrayFiliais.get(i).arrayLivros.get(j).getValorEstoque());
                        System.out.println(" ");
                        break;
                    }
                }
            }
            break;
        }
    }
    public static void buscarPreco(){
        Scanner input = new Scanner(System.in);

        System.out.println("Digite o preço limite do livro que quer buscar: ");
        input.useDelimiter("\n");
        double preco = input.nextDouble();
        System.out.println("Digite o código da filial:");
        String codFilial = input.next();

        for(int i = 0; i<arrayFiliais.size();i++){
            if(codFilial.equals(arrayFiliais.get(i).getCodigo())){
                for(int j=0; j<arrayFiliais.get(i).arrayLivros.size();j++){
                    if(preco >= arrayFiliais.get(i).arrayLivros.get(j).getValor()){
                        System.out.println("Informações do livro:");
                        System.out.println("Nome: " + arrayFiliais.get(i).arrayLivros.get(j).getTitulo());
                        System.out.println("Editora: " + arrayFiliais.get(i).arrayLivros.get(j).getEditora());
                        System.out.println("Area: " + arrayFiliais.get(i).arrayLivros.get(j).getArea());
                        System.out.println("Código: " + arrayFiliais.get(i).arrayLivros.get(j).getCodigo());
                        System.out.println("Ano: " + arrayFiliais.get(i).arrayLivros.get(j).getAno());
                        System.out.println("Valor: " + arrayFiliais.get(i).arrayLivros.get(j).getValor());
                        System.out.println("Quantidade em estoque: " + arrayFiliais.get(i).arrayLivros.get(j).getQtdEstoque());
                        System.out.println("Valor em estoque: " + arrayFiliais.get(i).arrayLivros.get(j).getValorEstoque());
                        System.out.println(" ");
                        break;
                    }
                }
            }
            break;
        }
    }
    public static void buscarValorEstoque(){
        Scanner input = new Scanner(System.in);

        System.out.println("Digite o valor em estoque para realizar a busca: ");
        input.useDelimiter("\n");
        double valorEstoque = input.nextDouble();
        System.out.println("Digite o código da filial:");
        String codFilial = input.next();

        for(int i = 0; i<arrayFiliais.size();i++){
            if(codFilial.equals(arrayFiliais.get(i).getCodigo())){
                for(int j=0; j<arrayFiliais.get(i).arrayLivros.size();j++){
                    if(valorEstoque < arrayFiliais.get(i).arrayLivros.get(j).getValorEstoque()){
                        System.out.println("Informações do livro:");
                        System.out.println("Nome: " + arrayFiliais.get(i).arrayLivros.get(j).getTitulo());
                        System.out.println("Editora: " + arrayFiliais.get(i).arrayLivros.get(j).getEditora());
                        System.out.println("Area: " + arrayFiliais.get(i).arrayLivros.get(j).getArea());
                        System.out.println("Código: " + arrayFiliais.get(i).arrayLivros.get(j).getCodigo());
                        System.out.println("Ano: " + arrayFiliais.get(i).arrayLivros.get(j).getAno());
                        System.out.println("Valor: " + arrayFiliais.get(i).arrayLivros.get(j).getValor());
                        System.out.println("Quantidade em estoque: " + arrayFiliais.get(i).arrayLivros.get(j).getQtdEstoque());
                        System.out.println("Valor em estoque: " + arrayFiliais.get(i).arrayLivros.get(j).getValorEstoque());
                        System.out.println(" ");
                        break;
                    }
                }
            }
            break;
        }
    }
}
