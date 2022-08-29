import java.util.Random;

public class VetorPalavraImagem {
	
	private int tamanho; //referente ao tamanho atual do arquivo
	private PalavraImagem vetor[]; //referente ao segredo e � figura que ser�o armazenados no vetor
	
	//m�todo contrutor que cria um vetor de registro com o tamanho dado, mas com tamana=ho atual 0
	public VetorPalavraImagem (int t) {
		this.vetor = new PalavraImagem [t];
		this.tamanho = 0;
	}
	
	//m�todo que insere um registro no vetor
	public void insereVetor(PalavraImagem registro){
		this.vetor[this.tamanho] = registro;
		this.tamanho++;
	}
	
	//m�todo para sortear aleatoriamente um elemento no vetor
	public PalavraImagem sorteio () {
		Random posicao = new Random ();
		int ind = posicao.nextInt (this.tamanho);
		return this.vetor[ind];
	}
	
	//m�todo que retorna o tamanho do vetor
	public int getTamanho () {
		return this.vetor.length;
	}
	
	//m�todo que retorna uma determinada posi��o do vetor
	public PalavraImagem getIndVetor (int ind) {
		return this.vetor[ind];
	}
}
