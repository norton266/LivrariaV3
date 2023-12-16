import java.util.ArrayList;
public class Filial {
    public String codigo;
    public String nome, endereco, contato;
    public ArrayList<Produto> arrayLivros;

    public Filial(String codigo, String nome, String endereco, String contato) {
        this.codigo = codigo;
        this.nome = nome;
        this.endereco = endereco;
        this.contato = contato;
        this.arrayLivros = new ArrayList<>();
    }

    public Filial(){

    }

    public void info(){
        System.out.println("=== MÉTODO INFO DE FILIAL ===");
        System.out.println(">>>>>Codigo" +this.getCodigo());
        System.out.println("Nome: "+this.getNome());
        System.out.println("Endereco: "+this.getEndereco());
        System.out.println("Contato: "+this.getContato());
    }

    public void listagemEstoque(String codFilial){
        double valorTotalLivros = 0;
        System.out.println("Livros em estoque da Filial: " +this.getNome());
        for(int i = 0; i < arrayLivros.size();i++){
            System.out.println("Título: " + this.arrayLivros.get(i).getTitulo());
            System.out.println("Valor: " + this.arrayLivros.get(i).getValor());
            System.out.println("Quantidade: " + this.arrayLivros.get(i).getQtdEstoque());
            System.out.println("Valor em estoque: " + this.arrayLivros.get(i).getValorEstoque());
            valorTotalLivros += this.arrayLivros.get(i).getValorEstoque();
            System.out.println("===============================");
        }
        System.out.println("Valor total em estoque de livros: R$" +valorTotalLivros);
    }

    public void buscarLivroCodigo(ArrayList<Filial> arrayFiliais, int codigo){
        boolean encontrado = false;
        Produto livroEncontrado = null;

        // Busca o livro e armazena a referência em livroEncontrado
        for (Filial filial : arrayFiliais) {
            for (Produto livro : filial.arrayLivros) {
                if (livro.getCodigo() == codigo) {
                    encontrado = true;
                    livroEncontrado = livro;
                    break;
                }
            }
            if (encontrado) {
                break;
            }
        }

        if (encontrado) {
            System.out.println(">>>>Cod# " + livroEncontrado.getCodigo());
            System.out.println("Titulo/Editora: " + livroEncontrado.getTitulo() + "/" + livroEncontrado.getEditora());
            System.out.println("Categoria: " + livroEncontrado.getArea());
            System.out.println("Ano: " + livroEncontrado.getAno());

            for (Filial filial : arrayFiliais) {
                for (Produto livro : filial.arrayLivros) {
                    if (livro.getCodigo() == codigo) {
                        System.out.println("Valor do Livro: R$" + livro.getValor() + ">>> Filial: " + filial.getNome() +", estoque: " + livro.getQtdEstoque());
                    }
                }
            }
        } else {
            System.out.println("Livro não encontrado nas filiais.");
        }

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public ArrayList<Produto> getArrayLivros() {
        return arrayLivros;
    }

    public void setArrayLivros(ArrayList<Produto> arrayLivros) {
        this.arrayLivros = arrayLivros;
    }
}
